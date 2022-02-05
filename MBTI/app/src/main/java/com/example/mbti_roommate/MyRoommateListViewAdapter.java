package com.example.mbti_roommate;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class MyRoommateListViewAdapter extends BaseAdapter {
    private ArrayList<UserInfo> listViewItemList= new ArrayList<UserInfo>();

    public MyRoommateListViewAdapter(){ }

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
        convertView = inflater.inflate(R.layout.profile_info, parent, false);

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

        return convertView;
    }

    public void addItem(UserInfo userInfo) {
        UserInfo item = new UserInfo(userInfo);
        listViewItemList.add(item);
    }

}
