package com.example.t_l;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class andro_vi extends AppCompatActivity {

    private final ApiService apiServiceForBackend = RetrofitClint.getClientForBackend().create(ApiService.class);

    User user = new User();
    UserPreferenceForm userPreferenceForm = new UserPreferenceForm();
    BaseResponse videoResponse = new BaseResponse();

    List<Video> videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andro_vi);


        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            userPreferenceForm = (UserPreferenceForm) intent.getSerializableExtra("userPreferenceForm");
        } else {
            // Handle case where the intent does not contain the expected extra
        }

        CardView[] cardViews = new CardView[]{
                findViewById(R.id.card1),
                findViewById(R.id.card2),
                findViewById(R.id.card3),
                findViewById(R.id.card4),
                findViewById(R.id.card5)
        };

        Call<BaseResponse> videoResponseCall = apiServiceForBackend.generateDynamicVideoContent(userPreferenceForm);
        videoResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    videoResponse = response.body();
                    Gson gson = new Gson();
                    VideoResponse videoList = gson.fromJson(gson.toJson(videoResponse.getResponse()), VideoResponse.class);

                    TextView[] textViews = new TextView[]{
                            findViewById(R.id.textView0),
                            findViewById(R.id.textView1),
                            findViewById(R.id.textView2),
                            findViewById(R.id.textView3),
                            findViewById(R.id.textView4)
                    };

                    videos = videoList.getVideos();
                    RequestOptions requestOptions = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.placeholder) // Placeholder image while loading
                            .error(R.drawable.error_image); // Image to show if loading fails

                    for (int i = 0; i < cardViews.length && i < videos.size(); i++) {
                        Video currentVideo = videos.get(i);
                        CardView currentCardView = cardViews[i];
                        textViews[i].setText(videos.get(i).getVideoTitle());
                        Glide.with(andro_vi.this)
                                .load(currentVideo.getThumbnailUrl())
                                .apply(requestOptions)
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        currentCardView.setBackground(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                        // Remove any placeholder if needed
                                    }

                                    @Override
                                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                        // Handle failure to load the image
                                    }
                                });

                    }


                } else {
                    // Handle unsuccessful API response
                    Toast.makeText(andro_vi.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                // Handle failure/error in making the API call
                Toast.makeText(andro_vi.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace(); // Print the error stack trace for debugging
            }
        });

        UserCourseForm userCourseForm = new UserCourseForm();
        userCourseForm.setEmpId(user.getEmpId());
        cardViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCourseForm.setCourseName(videos.get(0).getVideoTitle());
                userCourseForm.setCourseUrl(videos.get(0).getVideoUrl());
                userCourseForm.setStatus("Pending");
                userCourseForm.setVideoId(videos.get(0).getVideoTitle());
                userCourseForm.setThumbnailUrl(videos.get(0).getThumbnailUrl());
                userCourseForm.setVideoTitle(videos.get(0).getVideoTitle());
                System.out.println("before saving user course 2 : "+userCourseForm);
                Call<BaseResponse> saveUserCourseCall = apiServiceForBackend.saveUserCourse(userCourseForm);
                saveUserCourseCall.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            BaseResponse baseResponse = response.body();
                            System.out.println("success");
                            openWelcomeActivity(user,videos.get(0));
                        } else {
                            System.out.println(""+response.errorBody().toString());
                            String errorBody = response.errorBody().toString();
                            Toast.makeText(andro_vi.this, "Error: " + errorBody, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        // Handle failure/error in making the API call
                        Toast.makeText(andro_vi.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace(); // Print the error stack trace for debugging
                    }
                });
            }
        });
        cardViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCourseForm.setCourseName(videos.get(1).getVideoTitle());
                userCourseForm.setCourseUrl(videos.get(1).getVideoUrl());
                userCourseForm.setStatus("Pending");
                userCourseForm.setVideoId(videos.get(1).getVideoTitle());
                userCourseForm.setThumbnailUrl(videos.get(1).getThumbnailUrl());
                userCourseForm.setVideoTitle(videos.get(1).getVideoTitle());
                System.out.println("before saving user course 2 : "+userCourseForm);
                Call<BaseResponse> saveUserCourseCall = apiServiceForBackend.saveUserCourse(userCourseForm);
                saveUserCourseCall.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            BaseResponse baseResponse = response.body();
                            System.out.println("success");
                            openWelcomeActivity(user,videos.get(1));
                        } else {
                            System.out.println(""+response.errorBody().toString());
                            String errorBody = response.errorBody().toString();
                            Toast.makeText(andro_vi.this, "Error: " + errorBody, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        // Handle failure/error in making the API call
                        Toast.makeText(andro_vi.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace(); // Print the error stack trace for debugging
                    }
                });
            }
        });
        cardViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCourseForm.setCourseName(videos.get(2).getVideoTitle());
                userCourseForm.setCourseUrl(videos.get(2).getVideoUrl());
                userCourseForm.setStatus("Pending");
                userCourseForm.setVideoId(videos.get(2).getVideoTitle());
                userCourseForm.setThumbnailUrl(videos.get(2).getThumbnailUrl());
                userCourseForm.setVideoTitle(videos.get(2).getVideoTitle());
                System.out.println("before saving user course 3 : "+userCourseForm);
                Call<BaseResponse> saveUserCourseCall = apiServiceForBackend.saveUserCourse(userCourseForm);
                saveUserCourseCall.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            BaseResponse baseResponse = response.body();
                            System.out.println("success");
                            openWelcomeActivity(user,videos.get(2));
                        } else {
                            System.out.println(""+response.errorBody().toString());
                            String errorBody = response.errorBody().toString();
                            Toast.makeText(andro_vi.this, "Error: " + errorBody, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        // Handle failure/error in making the API call
                        Toast.makeText(andro_vi.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace(); // Print the error stack trace for debugging
                    }
                });
            }
        });
        cardViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCourseForm.setCourseName(videos.get(3).getVideoTitle());
                userCourseForm.setCourseUrl(videos.get(3).getVideoUrl());
                userCourseForm.setStatus("Pending");
                userCourseForm.setVideoId(videos.get(3).getVideoTitle());
                userCourseForm.setThumbnailUrl(videos.get(3).getThumbnailUrl());
                userCourseForm.setVideoTitle(videos.get(3).getVideoTitle());
                System.out.println("before saving user course 4 : "+userCourseForm);
                Call<BaseResponse> saveUserCourseCall = apiServiceForBackend.saveUserCourse(userCourseForm);
                saveUserCourseCall.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            BaseResponse baseResponse = response.body();
                            System.out.println("success");
                            openWelcomeActivity(user,videos.get(3));
                        } else {
                            System.out.println(""+response.errorBody().toString());
                            String errorBody = response.errorBody().toString();
                            Toast.makeText(andro_vi.this, "Error: " + errorBody, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        // Handle failure/error in making the API call
                        Toast.makeText(andro_vi.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace(); // Print the error stack trace for debugging
                    }
                });
            }
        });
        cardViews[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCourseForm.setCourseName(videos.get(4).getVideoTitle());
                userCourseForm.setCourseUrl(videos.get(4).getVideoUrl());
                userCourseForm.setStatus("Pending");
                userCourseForm.setVideoId(videos.get(4).getVideoTitle());
                userCourseForm.setThumbnailUrl(videos.get(4).getThumbnailUrl());
                userCourseForm.setVideoTitle(videos.get(4).getVideoTitle());
                System.out.println("before saving user course 5 : "+userCourseForm);
                Call<BaseResponse> saveUserCourseCall = apiServiceForBackend.saveUserCourse(userCourseForm);
                saveUserCourseCall.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            BaseResponse baseResponse = response.body();
                            System.out.println("success");
                            openWelcomeActivity(user,videos.get(4));
                        } else {
                            System.out.println(""+response.errorBody().toString());
                            String errorBody = response.errorBody().toString();
                            Toast.makeText(andro_vi.this, "Error: " + errorBody, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        // Handle failure/error in making the API call
                        Toast.makeText(andro_vi.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace(); // Print the error stack trace for debugging
                    }
                });
            }
        });
    }

    private void openWelcomeActivity(User user, Video video) {
        Intent intent = new Intent(this, youtube.class);
        intent.putExtra("user", user);
        intent.putExtra("video", video);
        // Use "user" instead of "User"
        startActivity(intent);
    }
}