package com.example.mbti_roommate;

public class urlManager {
    static String targetURL = new String("http://15.164.217.53:5000");
    //static String targetURL = new String("http://192.168.56.1:5000");
    static String loginURL = new String(targetURL+"/User/Login");
    static String registerURL = new String(targetURL+"/User/Register");
    static String newMatchURL = new String(targetURL+"/User/NewMatch");
    static String prevMatchURL = new String(targetURL+"/User/PrevMatch");
    static String SearchProfURL = new String(targetURL+"/User/SearchProfile");
    static String requestURL = new String(targetURL+"/User/Request");
    static String denyURL = new String(targetURL+"/User/Deny");
    static String requestListURL = new String(targetURL+"/User/RequestList");
    static String responseListURL = new String(targetURL+"/User/ResponseList");
    static String responseDenyURL = new String(targetURL+"/User/ResponseDeny");
    static String responseAcptURL = new String(targetURL+"/User/ResponseAcpt");
    static String denyListURL = new String(targetURL+"/User/DenyList");
    static String denyCancelURL = new String(targetURL+"/User/DenyCancel");
    static String reqCancelURL = new String(targetURL+"/User/ReqCancel");
}
