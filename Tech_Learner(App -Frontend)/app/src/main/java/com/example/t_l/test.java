package com.example.t_l;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class test extends AppCompatActivity {

    private ApiService apiService =  RetrofitClint.getClientForBackend().create(ApiService.class);

    User user = new User();
    Video video = new Video();

    Question question = new Question();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_test);
        final Button button = findViewById(R.id.button25);
        final Button button1 = findViewById(R.id.button26);
        final Button button2 = findViewById(R.id.button28);
        final Button button3 = findViewById(R.id.button29);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("user")) {
            // Retrieve the object from the intent
            user = (User) intent.getSerializableExtra("user");
            video = (Video) intent.getSerializableExtra("video");
        }

        Call<BaseResponse> apiResponseCall = apiService.generateQuestions();
        apiResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    Gson gson = new Gson();
                    Question question = gson.fromJson(gson.toJson(baseResponse.getResponse()), Question.class);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

        // Set onClickListeners for each button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerAndNavigate("A");
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerAndNavigate("B");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerAndNavigate("C");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerAndNavigate("D");
            }
        });
    }

    private void checkAnswerAndNavigate(String selectedAnswer) {
        // Replace "CorrectAnswer" with the correct answer for the current question
        String correctAnswer = "CorrectAnswer";

        if (selectedAnswer.equals(correctAnswer)) {
            // Correct answer, navigate to the new activity
            Intent intent = new Intent(this, test2.class);
            startActivity(intent);
        } else {
            // Incorrect answer, you can handle this case if needed
        }
    }
}

