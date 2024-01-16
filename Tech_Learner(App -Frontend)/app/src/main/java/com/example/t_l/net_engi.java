package com.example.t_l;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class net_engi extends AppCompatActivity {
    User user = new User();
    UserPreferenceForm userPreferenceForm = new UserPreferenceForm();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_engi);
        final Button button8 = findViewById(R.id.button8);
        final Button button9 = findViewById(R.id.button9);
        final Button button7 = findViewById(R.id.button7);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            System.out.println("name : "+user.getFirstName());
            userPreferenceForm = (UserPreferenceForm) intent.getSerializableExtra("userPreferenceForm");
            System.out.println("name : "+userPreferenceForm.getRole());
        } else {
            // Handle case where the intent does not contain the expected extra
        }

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPreferenceForm.setTechnology(button8.getText().toString());
                openonclick1(user, userPreferenceForm);
            }
                    });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPreferenceForm.setTechnology(button9.getText().toString());
                openonclick2(user, userPreferenceForm);
            }
        })
    ;
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPreferenceForm.setTechnology(button7.getText().toString());
                openonclick3(user, userPreferenceForm);
            }
        });
    }


    public void openonclick1(User user, UserPreferenceForm userPreferenceForm){
        Intent intent = new Intent(this, cn_exp.class);
        intent.putExtra("userPreferenceForm", userPreferenceForm);
        intent.putExtra("user", user);
        startActivity(intent);

    }
    public void openonclick2(User user, UserPreferenceForm userPreferenceForm){
        Intent intent = new Intent(this, nc_exp.class);
        intent.putExtra("userPreferenceForm", userPreferenceForm);
        intent.putExtra("user", user);
        startActivity(intent);

    }
    public void openonclick3(User user, UserPreferenceForm userPreferenceForm){
        Intent intent = new Intent(this, nc_exp.class);
        intent.putExtra("userPreferenceForm", userPreferenceForm);
        intent.putExtra("user", user);
        startActivity(intent);

    }};