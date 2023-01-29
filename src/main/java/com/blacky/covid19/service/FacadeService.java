package com.blacky.covid19.service;

import com.blacky.covid19.http.CovidService;
import com.blacky.covid19.http.model.CovidOneDayResult;
import com.blacky.covid19.model.CalculatedResult;
import com.blacky.covid19.model.Country;
import com.blacky.covid19.model.OneDayResult;
import com.blacky.covid19.model.mapper.CountryMapper;
import com.blacky.covid19.model.mapper.OneDayResultMapper;
import com.blacky.covid19.web.model.CalcFunction;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacadeService {

    private final CovidService covidService;

    public FacadeService(CovidService covidService) {
        this.covidService = covidService;
    }

    @SneakyThrows
    public List<Country> getCountries() {
        return covidService.getCountries().stream()
                .map(CountryMapper.MAPPER::covidCountryToCountry)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public CalculatedResult getConfirmedCasesInPeriod(CalcFunction function, LocalDate fromDate, LocalDate toDate, List<String> countries, String uri) {
        List<CovidOneDayResult> list = covidService.getConfirmedCasesInPeriod(fromDate, toDate, countries);

        OneDayResult ans = list.stream()
                .map(OneDayResultMapper.MAPPER::covidOneDayResultToOneDayResult)
                .reduce(function.getCasesFunction())
                .orElseThrow(() -> new RuntimeException("No results"));

        return CalculatedResult.builder()
                .confirmed(ans.getCases())
                .date(ans.getDate())
                .country(ans.getCountryCode())
                .build();
    }
}
