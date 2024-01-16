package com.example.t_l;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class login extends AppCompatActivity {

    private ApiService apiService =  RetrofitClint.getClientForBackend().create(ApiService.class);;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tlink-524b7-default-rtdb.firebaseio.com/");

    UserPreference userPreference = new UserPreference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userID = findViewById(R.id.editTextTextPersonName);
        final EditText password = findViewById(R.id.editTextTextPersonName2);
        final Button loginButton = findViewById(R.id.button);



        Gson g = new Gson();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = userID.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (id.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(login.this, "Please enter both user ID and password", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference employeeReference = databaseReference.child("employee");

                    employeeReference.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//

                             try {
                                 System.out.println("id : "+id+"  pwd : "+pass);
                                 LoginForm loginForm = new LoginForm();
                                 loginForm.setUsername(id);
                                 loginForm.setPassword(pass);

                                 Call<BaseResponse> apiResponseCall = apiService.getUser(loginForm);
                                 apiResponseCall.enqueue(new Callback<BaseResponse>() {
                                     @Override
                                     public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                         if (response.isSuccessful()) {
                                             User user;
                                             BaseResponse baseResponse = response.body();
                                             if (baseResponse != null && baseResponse.getStatus() == 200) {
                                                 user = g.fromJson(g.toJson(baseResponse.getResponse()), User.class);
                                                 Call<BaseResponse> userPreferenceCall = apiService.getUserPreference(user.getEmpId());
                                                 userPreferenceCall.enqueue(new Callback<BaseResponse>() {
                                                     @Override
                                                     public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                                         BaseResponse baseResponse = response.body();
                                                         System.out.println(response);
                                                         Gson gson = new Gson();
                                                         userPreference = gson.fromJson(gson.toJson(baseResponse.getResponse()), UserPreference.class);
                                                         if (userPreference != null) {
                                                             openDashboardActivity(user, userPreference);
//                                                             openWelcomeActivity(user);
                                                         }
                                                     }

                                                     @Override
                                                     public void onFailure(Call<BaseResponse> call, Throwable t) {

                                                     }
                                                 });
                                                 Toast.makeText(login.this, "" + baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                                 openWelcomeActivity(user);
                                             }
                                         } else {
                                             // Handle unsuccessful response (HTTP error)
//                                             String errorBody = response.errorBody().toString();
//                                             Toast.makeText(login.this, "Error: " + errorBody, Toast.LENGTH_SHORT).show();
                                             Toast.makeText(login.this, "ERROR", Toast.LENGTH_SHORT).show();
                                         }
                                     }

                                     @Override
                                     public void onFailure(Call<BaseResponse> call, Throwable t) {
                                         // Handle failure/error in making the API call
                                         Toast.makeText(login.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                         t.printStackTrace(); // Print the error stack trace for debugging
                                     }
                                 });
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }


                         }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(login.this, "Failed to read data.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void openDashboardActivity(User user, UserPreference userPreference) {
        Intent intent = new Intent(this, dashboard.class);
        intent.putExtra("user", user);
        System.out.println("user ref "+userPreference);
        intent.putExtra("userPreference", userPreference);
        // Use "user" instead of "User"
        startActivity(intent);
    }


    public void openWelcomeActivity(User user) {
        Intent intent = new Intent(this, welcome.class);
        intent.putExtra("user", user); // Use "user" instead of "User"
        startActivity(intent);
    }

}
