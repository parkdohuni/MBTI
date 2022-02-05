package com.example.mbti_roommate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ResponseListViewAdapter extends ArrayAdapter<UserInfo> implements View.OnClickListener{
    private ArrayList<UserInfo> listViewItemList= new ArrayList<UserInfo>();
    public interface ListBtnClickListener {
        void onListBtnClick(int position) ;
    }
    int resourceId;
    Context context;
    private ListBtnClickListener listBtnClickListener ;
    public ResponseListViewAdapter(Context context, int resource, ArrayList<UserInfo> list, ListBtnClickListener clickListener){
        super(context,resource,list);
        this.context = context;
        this.resourceId = resource;
        this.listBtnClickListener = clickListener;
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public UserInfo getItem(int position) {
        return listViewItemList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.profile_response, parent, false);

        ImageView image = convertView.findViewById(R.id.image);
        TextView user_name = convertView.findViewById(R.id.user_name);
        TextView mbti_name = convertView.findViewById(R.id.mbti_name);
        TextView isSmoke = convertView.findViewById(R.id.isSmoke);

        final UserInfo listViewItem = listViewItemList.get(position);
        image.setImageResource(listViewItem.getMbti_image());
        user_name.setText(listViewItem.getPname());
        mbti_name.setText(MbtiInfo.MbtiInfo[listViewItem.getPmbti()]);
        if(listViewItem.getPsmoke()==1)
            isSmoke.setText("YES");
        else
            isSmoke.setText("NO");

        Button acptButton = convertView.findViewById(R.id.response_accept_btn);
        final int Pos = position;
        acptButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User appuser = User.getInstance();
                if(appuser.info.getIsMatched()==1){
                    Toast info = Toast.makeText(context,"이미 정해진 룸메이트가 존재합니다.!",Toast.LENGTH_LONG);
                    info.show();
                    return;
                }
                sendRequest(0,appuser.info.getId(),listViewItem.getId(),Pos);
            }
        });

        Button denyButton = convertView.findViewById(R.id.response_deny_btn);
        denyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                final User appuser = User.getInstance();
                sendRequest(1,appuser.info.getId(),listViewItem.getId(),Pos);
            }
        });

        Button showButton = (Button) convertView.findViewById(R.id.profile_response_show_btn);
        showButton.setTag(position);
        showButton.setOnClickListener(this);

        return convertView;
    }

    public void addItem(UserInfo userInfo) {
        UserInfo item = new UserInfo(userInfo);

        listViewItemList.add(item);
    }

    public void onClick(View v) {
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag()) ;
        }
    }

    public void sendRequest(final int type, final String id, final String otherid, final int position){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url;
        if(type ==0)
            url = urlManager.responseAcptURL;
        else
            url = urlManager.responseDenyURL;
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
                            User appuser = User.getInstance();
                            Toast info;
                            info = Toast.makeText(context,"요청을 승락하였습니다.",Toast.LENGTH_LONG);
                            if(type==0){
                                info = Toast.makeText(context,"요청을 승락하였습니다.",Toast.LENGTH_LONG);
                                appuser.info.setIsMatched(1);
                                appuser.info.setMatched_user(new UserInfo(
                                        jsonObj.getString("id"),
                                        jsonObj.getString("password"),
                                        jsonObj.getString("pname"),
                                        jsonObj.getInt("pgender"),
                                        jsonObj.getInt("pmbti"),
                                        jsonObj.getInt("pdormitory"),
                                        jsonObj.getInt("univ"),
                                        jsonObj.getInt("pmajor"),
                                        jsonObj.getString("email"),
                                        jsonObj.getInt("psmoke"),
                                        jsonObj.getString("pcomment"),
                                        jsonObj.getInt("page"),
                                        jsonObj.getString("pcontact"),
                                        jsonObj.getInt("pstime"),
                                        jsonObj.getInt("pshour"),
                                        jsonObj.getInt("hasMatchBefore"),
                                        0,
                                        null));
                            }
                            else
                                info = Toast.makeText(context,"요청을 거부하고 차단하였습니다.",Toast.LENGTH_LONG);
                            info.show();
                        }
                        else{
                            Toast info = Toast.makeText(context,jsonObj.getString("reason"),Toast.LENGTH_LONG);
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
                params.put("otherid",otherid);
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
