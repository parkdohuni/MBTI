package com.example.mbti_roommate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationActivity extends AppCompatActivity {


    //Home 과 Match 를 왔다갔다 할 수 있는 하단바 구현 클래스
    Button ProfileEditActButton;
    Button DenyActButton;
    Button RequestActButton;
    Button ResponseActButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProfileEditActButton = findViewById(R.id.profile_edit_bottom_btn);
        DenyActButton = findViewById(R.id.deny_bottom_btn);
        RequestActButton = findViewById(R.id.request_bottom_btn);
        ResponseActButton = findViewById(R.id.response_bottom_btn);

        setContentView(R.layout.activity_bottom_navigation);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_match:
                        startActivity(new Intent(getApplicationContext(),Match.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void goProfileEditActivity(View v){

    }

    public void goDenyActivity(View v){

    }

    public void goRequestActivity(View v){

    }
    public void goResponseActivity(View v){

    }
}