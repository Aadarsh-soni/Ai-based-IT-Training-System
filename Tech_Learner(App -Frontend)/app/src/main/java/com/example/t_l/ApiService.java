package com.example.t_l;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("login")
    Call<BaseResponse> getUser(@Body LoginForm loginForm);

    @POST("saveUserPreference")
    Call<BaseResponse> saveUserPreference(@Body UserPreferenceForm userPreferenceForm);

    @POST("saveUserCourse")
    Call<BaseResponse> saveUserCourse(@Body UserCourseForm userCourseForm);

    @POST("generateDynamicVideoContent")
    Call<BaseResponse> generateDynamicVideoContent(@Body UserPreferenceForm userPreferenceForm);

    @GET("getPendingUserCourse/{empId}")
    Call<BaseResponse> getPendingUserCourse(@Path("empId") String empId);

    @GET("getCompletedUserCourse/{empId}")
    Call<BaseResponse> getCompletedUserCourse(@Path("empId") String empId);

    @GET("getUserPreference/{empId}")
    Call<BaseResponse> getUserPreference(@Path("empId") String empId);

    @GET("generateQuestions")
    Call<BaseResponse> generateQuestions();
}
