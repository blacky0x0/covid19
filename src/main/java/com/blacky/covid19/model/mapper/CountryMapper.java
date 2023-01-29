package com.blacky.covid19.model.mapper;

import com.blacky.covid19.http.model.CovidCountry;
import com.blacky.covid19.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CountryMapper {

    CountryMapper MAPPER = Mappers.getMapper(CountryMapper.class);

    Country covidCountryToCountry(CovidCountry covidCountry);

}
