package com.example.tech_learner.service;

import com.example.tech_learner.base.BaseResponse;
import com.example.tech_learner.entity.Question;
import com.example.tech_learner.entity.UserCourse;
import com.example.tech_learner.entity.UserPreference;
import com.example.tech_learner.form.*;
import com.example.tech_learner.repo.QuestionRepository;
import com.example.tech_learner.repo.UserCourseRepository;
import com.example.tech_learner.repo.UserPreferenceRepository;
import com.example.tech_learner.repo.UserRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.tech_learner.entity.User;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final UserCourseRepository userCourseRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    private final RestTemplate restTemplate = new RestTemplate();

    public UserService(UserRepository userRepository, UserPreferenceRepository userPreferenceRepository, UserCourseRepository userCourseRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.userPreferenceRepository = userPreferenceRepository;
        this.userCourseRepository = userCourseRepository;
        this.questionRepository = questionRepository;
    }

    public BaseResponse login(LoginForm loginForm) {
        BaseResponse response = new BaseResponse();
        User user = userRepository.findByEmpId(loginForm.getUsername());
        if (user == null) {
            return response.set(302, "Failed");
        }
        if (!user.getPassword().equals(loginForm.getPassword())) {
            return response.set(302, "Failed");
        }
        return response.set(200, "Success", user);
    }

    public BaseResponse saveUserPreference(UserPreferenceForm userPreferenceForm) {
        BaseResponse response = new BaseResponse();
        UserPreference userPreference = userPreferenceRepository.findByEmpId(userPreferenceForm.getEmpId());
        if (userPreference != null) {
            return response.set(302, "Already exist");
        }
        userPreference = new UserPreference();
        userPreference.setEmpId(userPreferenceForm.getEmpId());
        userPreference.setRole(userPreferenceForm.getRole());
        userPreference.setTechnology(userPreferenceForm.getTechnology());
        userPreference.setExperience(userPreferenceForm.getExperience());
        userPreference = userPreferenceRepository.save(userPreference);
        return response.set(200, "Success", userPreference);
    }

    public BaseResponse updateUserPreference(UserPreferenceForm userPreferenceForm) {
        BaseResponse response = new BaseResponse();
        UserPreference userPreference = userPreferenceRepository.findByEmpId(userPreferenceForm.getEmpId());
        if (userPreference != null) {
            return response.set(302, "Not exist");
        }
        userPreference = new UserPreference();
        userPreference.setRole(userPreferenceForm.getRole());
        userPreference.setTechnology(userPreferenceForm.getTechnology());
        userPreference.setExperience(userPreferenceForm.getExperience());
        userPreference = userPreferenceRepository.save(userPreference);
        return response.set(200, "Success", userPreference);
    }

    public BaseResponse getUserPreference(String empId) {
        BaseResponse response = new BaseResponse();
        UserPreference userPreference = userPreferenceRepository.findByEmpId(empId);
        if (userPreference == null) {
            return response.set(302, "Failed");
        }
        return response.set(200, "Success", userPreference);
    }

    public BaseResponse saveUserCourse(UserCourseForm userCourseForm) {
        BaseResponse response = new BaseResponse();
        UserCourse userCourse = new UserCourse();
        userCourse.setEmpId(userCourseForm.getEmpId());
        userCourse.setCourseName(userCourseForm.getCourseName());
        userCourse.setCourseUrl(userCourseForm.getCourseUrl());
        userCourse.setStatus(userCourseForm.getStatus());
        userCourse.setVideoId(userCourseForm.getVideoId());
        userCourse.setScore(userCourseForm.getScore());
        userCourse.setThumbnailUrl(userCourseForm.getThumbnailUrl());
        userCourse.setVideoTitle(userCourseForm.getVideoTitle());
        userCourse = userCourseRepository.save(userCourse);
        return response.set(200, "Success", userCourse);
    }

    public BaseResponse updateUserCourse(UserCourseForm userCourseForm) {
        BaseResponse response = new BaseResponse();
        UserCourse userCourse = userCourseRepository.findByEmpId(userCourseForm.getEmpId());
        if (userCourse == null) {
            return response.set(302, "Not present");
        }
        userCourse.setStatus("Completed");
        userCourse.setScore("7");
        userCourseRepository.save(userCourse);
        return response;
    }

    public BaseResponse getUserCourse(String empId) {
        BaseResponse response = new BaseResponse();
        UserCourse userCourse = userCourseRepository.findByEmpId(empId);
        if (userCourse == null) {
            return response.set(302, "Failed");
        }
        return response.set(200, "Success", userCourse);
    }

    public BaseResponse generateDynamicVideoContent(UserPreferenceForm userPreferenceForm) {
        BaseResponse response = new BaseResponse();
        String apiUrl = "http://127.0.0.1:3334/get_youtube_videos";

        ResponseEntity<VideoResponse> responseEntity = restTemplate.postForEntity(apiUrl, userPreferenceForm, VideoResponse.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            return response.set(302, "Failed");
        }
        return response.set(200, "Success", responseEntity.getBody());
    }


    public BaseResponse getPendingUserCourse(String empId) {
        BaseResponse response = new BaseResponse();
        List<UserCourse> userCourseList = userCourseRepository.findAllByEmpIdAndStatus(empId, "Pending");
        if (userCourseList.isEmpty()) {
            return response.set(302, "Not found");
        }
        return response.set(200, "Success", userCourseList);
    }

    public BaseResponse getCompletedUserCourse(String empId) {
        BaseResponse response = new BaseResponse();
        List<UserCourse> userCourseList = userCourseRepository.findAllByEmpIdAndStatus(empId, "Completed");
        if (userCourseList.isEmpty()) {
            return response.set(302, "Not found");
        }
        return response.set(200, "Success", userCourseList);
    }

    public BaseResponse generateQuestions() {
        BaseResponse response = new BaseResponse();
        List<Question> questionList = questionRepository.findAllByDomain("cyber");

        return response.set(200, "Success", questionList);
    }
}
