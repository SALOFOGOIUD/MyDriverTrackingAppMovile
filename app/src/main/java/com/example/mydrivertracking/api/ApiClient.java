package com.example.mydrivertracking.api;

import android.content.Context;

import com.example.mydrivertracking.utils.SessionManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static volatile Retrofit retrofit = null;

    private static final String BASE_URL = "https://backmydrivertracking.onrender.com/";

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            synchronized (ApiClient.class) {
                if (retrofit == null) {
                    HttpLoggingInterceptor log = new HttpLoggingInterceptor();
                    log.setLevel(HttpLoggingInterceptor.Level.BODY);

                    Context appCtx = context.getApplicationContext();

                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(log)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .addInterceptor(chain -> {
                                Request original = chain.request();
                                String token = new SessionManager(appCtx).getToken();
                                Request.Builder builder = original.newBuilder();
                                if (token != null && !token.isEmpty()) {
                                    builder.header("Authorization", "Bearer " + token);
                                }
                                return chain.proceed(builder.build());
                            })
                            .build();

                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
