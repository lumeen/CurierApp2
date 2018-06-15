package com.social.controller;


public class SimpleResponse {

    private String a;


    public SimpleResponse(String a) {
        this.a = a;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "SimpleResponse{" +
            "a='" + a + '\'' +
            '}';
    }
}
