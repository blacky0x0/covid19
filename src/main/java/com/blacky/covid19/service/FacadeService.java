package com.blacky.covid19.service;

import com.blacky.covid19.http.Covid19ApiClient;
import com.blacky.covid19.http.model.CovidOneDayResult;
import com.blacky.covid19.model.CalculatedResult;
import com.blacky.covid19.model.Country;
import com.blacky.covid19.model.OneDayResult;
import com.blacky.covid19.model.mapper.CountryMapper;
import com.blacky.covid19.model.mapper.OneDayResultMapper;
import com.blacky.covid19.util.Utils;
import com.blacky.covid19.web.model.CalcFunction;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacadeService {

    private final Covid19ApiClient httpService;

    public FacadeService(Covid19ApiClient httpService) {
        this.httpService = httpService;
    }

    @SneakyThrows
    public List<Country> getCountries() {
        var list = httpService.getCountries().execute().body();
        return list.stream()
                .map(CountryMapper.MAPPER::covidCountryToCountry)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public CalculatedResult getConfirmedCasesInPeriod(CalcFunction function, LocalDate fromDate, LocalDate toDate, List<String> countries, String uri) {
        String from = Utils.convertWithZeroHourUtc(fromDate);
        String to = toDate.equals(fromDate) 
                ? Utils.convertWithFirstHourUtc(toDate) 
                : Utils.convertWithZeroHourUtc(toDate);

        List<List<CovidOneDayResult>> responses = new ArrayList<>();
        for (String country : countries) {
            responses.add(httpService
                    .getConfirmedCasesInPeriod(country, from, to)
                    .execute()
                    .body());
        }

        OneDayResult ans = responses.stream()
                .flatMap(Collection::stream)
                .map(OneDayResultMapper.MAPPER::covidOneDayResultToOneDayResult)
                .reduce(function.getCasesFunction())
                .orElseThrow(() -> new RuntimeException("No result"));

        return CalculatedResult.builder()
                .confirmed(ans.getCases())
                .date(ans.getDate())
                .country(ans.getCountryCode())
                .build();
    }
}
