package com.blacky.covid19.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CalcFunction {
    
    @JsonProperty("min")
    MIN,
    @JsonProperty("max")
    MAX

}