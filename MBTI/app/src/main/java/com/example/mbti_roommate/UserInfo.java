package com.example.mbti_roommate;

import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String id;
    private String password;
    private String pname;
    private int pgender;
    private int pmbti;
    private int pdormitory;
    private int univ;
    private int pmajor;
    private String email;
    private int psmoke;
    private String pcomment;
    private int page;
    private String pcontact;
    private int pstime;
    private int pshour;
    private int hasMatchBefore;
    private int isMatched;
    private int mbti_image;

    private UserInfo matched_user;

    public UserInfo(){}

    public UserInfo(
            String id, String password,String pname,int pgender,int pmbti,int pdormitory,int univ,int pmajor,String email,
            int psmoke,String pcomment,int page,String pcontact,int pstime,int pshour,int hasMatchBefore,int isMatched,
            UserInfo matched_user){

        this.id = id;
        this.password = password;
        this.pname = pname;
        this.pgender = pgender;
        this.pmbti = pmbti;
        this.pdormitory = pdormitory;
        this.univ = univ;
        this.pmajor = pmajor;
        this.email = email;
        this.psmoke = psmoke;
        this.pcomment = pcomment;
        this.page = page;
        this.pcontact = pcontact;
        this.pstime = pstime;
        this.pshour = pshour;
        this.hasMatchBefore = hasMatchBefore;
        this.isMatched = isMatched;
        if(isMatched == 1){
            this.matched_user = new UserInfo(matched_user);
        }
        else
            this.matched_user = null;

        Log.e("mbti",MbtiInfo.MbtiInfo[this.pmbti]);
        this.mbti_image = MbtiInfo.Mbti_Image[this.pmbti];
        Log.e("mbti_image",String.valueOf(this.mbti_image));
    }

    public UserInfo(UserInfo obj){
        this.id = obj.id;
        this.password = obj.password;
        this.pname = obj.pname;
        this.pgender = obj.pgender;
        this.pmbti = obj.pmbti;
        this.pdormitory = obj.pdormitory;
        this.univ = obj.univ;
        this.pmajor = obj.pmajor;
        this.email = obj.email;
        this.psmoke = obj.psmoke;
        this.pcomment = obj.pcomment;
        this.page = obj.page;
        this.pcontact = obj.pcontact;
        this.pstime = obj.pstime;
        this.pshour = obj.pshour;
        this.hasMatchBefore = obj.hasMatchBefore;
        this.isMatched = obj.isMatched;
        if(obj.isMatched == 1)
            this.matched_user = new UserInfo(obj.matched_user);
        else
            this.matched_user = null;
        this.mbti_image = obj.mbti_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getPgender() {
        return pgender;
    }

    public void setPgender(int pgender) {
        this.pgender = pgender;
    }

    public int getPmbti() {
        return pmbti;
    }

    public void setPmbti(int pmbti) {
        this.pmbti = pmbti;
    }

    public int getPdormitory() {
        return pdormitory;
    }

    public void setPdormitory(int pdormitory) {
        this.pdormitory = pdormitory;
    }

    public int getUniv() {
        return univ;
    }

    public void setUniv(int univ) {
        this.univ = univ;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPsmoke() {
        return psmoke;
    }

    public void setPsmoke(int psmoke) {
        this.psmoke = psmoke;
    }

    public String getPcomment() {
        return pcomment;
    }

    public void setPcomment(String pcomment) {
        this.pcomment = pcomment;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getPcontact() {
        return pcontact;
    }

    public void setPcontact(String pcontact) {
        this.pcontact = pcontact;
    }

    public int getPstime() {
        return pstime;
    }

    public void setPstime(int pstime) {
        this.pstime = pstime;
    }

    public int getPshour() {
        return pshour;
    }

    public void setPshour(int pshour) {
        this.pshour = pshour;
    }

    public int getHasMatchBefore() {
        return hasMatchBefore;
    }

    public void setHasMatchBefore(int hasMatchBefore) {
        this.hasMatchBefore = hasMatchBefore;
    }

    public int getIsMatched() {
        return isMatched;
    }

    public void setIsMatched(int isMatched) {
        this.isMatched = isMatched;
    }

    public int getMbti_image() {
        return mbti_image;
    }

    public void setMbit_image(int mbti_image) {
        this.mbti_image = mbti_image;
    }

    public int getPmajor() {
        return pmajor;
    }

    public void setPmajor(int pmajor) {
        this.pmajor = pmajor;
    }

    public UserInfo getMatched_user() {
        return matched_user;
    }

    public void setMatched_user(UserInfo matched_user) {
        this.matched_user = matched_user;
    }

}
