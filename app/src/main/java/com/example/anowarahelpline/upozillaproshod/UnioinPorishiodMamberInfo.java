package com.example.anowarahelpline.upozillaproshod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.example.anowarahelpline.R;

import java.util.ArrayList;
import java.util.HashMap;

public class UnioinPorishiodMamberInfo extends AppCompatActivity {
    ArrayList<HashMap<String,String>> infoList;
    ListView listView;
    TextView unionNameTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unioin_porishiod_mamber_info);

        listView = findViewById(R.id.union_memeber_info_List);
        unionNameTxt = findViewById(R.id.unionInfoName);

        Intent intent = getIntent();
        if(intent!=null){
            infoList = (ArrayList<HashMap<String,String>>) intent.getSerializableExtra("info list");


        }
        String unionName = intent.getStringExtra("uniName");
        unionNameTxt.setText(unionName);
        UpMemberinfoListAdapter adapter = new UpMemberinfoListAdapter();
        listView.setAdapter(adapter);

    }

    public class UpMemberinfoListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return infoList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.union_info_item,null);

            TextView txtName = view.findViewById(R.id.union_memeber_name);
            TextView txtPost = view.findViewById(R.id.union_memeber_post);
            TextView txtPhone = view.findViewById(R.id.union_memeber_phone);
            LottieAnimationView call = view.findViewById(R.id.union_memeber_call_icon);

            HashMap<String,String> hashMap = infoList.get(position);

            String name = hashMap.get("name");
            String post = hashMap.get("post");
            String phone = hashMap.get("phone");

            txtName.setText(name);
            txtPost.setText(post);
            txtPhone.setText(phone);
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intents = new Intent(Intent.ACTION_DIAL);
                    intents.setData(Uri.parse("tel:"+phone));
                    startActivity(intents);
                }
            });

            return view;
        }
    }
}