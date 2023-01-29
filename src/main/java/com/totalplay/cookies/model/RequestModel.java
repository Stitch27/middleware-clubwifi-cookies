package com.totalplay.cookies.model;

import lombok.Data;

@Data
public class RequestModel {

    private String code;

    private String address;

    private String subscriber;

    private String firstCookie;

    private String secondCookie;

    private String mac;

}
