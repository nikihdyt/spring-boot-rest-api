package com.example.xtramileapplicationtest.controller;

import com.example.xtramileapplicationtest.entity.Student;
import com.example.xtramileapplicationtest.model.*;
import com.example.xtramileapplicationtest.repository.StudentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    void testCreateStudentSuccess() throws Exception{
        CreateStudentRequest request = new CreateStudentRequest();
        request.setId("SVUGM0001");
        request.setNama_depan("Niki");
        request.setNama_belakang("Hidayati");
        request.setTanggal_lahir("2000-01-01");

        mockMvc.perform(
                post("/api/student")
                .accept("application/json")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals("Student created successfully", response.getData());
        });
    }

    @Test
    void testCreateStudentBadRequest() throws Exception  {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setId("");
        request.setNama_depan("");
        request.setNama_belakang("");
        request.setTanggal_lahir("");

        mockMvc.perform(
                post("/api/student")
                .accept("application/json")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getError());
        });
    }

    @Test
    void testGetStudentsSuccess() throws Exception {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setId("SVUGM0001");
        request.setNama_depan("Niki");
        request.setNama_belakang("Hidayati");
        request.setTanggal_lahir("2000-01-01");

        mockMvc.perform(
                post("/api/student")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        );
        mockMvc.perform(
                get("/api/students")
                .contentType("application/json")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            GetStudentsResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals(null, response.getError());
        });
    }

    @Test
    void testUpdateStudentsSuccess() throws Exception {
        CreateStudentRequest requestCreate = new CreateStudentRequest();
        requestCreate.setId("SVUGM0001");
        requestCreate.setNama_depan("Niki");
        requestCreate.setNama_belakang("Hidayati");
        requestCreate.setTanggal_lahir("2000-01-01");

        mockMvc.perform(
                post("/api/student")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestCreate))
        );

        CreateStudentRequest request = new CreateStudentRequest();
        request.setNama_depan("Xtramile");
        request.setNama_belakang("Solution");
        request.setTanggal_lahir("2003-10-10");

        mockMvc.perform(
                put("/api/student/SVUGM0001")
                .accept("application/json")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            Response<StudentResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals(null, response.getError());
            assertEquals("Xtramile Solution", response.getData().getNama_lengkap());
        });
    }

    @Test
    void testDeleteStudentSuccess() throws Exception {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setId("SVUGM111");
        request.setNama_depan("Niki");
        request.setNama_belakang("Hidayati");
        request.setTanggal_lahir("2000-01-01");

        mockMvc.perform(
                post("/api/student")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        );

        mockMvc.perform(
                delete("/api/student/SVUGM111")
                .contentType("application/json")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals(null, response.getError());
            assertEquals("Student deleted successfully", response.getData());
        });
    }

}