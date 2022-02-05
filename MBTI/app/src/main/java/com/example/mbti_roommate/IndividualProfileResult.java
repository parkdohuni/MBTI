package com.example.mbti_roommate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.w3c.dom.Text;

//상대 프로필 열람 클래스임. 매칭된 상대 개개인의 상세 프로필이라고 보면 된다.
//서버와 통신하여 매칭된 상대의 프로필 정보를 형식에 맞게 띄우면 된다.

public class IndividualProfileResult extends AppCompatActivity {

    ImageView matchResultUserMBTIImage;             //상대 MBTI 이미지
    TextView matchResultUsername;                   //상대 유저 이름
    TextView matchResultUserMBTI;                   //상대 MBTI 유형
    TextView matchResultUserAge;                    //상대 나이
    TextView matchResultUserMajor;                  //상대 학과
    TextView matchResultUserSmoke;                  //상대 흡연 유무
    EditText matchResultUserLifeStyle;              //상대 라이프스타일 설명
    TextView matchResultGenderResult;               //상대 성별
    TextView matchResultDormitory;                  //상대 기숙사
    TextView matchResultContactInfo;                //상대 연락처 (오픈카톡 링크)
    TextView matchResultSleepTime;                  //상대 수면시각
    TextView matchResultSleepHour;                  //상대 수면시간

    String contactInfo;                             //상대 연락처 저장 변수 (오픈 카톡 링크, 전화번호, 이메일 등이 가능

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_profile_result);

        Intent intent = getIntent();
        DormInfo dinfo= DormInfo.getInstance();
        UserInfo userInfo = new UserInfo((UserInfo)intent.getSerializableExtra("UserInfo"));
        //레이아웃 구성 위젯 연결 및 초기화 단계
        matchResultUserMBTIImage = (ImageView)findViewById(R.id.match_result_user_mbti_image);
        matchResultUsername = (TextView)findViewById(R.id.match_result_username);
        matchResultUserMBTI = (TextView)findViewById(R.id.match_result_user_mbti);
        matchResultUserAge = (TextView)findViewById(R.id.match_result_user_age);
        matchResultUserMajor = (TextView)findViewById(R.id.match_result_user_major);
        matchResultUserSmoke = (TextView)findViewById(R.id.match_result_user_smoke);
        matchResultUserLifeStyle = (EditText)findViewById(R.id.match_result_user_selfIntroduction);
        matchResultGenderResult = (TextView)findViewById(R.id.gender_result);
        matchResultDormitory = (TextView)findViewById(R.id.match_result_dormitory);
        matchResultContactInfo = (TextView)findViewById(R.id.match_result_contact);
        matchResultSleepTime = (TextView)findViewById(R.id.match_result_sleep_time);
        matchResultSleepHour = (TextView)findViewById(R.id.match_result_sleep_hour);

        matchResultUserMBTIImage.setImageResource(userInfo.getMbti_image());
        matchResultUsername.setText(userInfo.getPname());
        matchResultUserMBTI.setText(MbtiInfo.MbtiInfo[userInfo.getPmbti()]);
        matchResultUserAge.setText(String.valueOf(userInfo.getPage()));

        if(userInfo.getPsmoke()==1)
            matchResultUserSmoke.setText("YES");
        else
            matchResultUserSmoke.setText("NO");

        matchResultUserLifeStyle.setText(userInfo.getPcomment());
        if(userInfo.getPgender()==0)
            matchResultGenderResult.setText("남자");
        else
            matchResultGenderResult.setText("여자");

        try{
            matchResultUserMajor.setText(dinfo.getMajorObjects().getString(String.valueOf(userInfo.getPmajor())));

            matchResultDormitory.setText(dinfo.getDormObjects().getString(String.valueOf(userInfo.getPdormitory())));
        }catch (JSONException e){
            e.printStackTrace();
        }
        matchResultContactInfo.setText(userInfo.getPcontact());
        matchResultSleepHour.setText(String.valueOf(userInfo.getPshour()));
        matchResultSleepTime.setText(String.valueOf(userInfo.getPstime()));
    }
}


