package com.blacky.covid19.service;

import com.blacky.covid19.http.CovidService;
import com.blacky.covid19.model.CalculatedResult;
import com.blacky.covid19.model.Country;
import com.blacky.covid19.model.OneDayResult;
import com.blacky.covid19.model.mapper.CountryMapper;
import com.blacky.covid19.model.mapper.OneDayResultMapper;
import com.blacky.covid19.repository.CountryRepository;
import com.blacky.covid19.web.model.CalcFunction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FacadeService {

    private final CovidService covidService;
    private final CountryRepository countryRepository;

    public List<Country> getCountries() {
        List<Country> countries;

        if (countryRepository.count() == 0) {
            countries = covidService.getCountries().stream()
                    .map(CountryMapper.MAPPER::covidCountryToCountry)
                    .collect(Collectors.toList());
            countryRepository.saveAll(countries);
        } else {
            countries = countryRepository.findAll();
        }

        return countries;
    }

    public CalculatedResult getConfirmedCasesInPeriod(CalcFunction function,
                                                      LocalDate fromDate,
                                                      LocalDate toDate,
                                                      List<String> countries) {

        validateCountryNames(countries);

        OneDayResult ans = covidService.getConfirmedCasesInPeriod(fromDate, toDate, countries)
                .stream()
                .map(OneDayResultMapper.MAPPER::covidOneDayResultToOneDayResult)
                .reduce(function.getCasesFunction())
                .orElseThrow(() -> new IllegalArgumentException("No results"));

        return CalculatedResult.builder()
                .confirmed(ans.getCases())
                .date(ans.getDate())
                .country(ans.getCountryCode())
                .build();
    }

    private void validateCountryNames(List<String> names) {
        var variations = getCountries().stream()
                .map(c -> List.of(c.getCountry(), c.getSlug(), c.getIso2()))
                .flatMap(Collection::stream)
                .toList();

        for (var name : names) {
            if (!variations.contains(name)) {
                throw new IllegalArgumentException(String.format("Country name '%s' not found", name));
            }
        }
    }

}
