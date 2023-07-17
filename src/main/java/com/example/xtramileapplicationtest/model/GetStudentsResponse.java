package com.example.xtramileapplicationtest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetStudentsResponse {
    private List<StudentResponse> data;
    private String error;
    private Metadata metadata;
}


