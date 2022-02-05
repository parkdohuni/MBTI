package com.example.mbti_roommate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button signInButton;
    private Button userRegister;
    private String username,password;
    private User appuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appuser = User.getInstance();

        //로그인 버튼 클릭
        signInButton = (Button)findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                username = ((EditText)findViewById(R.id.username)).getText().toString();
                password = ((EditText)findViewById(R.id.password)).getText().toString();
                sendRequest(username,password);

            }
        });

    }

    //회원가입 페이지 띄우기
    public void openUserRegisterPage(View v){
        Intent intent = new Intent(this, UserRegister.class);
        startActivity(intent);
    }

    //로그인하고 최초화면 띄우기
    public void openMainPage(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void sendRequest(final String id, final String password){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = urlManager.loginURL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()) {
                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        boolean isSuccessed = jsonObj.getBoolean("success");
                        Log.e("Success",String.valueOf(isSuccessed));
                        if(isSuccessed) {
                            appuser.info.setId(jsonObj.getString("id"));
                            appuser.info.setPassword(jsonObj.getString("password"));
                            appuser.info.setPname(jsonObj.getString("pname"));
                            appuser.info.setPgender(jsonObj.getInt("pgender"));
                            appuser.info.setPmbti(jsonObj.getInt("pmbti"));
                            appuser.info.setPdormitory(jsonObj.getInt("pdormitory"));
                            appuser.info.setUniv(jsonObj.getInt("univ"));
                            appuser.info.setPmajor(jsonObj.getInt("pmajor"));
                            appuser.info.setEmail(jsonObj.getString("email"));
                            appuser.info.setPsmoke(jsonObj.getInt("psmoke"));
                            appuser.info.setPcomment(jsonObj.getString("pcomment"));
                            appuser.info.setPage(jsonObj.getInt("page"));
                            appuser.info.setPcontact(jsonObj.getString("pcontact"));
                            appuser.info.setPstime(jsonObj.getInt("pstime"));
                            appuser.info.setPshour(jsonObj.getInt("pshour"));
                            appuser.info.setHasMatchBefore(jsonObj.getInt("hasMatchBefore"));
                            appuser.info.setIsMatched(jsonObj.getInt("isMatched"));
                            if(appuser.info.getIsMatched()==1) {
                                JSONObject roomObj = jsonObj.getJSONObject("roommate");
                                appuser.info.setMatched_user(
                                        new UserInfo(
                                                roomObj.getString("id"),
                                                roomObj.getString("password"),
                                                roomObj.getString("pname"),
                                                roomObj.getInt("pgender"),
                                                roomObj.getInt("pmbti"),
                                                roomObj.getInt("pdormitory"),
                                                roomObj.getInt("univ"),
                                                roomObj.getInt("pmajor"),
                                                roomObj.getString("email"),
                                                roomObj.getInt("psmoke"),
                                                roomObj.getString("pcomment"),
                                                roomObj.getInt("page"),
                                                roomObj.getString("pcontact"),
                                                roomObj.getInt("pstime"),
                                                roomObj.getInt("pshour"),
                                                roomObj.getInt("hasMatchBefore"),
                                                0,
                                                null
                                        )
                                );
                                Log.e("here",roomObj.getString("id"));
                            }
                            else
                                appuser.info.setMatched_user(null);
                            openMainPage();
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
                return params;
            }
            @Override
            public  Map<String,String> getHeaders() throws AuthFailureError{
                Map<String,String> params = new HashMap<String,String>();
                params.put("Context-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}