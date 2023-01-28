package com.blacky.covid19.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country implements Serializable {

    private String country;
    private String slug;
    private String iso2;

}
