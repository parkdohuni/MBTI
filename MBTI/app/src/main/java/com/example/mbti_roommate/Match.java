package com.example.mbti_roommate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

//하단바에서 "매칭" 탭을 누르면 나오는 화면 클래스

public class Match extends AppCompatActivity {

    Button goSeeMatchResultButton;
    Button goWriteNewProfileButton;
    Button goSearchProfileButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.nav_match);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_match:
                        return true;
                }
                return false;
            }
        });



    }
    public void matchButtonClick(View v){               //매칭하기 버튼 클릭 시 호출 함수

            User appuser = User.getInstance();
            if(appuser.info.getIsMatched()==1) {
                Toast info = Toast.makeText(getApplicationContext(),"이미 정해진 룸메이트가 존재합니다.!",Toast.LENGTH_LONG);
                info.show();
                return;
            }

          sendRequest(0,
               appuser.info.getId(),
               appuser.info.getPassword(),
               appuser.info.getPname(),
               String.valueOf(appuser.info.getPgender()),
               String.valueOf(appuser.info.getPmbti()),
               String.valueOf(appuser.info.getPmajor()),
               String.valueOf(appuser.info.getPdormitory()),
               String.valueOf(appuser.info.getUniv()),
               appuser.info.getEmail(),
                  String.valueOf(appuser.info.getPsmoke()),
                  appuser.info.getPcomment(),
                  String.valueOf(appuser.info.getPage()),
                  appuser.info.getPcontact(),
                  String.valueOf(appuser.info.getPstime()),
                  String.valueOf(appuser.info.getPshour())
          );

    }

    public void openSavedMatchResults(View v){          //이전 매칭 결과 불러오기 클릭 시 호출 함수
        User appuser = User.getInstance();
        if(appuser.info.getIsMatched()==1) {
            Toast info = Toast.makeText(getApplicationContext(),"이미 정해진 룸메이트가 존재합니다.!",Toast.LENGTH_LONG);
            info.show();
            return;
        }
        sendRequest(1,
                appuser.info.getId(),
                appuser.info.getPassword(),
                appuser.info.getPname(),
                String.valueOf(appuser.info.getPgender()),
                String.valueOf(appuser.info.getPmbti()),
                String.valueOf(appuser.info.getPdormitory()),
                String.valueOf(appuser.info.getUniv()),
                String.valueOf(appuser.info.getPmajor()),
                appuser.info.getEmail(),
                String.valueOf(appuser.info.getPsmoke()),
                appuser.info.getPcomment(),
                String.valueOf(appuser.info.getPage()),
                appuser.info.getPcontact(),
                String.valueOf(appuser.info.getPstime()),
                String.valueOf(appuser.info.getPshour())
        );
    }

    public void openSearchProfile(View v){
        User appuser = User.getInstance();
        if(appuser.info.getIsMatched()==1) {
            Toast info = Toast.makeText(getApplicationContext(),"이미 정해진 룸메이트가 존재합니다.!",Toast.LENGTH_LONG);
            info.show();
            return;
        }
        Intent intent = new Intent(getApplicationContext(), SearchProfile.class);
        startActivity(intent);
    }

    public void sendRequest(final int type,final String id, final String password, final String pname, final String pgender, final String pmbti,final String pmajor, final String pdormitory, final String univ, final String email,
                            final String psmoke, final String pcomment, final String page, final String pcontact, final String pstime, final String pshour){
        RequestQueue requestQueue = Volley.newRequestQueue(Match.this);
        String url;
        if(type ==0)
            url = urlManager.newMatchURL;
        else
            url = urlManager.prevMatchURL;
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
                            Intent intent = new Intent(getApplicationContext(), MatchResult.class);
                            ArrayList<UserInfo> uInfos = new ArrayList<UserInfo>();
                            JSONArray UserObjs = jsonObj.getJSONArray("Users");
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
                            User appuser = User.getInstance();
                            appuser.info.setHasMatchBefore(1);
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
                params.put("password",password);
                params.put("pname", pname);
                params.put("pgender", pgender);
                params.put("pmbti", pmbti);
                params.put("pdormitory", pdormitory);
                params.put("univ", univ);
                params.put("pmajor",pmajor);
                params.put("email", email);
                params.put("psmoke", psmoke);
                params.put("pcomment", pcomment);
                params.put("page", page);
                params.put("pcontact", pcontact);
                params.put("pstime", pstime);
                params.put("pshour", pshour);
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