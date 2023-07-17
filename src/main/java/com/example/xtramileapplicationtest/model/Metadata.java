package com.example.xtramileapplicationtest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Metadata {
    private int page;
    private int size;
    private int total_elements;
    private int total_pages;
}
