package com.example.mydrivertracking.api;

import com.example.mydrivertracking.models.AuthResponse;
import com.example.mydrivertracking.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/auth/registro")
    Call<AuthResponse> registro(@Body User body);

    @POST("api/auth/login")
    Call<AuthResponse> login(@Body User body);
}
