package com.blacky.covid19.service;

import com.blacky.covid19.http.Covid19ApiClient;
import com.blacky.covid19.http.model.CovidOneDayResult;
import com.blacky.covid19.model.CalculatedResult;
import com.blacky.covid19.model.Country;
import com.blacky.covid19.web.model.CalcFunction;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacadeService {

    private final static LocalTime ZERO_HOUR = LocalTime.of(0, 0, 0);
    private final static LocalTime FIRST_HOUR = LocalTime.of(1, 0, 0);
    private final Covid19ApiClient httpService;

    public FacadeService(Covid19ApiClient httpService) {
        this.httpService = httpService;
    }

    @SneakyThrows
    public List<Country> getCountries() {
        var list = httpService.getCountries().execute().body();
        return list.stream()
                .map(c -> new Country(c.getCountry(), c.getSlug(), c.getIso2()))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public CalculatedResult getConfirmedCasesInPeriod(CalcFunction function, LocalDate fromLocal, LocalDate toLocal, List<String> countries, String uri) {
        ZonedDateTime fromZdt = ZonedDateTime.of(fromLocal, ZERO_HOUR, ZoneOffset.UTC);
        ZonedDateTime toZdt = fromLocal.equals(toLocal)
                ? ZonedDateTime.of(toLocal, FIRST_HOUR, ZoneOffset.UTC)
                : ZonedDateTime.of(toLocal, ZERO_HOUR, ZoneOffset.UTC);

        String from = fromZdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        String to = toZdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        
        List<List<CovidOneDayResult>> responses = new ArrayList<>();
        for (String country : countries) {
            responses.add(httpService
                    .getConfirmedCasesInPeriod(country, from, to)
                    .execute()
                    .body());
        }

        CovidOneDayResult ans;
        switch (function) {
            case MIN -> ans = responses.stream()
                    .flatMap(Collection::stream)
                    .min(Comparator.comparing(CovidOneDayResult::getCases))
                    .orElseThrow(() -> new RuntimeException("min not found"));
            case MAX -> ans = responses.stream()
                    .flatMap(Collection::stream)
                    .max(Comparator.comparing(CovidOneDayResult::getCases))
                    .orElseThrow(() -> new RuntimeException("max not found"));
            default -> throw new IllegalStateException("Function is not supported yet");
        }

        return CalculatedResult.builder()
                .confirmed(ans.getCases())
                .date(ans.getDate().toLocalDate())
                .country(ans.getCountryCode())
                .build();
    }
}
