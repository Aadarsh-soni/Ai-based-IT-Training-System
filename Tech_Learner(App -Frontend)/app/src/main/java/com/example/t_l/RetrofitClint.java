package com.example.t_l;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClint {
    private static final String BASE_URL_BACKEND = "http://10.0.2.2:3333/tech-learner/user/"; // Replace with your base URL

    private static Retrofit retrofit = null;

    public static Retrofit getClientForBackend() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_BACKEND)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
