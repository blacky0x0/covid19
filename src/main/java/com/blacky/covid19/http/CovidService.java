package com.blacky.covid19.http;

import com.blacky.covid19.http.model.CovidCountry;
import com.blacky.covid19.http.model.CovidOneDayResult;
import com.blacky.covid19.util.Utils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CovidService {
    private final Covid19ApiClient httpService;

    public CovidService(Covid19ApiClient httpService) {
        this.httpService = httpService;
    }

    @SneakyThrows
    public List<CovidCountry> getCountries() {
        var call = httpService.getCountries();
        var response = call.execute();

        if (!response.isSuccessful()) {
            log.error("3rd-party service unavailable. Details: code={} body={}", response.code(), response.errorBody());
            throw new RuntimeException("Service unavailable. Try again later");
        }

        return response.body();
    }

    @SneakyThrows
    public List<CovidOneDayResult> getConfirmedCasesInPeriod(LocalDate fromDate, LocalDate toDate, List<String> countries) {
        String from = Utils.convertWithZeroHourUtc(fromDate);
        String to = toDate.equals(fromDate)
                ? Utils.convertWithFirstHourUtc(toDate)
                : Utils.convertWithZeroHourUtc(toDate);
        
        if (fromDate.isAfter(toDate)) {
            throw new RuntimeException("The start date cannot occur after the end date");
        }
        
        var responses = new ArrayList<List<CovidOneDayResult>>();
        var futures = new ArrayList<CompletableFuture<List<CovidOneDayResult>>>();
        countries.forEach(country -> futures.add(httpService.getConfirmedCasesInPeriod(country, from, to)));
        for (var future : futures) {
            responses.add(future.get());
        }
        
        return responses.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
