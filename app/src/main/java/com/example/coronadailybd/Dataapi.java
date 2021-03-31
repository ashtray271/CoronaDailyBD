package com.example.coronadailybd;

import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Dataapi {





    @GET("live/country/{y}/status/confirmed/date/{s}")
    Call<List<Data>> getdata(
            @Path("s") String t,
            @Path("y") String yu

    );

    @GET("live/country/{r}")
    Call<List<Data>> getda(
            @Path("r") String g
    );

    

}
