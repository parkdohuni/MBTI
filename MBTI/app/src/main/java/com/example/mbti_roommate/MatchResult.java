package com.example.mbti_roommate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


//매칭된 3명을 나타내는 클래스

public class MatchResult extends AppCompatActivity implements ListViewAdapter.ListBtnClickListener{

    Button rematchButton;           //재매칭하기 버튼

    ListView listView;
    ListViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_result);

        Intent intent = getIntent();
        Bundle bundleObject = getIntent().getExtras();
        ArrayList<UserInfo> list = new ArrayList<UserInfo>();
        ArrayList<UserInfo> uInfos = (ArrayList<UserInfo>)bundleObject.getSerializable("UserInfos");
        adapter = new ListViewAdapter(this,R.layout.profile_default_info,list,this);
        listView = findViewById(R.id.profilelistView);
        rematchButton = findViewById(R.id.rematch_btn);
        listView.setAdapter(adapter);
        for(int i=0;i<uInfos.size();++i){
            adapter.addItem(uInfos.get(i));
        }
//        adapter.addItem(new UserInfo("guest2","password","이종제",1,12,11110001,1111,"kkk1111@knu.ac.kr",0,"Hello!",25,"전화번호",12,6,0,0,null));
//        adapter.addItem(new UserInfo("guest3","password","김수현",1,5,11110001,1111,"ijk2020@knu.ac.kr",1,"hi!",25,"삐삐번호",10,7,0,0,null));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserInfo userInfo = (UserInfo)parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), IndividualProfileResult.class);
                intent.putExtra("UserInfo",userInfo);
                startActivity(intent);
            }
        });

        rematchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                User appuser = User.getInstance();
                sendRequest(0,
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
        });

    }

    public void sendRequest(final int type,final String id, final String password, final String pname, final String pgender, final String pmbti,final String pdormitory, final String univ,final String pmajor,  final String email,
                            final String psmoke, final String pcomment, final String page, final String pcontact, final String pstime, final String pshour){
        RequestQueue requestQueue = Volley.newRequestQueue(MatchResult.this);
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
                            try {
                                for(int i =0;i<UserObjs.length();++i) {
                                    JSONObject userObj = UserObjs.getJSONObject(i);
                                    userObj.getString("id");
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
                                            userObj.getInt("isMatched"),
                                            null));
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
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

    @Override
    public void onListBtnClick(int position) {
        UserInfo userInfo = (UserInfo)adapter.getItem(position);
        Intent intent = new Intent(getApplicationContext(), IndividualProfileResult.class);
        intent.putExtra("UserInfo",userInfo);
        startActivity(intent);
    }
}
