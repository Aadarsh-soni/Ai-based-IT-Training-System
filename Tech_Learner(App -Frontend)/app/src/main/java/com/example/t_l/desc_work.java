package com.example.t_l;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

public class desc_work extends AppCompatActivity {

    User user = new User();
    UserPreferenceForm userPreferenceForm = new UserPreferenceForm();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_work);

        final Button button = findViewById(R.id.button2);
        final Button button2 = findViewById(R.id.button3);
        final Button button3 = findViewById(R.id.button4);
        final Button button4 = findViewById(R.id.button5);

        Intent intent = getIntent();


        if (intent != null && intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            System.out.println("name : "+user.getFirstName());
        }
        Button myButton = findViewById(R.id.button2); // Replace my_button_id with your button's ID
        String buttonText = myButton.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPreferenceForm.setRole(button.getText().toString());
                openonclick(user, userPreferenceForm);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPreferenceForm.setRole(button2.getText().toString());
                openonclick2(user, userPreferenceForm);
            }

        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPreferenceForm.setRole(button3.getText().toString());
                openonclick3(user, userPreferenceForm);
            }

        });
        button4.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   userPreferenceForm.setRole(button4.getText().toString());
                   openonclick4(user, userPreferenceForm);
               }
        });
    }


    public void openonclick(User user, UserPreferenceForm userPreferenceForm) {
        Intent intent = new Intent(this, net_engi.class);
        System.out.println("Role : "+userPreferenceForm.getRole());
        intent.putExtra("userPreferenceForm", userPreferenceForm);
        intent.putExtra("user", user);
        startActivity(intent);

    }

    public void openonclick2(User user, UserPreferenceForm userPreferenceForm) {
        Intent intent = new Intent(this, and_dev.class);
        intent.putExtra("user", user);
        intent.putExtra("userPreferenceForm", userPreferenceForm);
        startActivity(intent);

    }

    public void openonclick3(User user, UserPreferenceForm userPreferenceForm) {
        Intent intent = new Intent(this, ai_devsel.class);
        intent.putExtra("user", user);
        intent.putExtra("userPreferenceForm", userPreferenceForm);
        startActivity(intent);

    }

    public void openonclick4(User user, UserPreferenceForm userPreferenceForm) {
        Intent intent = new Intent(this, cyber_sec.class);
        intent.putExtra("user", user);
        intent.putExtra("userPreferenceForm", userPreferenceForm);
        startActivity(intent);
    }
};