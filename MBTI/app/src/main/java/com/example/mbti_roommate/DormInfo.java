package com.example.mbti_roommate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DormInfo {
    private static JSONObject univObject = new JSONObject();
    private static JSONObject dormObject = new JSONObject();
    private static JSONObject majorObject = new JSONObject();
    private DormInfo(){};
    private static DormInfo instance;
    private static void initUnivObjs(){
        try{
            univObject.put("1111","경북대학교(대구캠퍼스)");
            univObject.put("1112","경북대학교(상주캠퍼스)");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private static void initDormObjs(){
        try{
            dormObject.put("11110001","첨성관");
            dormObject.put("11110002","성실관");
            dormObject.put("11110003","긍지관");
            dormObject.put("11110004","협동관");
            dormObject.put("11110005","봉사관");
            dormObject.put("11110006","진리관");
            dormObject.put("11110007","화목관");
            dormObject.put("11110008","면학관");
            dormObject.put("11110009","향토관");
            dormObject.put("11110010","명의관");
            dormObject.put("11110011","누리관");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private static void initMajorObjs(){
        try{
            majorObject.put("11110001","컴퓨터학부");
            majorObject.put("11110002","경영학부");
            majorObject.put("11110003","영어영문학과");
            majorObject.put("11110004","수학과");
            majorObject.put("11110005","전자공학과");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private static void init(){
        initUnivObjs();
        initDormObjs();
        initMajorObjs();
    }

    public static DormInfo getInstance(){
        if(instance==null){
            instance = new DormInfo();
            init();
        }
        return instance;
    }

    public static JSONObject getUnivObjects() {
        return univObject;
    }

    public static JSONObject getDormObjects() {
        return dormObject;
    }

    public static JSONObject getMajorObjects() {
        return majorObject;
    }
}
