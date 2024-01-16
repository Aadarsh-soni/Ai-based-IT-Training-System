package com.example.tech_learner.controller;

import com.example.tech_learner.base.BaseResponse;
import com.example.tech_learner.form.LoginForm;
import com.example.tech_learner.form.UserCourseForm;
import com.example.tech_learner.form.UserPreferenceForm;
import com.example.tech_learner.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginForm loginForm) {
        return userService.login(loginForm);
    }

    @PostMapping("/saveUserCourse")
    public BaseResponse saveUserCourse(@RequestBody UserCourseForm userCourseForm) {
        System.out.println("Saving user course");
        return userService.saveUserCourse(userCourseForm);
    }

    @PostMapping("/updateUserCourse")
    public BaseResponse updateUserCourse(@RequestBody UserCourseForm userCourseForm) {
        System.out.println("update user course");
        return userService.updateUserCourse(userCourseForm);
    }

    @PostMapping("/getUserCourse")
    public BaseResponse getUserCourse(@PathVariable String empId) {
        System.out.println("Saving user course");
        return userService.getUserCourse(empId);
    }

    @PostMapping("/saveUserPreference")
    public BaseResponse saveUserPreference(@RequestBody UserPreferenceForm userPreferenceForm) {
        System.out.println("saving user preference : "+userPreferenceForm.getTechnology());
        return userService.saveUserPreference(userPreferenceForm);
    }

    @PostMapping("/updateUserPreference")
    public BaseResponse updateUserPreference(@RequestBody UserPreferenceForm userPreferenceForm) {
        System.out.println("updating user preference : "+userPreferenceForm.getTechnology());
        return userService.updateUserPreference(userPreferenceForm);
    }

    @GetMapping("/getUserPreference/{empId}")
    public BaseResponse getUserPreference(@PathVariable("empId") String empId) {
        System.out.println("get user preference : "+empId);
        return userService.getUserPreference(empId);
    }

    @PostMapping("/generateDynamicVideoContent")
    public BaseResponse generateDynamicVideoContent(@RequestBody UserPreferenceForm userPreferenceForm) {
        System.out.println("generating dynamic video content : "+userPreferenceForm.getTechnology());
        return userService.generateDynamicVideoContent(userPreferenceForm);
    }

    @GetMapping("/getPendingUserCourse/{empId}")
    public BaseResponse getPendingUserCourse(@PathVariable("empId") String empId) {
        System.out.println("get pending courses : "+empId);
        return userService.getPendingUserCourse(empId);
    }

    @GetMapping("/getCompletedUserCourse/{empId}")
    public BaseResponse getCompletedUserCourse(@PathVariable("empId") String empId) {
        System.out.println("get completed courses : "+empId);
        return userService.getCompletedUserCourse(empId);
    }

    @PostMapping("/generateQuestions")
    public BaseResponse generateQuestions() {
        System.out.println("generating dynamic questions : ");
        return userService.generateQuestions();
    }
}
