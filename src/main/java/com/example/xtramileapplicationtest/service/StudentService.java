package com.example.xtramileapplicationtest.service;

import com.example.xtramileapplicationtest.entity.Student;
import com.example.xtramileapplicationtest.model.*;
import com.example.xtramileapplicationtest.repository.StudentRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private Validator validator;

    @Transactional
    public void create(CreateStudentRequest request) {
        Set<ConstraintViolation<CreateStudentRequest>> constraintViolations = validator.validate(request);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }

        if (studentRepository.existsById(request.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student with id " + request.getId() + " already exists");
        }

        Student student = new Student();
        student.setId(request.getId());
        student.setNamaDepan(request.getNama_depan());
        student.setNamaBelakang(request.getNama_belakang());

        LocalDate tanggalLahir = LocalDate.parse(request.getTanggal_lahir());
        student.setTanggalLahir(tanggalLahir);

        studentRepository.save(student);
    }

    @Transactional
    public GetStudentsResponse getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> students = studentRepository.findAll(pageable);

        List<StudentResponse> studentResponses = students.getContent().stream()
                .map(student -> StudentResponse.builder()
                        .nim(student.getId())
                        .nama_lengkap(student.getNamaBelakang() != null ? student.getNamaDepan() + " " + student.getNamaBelakang() : student.getNamaDepan() + " ")
                        .usia(student.getTanggalLahir() != null ? Period.between(student.getTanggalLahir(), LocalDate.now()).getYears() : null)
                        .build())
                .collect(Collectors.toList());

        return GetStudentsResponse.builder()
                .data(studentResponses)
                .metadata(Metadata.builder()
                        .page(students.getNumber())
                        .size(students.getSize())
                        .total_elements((int) students.getTotalElements())
                        .total_pages(students.getTotalPages())
                        .build())
                .build();
    }

    public StudentData getById(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " not found"));

        return StudentData.builder()
                .nim(student.getId())
                .nama_depan(student.getNamaDepan())
                .nama_belakang(student.getNamaBelakang() != null ? student.getNamaBelakang() : "")
                .tanggal_lahir(student.getTanggalLahir().toString())
                .build();
    }

    public StudentResponse update(String id, UpdateStudentRequest request) {
        validator.validate(request);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " not found"));

        student.setNamaDepan(request.getNama_depan());
        student.setNamaBelakang(request.getNama_belakang().isEmpty() ? null : request.getNama_belakang());
        student.setTanggalLahir(LocalDate.parse(request.getTanggal_lahir()));
        studentRepository.save(student);

        return StudentResponse.builder()
                .nim(student.getId())
                .nama_lengkap(student.getNamaBelakang() != null ? student.getNamaDepan() + " " + student.getNamaBelakang() : student.getNamaDepan() + " ")
                .usia(student.getTanggalLahir() != null ? Period.between(student.getTanggalLahir(), LocalDate.now()).getYears() : null)
                .build();
    }


    @Transactional
    public void delete(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " not found"));

        studentRepository.delete(student);
    }

}
