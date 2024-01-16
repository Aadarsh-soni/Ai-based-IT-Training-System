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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class dashboard extends AppCompatActivity {

    private final ApiService apiServiceForBackend =  RetrofitClint.getClientForBackend().create(ApiService.class);

    User user = new User();
    Video video = new Video();
    UserPreferenceForm userPreferenceForm = new UserPreferenceForm();
    UserPreference userPreference = new UserPreference();
    BaseResponse videoResponse = new BaseResponse();
    List<Video> videos;

    List<UserCourse> userPendingCourseList = new ArrayList<>();
    List<UserCourse> userCompletedCourseList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            userPreference = (UserPreference) intent.getSerializableExtra("userPreference");
            System.out.println("intent user preference "+userPreference.getExperience());
        }

        CardView[] pendingCourses = new CardView[]{
                findViewById(R.id.card1),
                findViewById(R.id.card2)
        };

        CardView[] completedCourses = new CardView[]{
                findViewById(R.id.card10),
                findViewById(R.id.card11),
                findViewById(R.id.card12),
                findViewById(R.id.card13)
        };

        CardView[] recommendedCourses = new CardView[]{
                findViewById(R.id.card20),
                findViewById(R.id.card21),
                findViewById(R.id.card22),
                findViewById(R.id.card23),
                findViewById(R.id.card24)
        };

        TextView textView1 = findViewById(R.id.textView24);
        TextView textView2 = findViewById(R.id.textView22);
        TextView textView3 = findViewById(R.id.textView25);
        textView1.setText(userPreference.getRole());
        textView2.setText(userPreference.getTechnology());
        textView3.setText(userPreference.getExperience());

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder) // Placeholder image while loading
                .error(R.drawable.error_image);



        Call<BaseResponse> pendingUserCourseCall = apiServiceForBackend.getPendingUserCourse(user.getEmpId());
        pendingUserCourseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    userPendingCourseList = (List<UserCourse>) baseResponse.getResponse();
                    System.out.println("pending course "+userPendingCourseList.size());
                    System.out.println("before first for loop "+userPendingCourseList.size());
                    if(userPendingCourseList != null) {
                        for (int i = 0; i < userPendingCourseList.size(); i++) {
                            CardView currentCard = pendingCourses[i];
                            Gson gson = new Gson();
                            UserCourse currentCourse = gson.fromJson(gson.toJson(userPendingCourseList.get(i)), UserCourse.class);
                            System.out.println("current course : " + currentCourse.getThumbnailUrl());
                            Glide.with(dashboard.this)
                                    .load(currentCourse.getThumbnailUrl())
                                    .apply(requestOptions)
                                    .into(new CustomTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            currentCard.setBackground(resource);
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
                    }
                } else {
                    System.out.println(""+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace(); // Print the error stack trace for debugging
            }
        });

        Call<BaseResponse> completedUserCourseCall = apiServiceForBackend.getCompletedUserCourse(user.getEmpId());
        completedUserCourseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    userCompletedCourseList = (List<UserCourse>) baseResponse.getResponse();
                    if(userCompletedCourseList != null) {
                        for (int i = 0; i < userCompletedCourseList.size(); i++) {
                            CardView currentCard = completedCourses[i];
                            Gson gson = new Gson();
                            UserCourse currentCourse = gson.fromJson(gson.toJson(userCompletedCourseList.get(i)), UserCourse.class);
                            System.out.println("current course : " + currentCourse.getThumbnailUrl());
                            Glide.with(dashboard.this)
                                    .load(currentCourse.getThumbnailUrl())
                                    .apply(requestOptions)
                                    .into(new CustomTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            currentCard.setBackground(resource);
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
                    }
                } else {
                    System.out.println(""+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace(); // Print the error stack trace for debugging
            }
        });

        userPreferenceForm.setRole(userPreference.getRole());
        userPreferenceForm.setExperience(userPreference.getExperience());
        userPreferenceForm.setTechnology(userPreference.getTechnology());
        Call<BaseResponse> videoResponseCall = apiServiceForBackend.generateDynamicVideoContent(userPreferenceForm);
        videoResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    videoResponse = response.body();
                    Gson gson = new Gson();
                    VideoResponse videoList = gson.fromJson(gson.toJson(videoResponse.getResponse()), VideoResponse.class);
                    System.out.println("video list : "+videoList.getVideos().size());

                    videos = videoList.getVideos();

                    System.out.println("before recommended for loop "+videos.size());
                    for (int i = 0; i < videos.size(); i++) {
                        Video currentVideo = videos.get(i);
                        CardView currentCardView = recommendedCourses[i];

                        Glide.with(dashboard.this)
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
                    Toast.makeText(dashboard.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                // Handle failure/error in making the API call
                Toast.makeText(dashboard.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace(); // Print the error stack trace for debugging
            }
        })
        ;

        pendingCourses[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                UserCourse userCourse = gson.fromJson(gson.toJson(userPendingCourseList.get(0)), UserCourse.class);
                System.out.println(userCourse.getVideoId());
                video.setVideoId(userCourse.getVideoId());
                openonclick1(video);
            }
        });
        pendingCourses[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                UserCourse userCourse = gson.fromJson(gson.toJson(userPendingCourseList.get(1)), UserCourse.class);
                video.setVideoId(userCourse.getVideoId());
                openonclick1(video);
            }
        });
        completedCourses[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                UserCourse userCourse = gson.fromJson(gson.toJson(userPendingCourseList.get(0)), UserCourse.class);
                video.setVideoId(userCourse.getVideoId());
                openonclick1(video);
            }
        });
        completedCourses[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                UserCourse userCourse = gson.fromJson(gson.toJson(userPendingCourseList.get(1)), UserCourse.class);
                video.setVideoId(userCourse.getVideoId());
                openonclick1(video);
            }
        });
        completedCourses[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                UserCourse userCourse = gson.fromJson(gson.toJson(userPendingCourseList.get(2)), UserCourse.class);
                video.setVideoId(userCourse.getVideoId());
                openonclick1(video);
            }
        });
        completedCourses[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                UserCourse userCourse = gson.fromJson(gson.toJson(userPendingCourseList.get(3)), UserCourse.class);
                video.setVideoId(userCourse.getVideoId());
                openonclick1(video);
            }
        });
        recommendedCourses[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                UserCourse userCourse = gson.fromJson(gson.toJson(userPendingCourseList.get(0)), UserCourse.class);
                video.setVideoId(userCourse.getVideoId());
                openonclick1(video);
            }
        });
        recommendedCourses[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                UserCourse userCourse = gson.fromJson(gson.toJson(userPendingCourseList.get(1)), UserCourse.class);
                video.setVideoId(userCourse.getVideoId());
                openonclick1(video);
            }
        });
        recommendedCourses[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                UserCourse userCourse = gson.fromJson(gson.toJson(userPendingCourseList.get(2)), UserCourse.class);
                video.setVideoId(userCourse.getVideoId());
                openonclick1(video);
            }
        });
        recommendedCourses[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                UserCourse userCourse = gson.fromJson(gson.toJson(userPendingCourseList.get(3)), UserCourse.class);
                video.setVideoId(userCourse.getVideoId());
                openonclick1(video);
            }
        });
        recommendedCourses[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                UserCourse userCourse = gson.fromJson(gson.toJson(userPendingCourseList.get(4)), UserCourse.class);
                video.setVideoId(userCourse.getVideoId());
                openonclick1(video);
            }
        });
    }

    private void openonclick1(Video video) {
        Intent intent = new Intent(this, youtube.class);
            intent.putExtra("userPreferenceForm", userPreferenceForm);
            intent.putExtra("user", user);
            intent.putExtra("video",video);
            startActivity(intent);
    }


}