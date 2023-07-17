package com.example.xtramileapplicationtest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentData {
    private String nim;
    private String nama_depan;
    private String nama_belakang;
    private String tanggal_lahir;
}