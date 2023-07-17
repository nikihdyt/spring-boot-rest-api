package com.example.xtramileapplicationtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStudentRequest {
    @JsonIgnore
    @NotBlank
    private String id;

    @Size(max = 50)
    private String nama_depan;

    @Size(max = 100)
    private String nama_belakang;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String tanggal_lahir;
}
