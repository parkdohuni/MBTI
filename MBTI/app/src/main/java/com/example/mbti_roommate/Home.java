package com.example.mbti_roommate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


//별 기능이 있는 클래스는 아니고, 그냥 16가지 MBTI 중에 궁금한 유형이 있으면 클릭 시 설명하는 웹페이지로 이동하게끔 코드를 짬

public class Home extends AppCompatActivity {

    ImageView infj, infp,enfj, enfp,isfj,istj,estj,esfj,istp,estp, isfp, esfp, entj, intp, intj, entp;


    Button ProfileEditActButton;
    Button DenyActButton;
    Button RequestActButton;
    Button ResponseActButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ProfileEditActButton = findViewById(R.id.profile_edit_home_btn);
        DenyActButton = findViewById(R.id.deny_home_btn);
        RequestActButton = findViewById(R.id.request_home_btn);
        ResponseActButton = findViewById(R.id.response_home_btn);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_match:
                        startActivity(new Intent(getApplicationContext(),Match.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        infj = findViewById(R.id.infj);infp = findViewById(R.id.infp);enfj = findViewById(R.id.enfj);
        enfp = findViewById(R.id.enfp);isfj = findViewById(R.id.isfj);istj = findViewById(R.id.istj);
        estj = findViewById(R.id.estj);esfj = findViewById(R.id.esfj);istp = findViewById(R.id.istp);
        estp = findViewById(R.id.isfp);isfp = findViewById(R.id.isfp);esfp = findViewById(R.id.esfp);
        entj = findViewById(R.id.entj);intp = findViewById(R.id.intp);intj = findViewById(R.id.intj);
        entp = findViewById(R.id.entp);

        infj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-infj");
            }
        });

        infp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-infp");
            }
        });

        enfj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-enfj");
            }
        });

        enfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-enfp");
            }
        });

        isfj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-isfj");
            }
        });

        istj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-istj");
            }
        });

        estj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-estj");
            }
        });
        esfj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-esfj");
            }
        });
        istp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-istp");
            }
        });
        estp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-estp");
            }
        });
        isfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-isfp");
            }
        });
        esfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-esfp");
            }
        });
        entj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-entj");
            }
        });
        intp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-intp");
            }
        });
        intj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-intj");
            }
        });
        entp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9%EC%9C%A0%ED%98%95-entp");
            }
        });
    }


    public void goProfileEditActivity(View v){
        Intent intent = new Intent(this, ProfileEditActivity.class);
        startActivity(intent);
    }

    public void goRequestActivity(View v){
        User appuser = User.getInstance();
        if(appuser.info.getIsMatched()==1) {
            Toast info = Toast.makeText(getApplicationContext(),"이미 정해진 룸메이트가 존재합니다.!",Toast.LENGTH_LONG);
            info.show();
            return;
        }
        sendRequest(0,User.getInstance().info.getId());
    }
    public void goResponseActivity(View v){
        User appuser = User.getInstance();
        if(appuser.info.getIsMatched()==1) {
            Toast info = Toast.makeText(getApplicationContext(),"이미 정해진 룸메이트가 존재합니다.!",Toast.LENGTH_LONG);
            info.show();
            return;
        }
        sendRequest(1,User.getInstance().info.getId());
    }

    public void goDenyActivity(View v){

        sendRequest(2,User.getInstance().info.getId());
    }

    public void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void sendRequest(final int type,final String id){
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        String url;
        if(type ==0)
            url = urlManager.requestListURL;
        else if(type==1)
            url = urlManager.responseListURL;
        else
            url = urlManager.denyListURL;
        Log.e("url",url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        boolean isSuccessed = jsonObj.getBoolean("success");
                        Log.e("Success",String.valueOf(isSuccessed));
                        if(isSuccessed) {
                            Intent intent;
                            if(type==0)
                                intent = new Intent(getApplicationContext(), RequestActivity.class);
                            else if(type==1)
                                intent = new Intent(getApplicationContext(), ResponseActivity.class);
                            else
                                intent = new Intent(getApplicationContext(), DenyActivity.class);
                            ArrayList<UserInfo> uInfos = new ArrayList<UserInfo>();
                            JSONArray UserObjs = jsonObj.getJSONArray("Users");
                            try {
                                for(int i =0;i<UserObjs.length();++i) {
                                    JSONObject userObj = UserObjs.getJSONObject(i);
                                    uInfos.add(new UserInfo(
                                            userObj.getString("id"),
                                            userObj.getString("password"),
                                            userObj.getString("pname"),
                                            userObj.getInt("pgender"),
                                            userObj.getInt("pmbti"),
                                            userObj.getInt("pdormitory"),
                                            userObj.getInt("univ"),
                                            userObj.getInt("pmajor"),
                                            userObj.getString("email"),
                                            userObj.getInt("psmoke"),
                                            userObj.getString("pcomment"),
                                            userObj.getInt("page"),
                                            userObj.getString("pcontact"),
                                            userObj.getInt("pstime"),
                                            userObj.getInt("pshour"),
                                            userObj.getInt("hasMatchBefore"),
                                            0,
                                            null));
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("UserInfos",uInfos);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        else{
                            Toast info = Toast.makeText(getApplicationContext(),jsonObj.getString("reason"),Toast.LENGTH_LONG);
                            info.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id",id);
                return params;
            }
            @Override
            public  Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Context-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}