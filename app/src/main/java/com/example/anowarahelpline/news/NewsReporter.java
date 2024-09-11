package com.example.anowarahelpline.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.anowarahelpline.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NewsReporter extends AppCompatActivity {

    ProgressBar progressBar;
    ListView repoterListView;

    HashMap<String,String> repoterHashMap;

    ArrayList<HashMap<String,String>> repoterArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_reporter);


        progressBar = findViewById(R.id.repotereProgressbar);
        repoterListView = findViewById(R.id.repoterListView);

        RequestQueue queue = Volley.newRequestQueue(NewsReporter.this);

        String url = "https://osmanparvej.000webhostapp.com/apps/chittagongHelpLine/repoerter.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String repoterName = jsonObject.getString("name");
                        String repoterNewsName = jsonObject.getString("news");
                        String repoterNumber = jsonObject.getString("number");

                        repoterHashMap = new HashMap<>();
                        repoterHashMap.put("name", repoterName);
                        repoterHashMap.put("newsName", repoterNewsName);
                        repoterHashMap.put("phone", repoterNumber);
                        repoterArrayList.add(repoterHashMap);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                MyAdapter adapter = new MyAdapter();
                repoterListView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(NewsReporter.this, "Network timeout. Please try again.", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(jsonArrayRequest);

    }


    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return repoterArrayList.size();
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
            View view = layoutInflater.inflate(R.layout.repoter_name,null);

            TextView repoterName= view.findViewById(R.id.repooooooName);
            TextView repoterNewsName = view.findViewById(R.id.repooooooNewsName);
            TextView repoterNumber = view.findViewById(R.id.repooooooPhone);
            LottieAnimationView pohoneCall = view.findViewById(R.id.repoterNamePhoneLogo);

            repoterHashMap = repoterArrayList.get(position);

            String namme = repoterHashMap.get("name");
            String newsnamme = repoterHashMap.get("newsName");
            String repoternmmber = repoterHashMap.get("phone");

            repoterName.setText(namme);
            repoterNewsName.setText(newsnamme);
            repoterNumber.setText(repoternmmber);

            pohoneCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intents = new Intent(Intent.ACTION_DIAL);
                    intents.setData(Uri.parse("tel:"+repoternmmber));
                    startActivity(intents);
                }
            });



            return view;

        }
    }





}