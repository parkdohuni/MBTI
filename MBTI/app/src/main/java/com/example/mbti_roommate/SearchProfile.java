package com.example.mbti_roommate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchProfile extends AppCompatActivity implements ListViewAdapter.ListBtnClickListener{
    TextView dormName;
    EditText userName;
    ListView searchListView;
    ListViewAdapter adapter;
    User appuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_for_profile);
        appuser = User.getInstance();
        dormName = findViewById(R.id.search_dorm_name);
        userName = findViewById(R.id.search_username);
        userName.setPrivateImeOptions("defaultInputmode=korean; ");
        searchListView = findViewById(R.id.search_listView);
        try{
            DormInfo dinfo= DormInfo.getInstance();
            dormName.setText(dinfo.getDormObjects().getString(String.valueOf(appuser.info.getPdormitory())));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void Search(View v){
        String pdormitory = String.valueOf(appuser.info.getPdormitory());
        String pname = userName.getText().toString();
        Log.e("name",pname);
        sendRequest(pdormitory,pname,appuser.info.getId());
    }

    public void sendRequest(final String pdormitory, final String pname,final String id){
        RequestQueue requestQueue = Volley.newRequestQueue(SearchProfile.this);
        String url = urlManager.SearchProfURL;
        ArrayList<UserInfo> ulist = new ArrayList<UserInfo>();
        adapter = new ListViewAdapter(getApplicationContext(),R.layout.profile_default_info,ulist,this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                searchListView.setAdapter(adapter);
                searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        UserInfo userInfo = (UserInfo)parent.getItemAtPosition(position);
                        Intent intent = new Intent(getApplicationContext(), IndividualProfileResult.class);
                        intent.putExtra("UserInfo",userInfo);
                        startActivity(intent);
                    }
                });
                if(!response.isEmpty()) {
                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        boolean isSuccessed = jsonObj.getBoolean("success");
                        Log.e("Success",String.valueOf(isSuccessed));
                        if(isSuccessed) {
                            JSONArray UserObjs = jsonObj.getJSONArray("Users");
                            for(int i =0;i<UserObjs.length();++i) {
                                JSONObject userObj = UserObjs.getJSONObject(i);
                                userObj.getString("id");
                                adapter.addItem(new UserInfo(
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
                        }
                    }catch (JSONException e){
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
                params.put("pdormitory",pdormitory);
                params.put("pname",pname);
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
