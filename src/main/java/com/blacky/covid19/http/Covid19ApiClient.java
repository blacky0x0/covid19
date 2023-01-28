package com.blacky.covid19.http;

import com.blacky.covid19.http.model.CovidCountry;
import com.blacky.covid19.http.model.CovidOneDayResult;
import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

@Component
@RetrofitClient(baseUrl = "https://api.covid19api.com/")
public interface Covid19ApiClient {

    @GET("countries")
    Call<List<CovidCountry>> getCountries();

    @GET("country/{country}/status/confirmed")
    Call<List<CovidOneDayResult>> getConfirmedCasesInPeriod(@Path("country") String country,
                                                            @Query("from") String from,
                                                            @Query("to") String to);

}
