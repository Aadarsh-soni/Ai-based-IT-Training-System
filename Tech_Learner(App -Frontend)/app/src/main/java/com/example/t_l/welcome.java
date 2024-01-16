package com.example.t_l;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class welcome extends AppCompatActivity {
    TextView textView;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();


        if (intent != null && intent.hasExtra("user")) {
            // Retrieve the object from the intent
            user = (User) intent.getSerializableExtra("user");

            if (user != null) {
                System.out.println("name : " + user.getFirstName());
                TextView textView = findViewById(R.id.textView18);
                String text = user.getFirstName(); // Use getName() method to get the username
                textView.setText(text); // Set the text to the TextView
            }
        } else {
            // Handle case where the intent does not contain the expected extra
        }

        final Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openonclick(user);
            }





        });

    }

    public void openonclick(User user){

        Intent intent = new Intent(this, desc_work.class);
        intent.putExtra("user", user);
        startActivity(intent);

    }


};


