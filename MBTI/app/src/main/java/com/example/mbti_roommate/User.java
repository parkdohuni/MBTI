package com.example.mbti_roommate;

public class User {
    public UserInfo info;

    private User(){
        info = new UserInfo();
    }
    private static User Instance;
    public static User getInstance(){
        if(Instance==null)
           Instance = new User();
        return Instance;
    }
}
