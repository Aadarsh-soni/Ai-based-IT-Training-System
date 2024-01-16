package com.example.t_l;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class youtube extends AppCompatActivity {
    User user = new User();
    Video video = new Video();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        final Button button = findViewById(R.id.button14);
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("user")) {
            // Retrieve the object from the intent
            user = (User) intent.getSerializableExtra("user");
            video = (Video) intent.getSerializableExtra("video");
        }

        WebView webView;


        webView = findViewById(R.id.webview_youtube);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String videoId = video.getVideoId(); // Replace with the YouTube video ID
        String frameVideo = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"
                + videoId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        webView.loadData(frameVideo, "text/html", "utf-8");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openonclick1(user,video);
            }
        });



        
    }

    private void openonclick1(User user, Video video) {
        Intent intent = new Intent(this, test.class);
        intent.putExtra("user", user);
        intent.putExtra("video", video);
        // Use "user" instead of "User"
        startActivity(intent);
    }
    }
