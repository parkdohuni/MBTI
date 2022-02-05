package com.example.mbti_roommate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity implements RequestListViewAdapter.ListBtnClickListener{
    ListView listView;
    RequestListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int i;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        Intent intent = getIntent();
        Bundle bundleObject = getIntent().getExtras();
        ArrayList<UserInfo> uInfos = (ArrayList<UserInfo>)bundleObject.getSerializable("UserInfos");
        listView = findViewById(R.id.requestlistView);
        adapter = new RequestListViewAdapter(this,R.layout.profile_request,uInfos,this);
        Log.e("Object",String.valueOf(adapter==null));
        listView.setAdapter(adapter);

        for(i=0;i<uInfos.size();++i){
            adapter.addItem(uInfos.get(i));
        }

        // (보낸요청함의)총 000개의 보낸 요청이 있습니다. 부분
        TextView txt = (TextView)findViewById(R.id.request_num_text);
        txt.setText(String.format("총 %03d개의 보낸 요청이 있습니다.", i));

    }

    @Override
    public void onListBtnClick(int position) {
        UserInfo userInfo = (UserInfo)adapter.getItem(position);
        Intent intent = new Intent(getApplicationContext(), IndividualProfileResult.class);
        intent.putExtra("UserInfo",userInfo);
        startActivity(intent);
    }
}
