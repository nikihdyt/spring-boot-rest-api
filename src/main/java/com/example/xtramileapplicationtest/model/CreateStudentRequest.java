package com.example.xtramileapplicationtest.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudentRequest {
    @NotBlank
    @Size(max = 25)
    private String id;

    @NotBlank
    @Size(max = 50)
    private String nama_depan;

    @Size(max = 100)
    private String nama_belakang;

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String tanggal_lahir;
}
