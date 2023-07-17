package com.example.xtramileapplicationtest.controller;

import com.example.xtramileapplicationtest.model.*;
import com.example.xtramileapplicationtest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(
            path = "/api/student",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<String> createStudent(@RequestBody CreateStudentRequest request) {
        studentService.create(request);
        return Response.<String>builder()
                .data("Student created successfully")
                .build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(
            path = "/api/students",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GetStudentsResponse getStudents(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        int pageNumber = page >= 1 ? page : 1;
        int pageSize = size > 0 ? size : 10;

        List<StudentResponse> students = studentService.getList(pageNumber -1, pageSize).getData();
        List<StudentResponse> res = new ArrayList<>();

        for (StudentResponse student : students) {
            StudentResponse temp = StudentResponse.<String>builder()
                    .nim(student.getNim())
                    .nama_lengkap(student.getNama_lengkap())
                    .usia(student.getUsia())
                    .build();
            res.add(temp);
        }

        return GetStudentsResponse.builder()
                .data(res)
                .metadata(Metadata.builder()
                        .page(pageNumber)
                        .size(pageSize)
                        .total_elements(studentService.getList(pageNumber, pageSize).getMetadata().getTotal_elements())
                        .total_pages(studentService.getList(pageNumber, pageSize).getMetadata().getTotal_pages())
                        .build()
                )
                .build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(
            path = "/api/student/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<StudentResponse> updateStudent(@PathVariable("id") String id, @RequestBody UpdateStudentRequest request) {
        request.setId(id);

        StudentResponse studentResponse = studentService.update(id, request);
        return Response.<StudentResponse>builder()
                .data(studentResponse)
                .build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping(
            path = "/api/student/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<String> deleteStudent(@PathVariable("id") String id) {
        studentService.delete(id);
        return Response.<String>builder()
                .data("Student deleted successfully")
                .build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(
            path = "/api/student/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<StudentData> getStudent(@PathVariable("id") String id) {
        StudentData studentData = studentService.getById(id);
        return Response.<StudentData>builder()
                .data(studentData)
                .build();
    }
}
