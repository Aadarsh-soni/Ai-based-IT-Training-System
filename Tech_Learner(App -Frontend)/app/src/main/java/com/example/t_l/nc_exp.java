package com.example.t_l;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class nc_exp extends AppCompatActivity {

    private final ApiService apiServiceForBackend =  RetrofitClint.getClientForBackend().create(ApiService.class);

    User user = new User();
    UserPreferenceForm userPreferenceForm = new UserPreferenceForm();
    List<Video> videoResponseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nc_exp);

        Button novice = findViewById(R.id.button10);
        Button moderate = findViewById(R.id.button11);
        Button proficient = findViewById(R.id.button12);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            System.out.println("name : "+user.getFirstName());
            userPreferenceForm = (UserPreferenceForm) intent.getSerializableExtra("userPreferenceForm");
            System.out.println("name : "+userPreferenceForm.getRole());
        }

        novice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPreferenceForm.setExperience("beginner");
                userPreferenceForm.setEmpId(user.getEmpId());
                System.out.println("Saving user preference 1 NC");
                Call<BaseResponse> apiResponseCall = apiServiceForBackend.saveUserPreference(userPreferenceForm);
                apiResponseCall.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            BaseResponse baseResponse = response.body();
                            System.out.println("api response "+baseResponse.getStatus());
                            openOnClick(user, userPreferenceForm);
                        } else {
                            String errorBody = response.errorBody().toString();
                            Toast.makeText(nc_exp.this, "Error: " + errorBody, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        // Handle failure/error in making the API call
                        Toast.makeText(nc_exp.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace(); // Print the error stack trace for debugging
                    }
                });
            }
        });

        moderate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPreferenceForm.setExperience("intermediate");
                userPreferenceForm.setEmpId(user.getEmpId());
                System.out.println("Saving user preference 1 NC");
                Call<BaseResponse> apiResponseCall = apiServiceForBackend.saveUserPreference(userPreferenceForm);
                apiResponseCall.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            BaseResponse baseResponse = response.body();
                            System.out.println("api response "+baseResponse.getStatus());
                            openOnClick(user, userPreferenceForm);
                        } else {
                            String errorBody = response.errorBody().toString();
                            Toast.makeText(nc_exp.this, "Error: " + errorBody, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        // Handle failure/error in making the API call
                        Toast.makeText(nc_exp.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace(); // Print the error stack trace for debugging
                    }
                });
            }
        });

        proficient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPreferenceForm.setExperience("advanced");
                userPreferenceForm.setEmpId(user.getEmpId());
                System.out.println("Saving user preference 1 NC");
                Call<BaseResponse> apiResponseCall = apiServiceForBackend.saveUserPreference(userPreferenceForm);
                apiResponseCall.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            BaseResponse baseResponse = response.body();
                            System.out.println("api response "+baseResponse.getStatus());
                            openOnClick(user, userPreferenceForm);
                        } else {
                            String errorBody = response.errorBody().toString();
                            Toast.makeText(nc_exp.this, "Error: " + errorBody, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        // Handle failure/error in making the API call
                        Toast.makeText(nc_exp.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace(); // Print the error stack trace for debugging
                    }
                });
            }
        });
    }

    private void openOnClick(User user, UserPreferenceForm userPreferenceForm) {
        Intent intent = new Intent(this, net_vi.class);
        intent.putExtra("userPreferenceForm", userPreferenceForm);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}