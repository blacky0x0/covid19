package com.blacky.covid19.web;

import com.blacky.covid19.model.CalculatedResult;
import com.blacky.covid19.model.Country;
import com.blacky.covid19.web.model.CalcFunction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/covid19")
public class WebController {

    @Operation(summary = "Get a list of countries")
    @GetMapping("/countries")
    public List<Country> countries() {
        return List.of(new Country("Finland", "finland", "FI"));
    }

    @Operation(summary = "Get min or max confirmed cases of covid19 infection" +
            " for a specified list of countries per day on the selected day/period")
    @GetMapping("/confirmed/calc/{function}")
    @Parameter(name = "from", in = ParameterIn.QUERY, example = "2020-03-01")
    @Parameter(name = "to", in = ParameterIn.QUERY, example = "2020-03-02")
    @Parameter(name = "countries", in = ParameterIn.QUERY, example = "[\"portugal\",\"finland\",\"united-states\"]")
    public Object confirmedCasesInPeriod(@PathVariable CalcFunction function,
                                         @RequestParam LocalDate from,
                                         @RequestParam LocalDate to,
                                         @RequestParam List<String> countries) {

        log.info("Received: {}", Map.of("function", function, "from", from, "to", to, "countries", countries));
        return CalculatedResult.builder()
                .confirmed(165)
                .date(LocalDate.of(2020, 3,1))
                .country("FI")
                .build();
    }

}
