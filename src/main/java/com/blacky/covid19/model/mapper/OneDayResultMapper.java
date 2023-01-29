package com.blacky.covid19.model.mapper;

import com.blacky.covid19.http.model.CovidOneDayResult;
import com.blacky.covid19.model.OneDayResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OneDayResultMapper {

    OneDayResultMapper MAPPER = Mappers.getMapper(OneDayResultMapper.class);

    @Mapping(source = "date", target = "date", dateFormat = "yyyy.MM.dd")
    OneDayResult covidOneDayResultToOneDayResult(CovidOneDayResult covidOneDayResult);

}
