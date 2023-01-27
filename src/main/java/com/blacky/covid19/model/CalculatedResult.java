package com.blacky.covid19.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculatedResult {

    private Integer confirmed;
    private LocalDate date;
    private String country;

}
