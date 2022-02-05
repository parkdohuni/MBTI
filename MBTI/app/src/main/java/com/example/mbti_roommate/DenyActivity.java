package com.example.mbti_roommate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DenyActivity extends AppCompatActivity implements DenyListViewAdapter.ListBtnClickListener{

    ListView listView;
    DenyListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deny);
        int i;
        Intent intent = getIntent();
        Bundle bundleObject = getIntent().getExtras();
        ArrayList<UserInfo> uInfos = (ArrayList<UserInfo>)bundleObject.getSerializable("UserInfos");
        listView = findViewById(R.id.denylistView);
        adapter = new DenyListViewAdapter(this,R.layout.profile_deny,uInfos,this);
        listView.setAdapter(adapter);

        for(i=0;i<uInfos.size();++i){
            adapter.addItem(uInfos.get(i));
        }

        // (차단보관함의)총 000개의 차단 프로필이 있습니다. 부분
        TextView txt = (TextView)findViewById(R.id.deny_num_text);
        txt.setText(String.format("총 %03d개의 차단 프로필이 있습니다.", i));
    }

    @Override
    public void onListBtnClick(int position) {
        UserInfo userInfo = (UserInfo)adapter.getItem(position);
        Intent intent = new Intent(getApplicationContext(), IndividualProfileResult.class);
        intent.putExtra("UserInfo",userInfo);
        startActivity(intent);
    }
}
