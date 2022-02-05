package com.example.mbti_roommate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileEditActivity extends AppCompatActivity {
    ListView listView;
    MyRoommateListViewAdapter adapter;
    User appuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        appuser = User.getInstance();
        listView = findViewById(R.id.myRoommateListView);
        adapter = new MyRoommateListViewAdapter();
        listView.setAdapter(adapter);
        if(appuser.info.getIsMatched()==1)
            adapter.addItem(new UserInfo(appuser.info.getMatched_user()));
        else{
            TextView roommateText = findViewById(R.id.roommateTextView);
            roommateText.setText("아직 룸메이트가 없습니다.");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserInfo userInfo = (UserInfo)parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), IndividualProfileResult.class);
                intent.putExtra("UserInfo",userInfo);
                startActivity(intent);
            }
        });
    }
}
