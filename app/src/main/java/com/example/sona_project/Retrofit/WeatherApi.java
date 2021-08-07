package com.example.sona_project.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("weather?appid=03bc734cbfed390429669597db46ff2e&units=metric")
    Call<WeatherData> getWheather (@Query("q") String city);


}
