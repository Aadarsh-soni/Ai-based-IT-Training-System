package com.example.tech_learner.base;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class BaseResponse {
    private Integer status;
    private String message;
    private Object response;


    public BaseResponse set(Integer status,String message,Object response) {
        this.status = status;
        this.message = message;
        this.response = response;
        return this;
    }
    public BaseResponse set(Integer status,String message) {
        this.status = status;
        this.message = message;
        return this;
    }


    public BaseResponse setInternalServerError() {
        this.status = 500;
        this.message = "Internal Server Error";
        this.response = null;
        return this;
    }

    public BaseResponse setSomethingWentWrong() {
        this.status = 302;
        this.message = "setSomethingWentWrong";
        this.response = null;
        return this;
    }

    public BaseResponse setUnauthorized() {
        this.status = 302;
        this.message = "setUnauthorized";
        this.response = null;
        return this;
    }

    public BaseResponse setUnauthenticated() {
    	this.status = 401;
        this.message = "Invalid user credentials";
        this.response = null;
        return this;
    }


    public BaseResponse setBlockedUserException() {
        this.status = 302;
        this.message = "setBlockedUserException";
        this.response = null;
        return this;
    }
    public BaseResponse isValidCaptcha() {
        this.status = 302;
        this.message = "isValidCaptcha";
        this.response = null;
        return this;
    }
    

}
