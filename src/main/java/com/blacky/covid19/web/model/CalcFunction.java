package com.blacky.covid19.web.model;

import com.blacky.covid19.model.OneDayResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public enum CalcFunction {

    @JsonProperty("min")
    MIN {
        @Override
        public BinaryOperator<OneDayResult> getCasesFunction() {
            return BinaryOperator.minBy(Comparator.comparing(OneDayResult::getCases));
        }
    },
    @JsonProperty("max")
    MAX {
        @Override
        public BinaryOperator<OneDayResult> getCasesFunction() {
            return BinaryOperator.maxBy(Comparator.comparing(OneDayResult::getCases));
        }
    };

    public abstract BinaryOperator<OneDayResult> getCasesFunction();

}