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
public class OneDayResult {

    private String country;
    private String countryCode;
    private Integer cases;
    private String status;
    private LocalDate date;

}
