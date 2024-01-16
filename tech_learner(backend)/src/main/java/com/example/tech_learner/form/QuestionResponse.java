package com.example.tech_learner.form;

import com.example.tech_learner.entity.Question;

import java.util.List;

public class QuestionResponse {

    private List<Question> Questions;

    public List<Question> getQuestions() {
        return Questions;
    }

    public void setQuestions(List<Question> questions) {
        Questions = questions;
    }
}
