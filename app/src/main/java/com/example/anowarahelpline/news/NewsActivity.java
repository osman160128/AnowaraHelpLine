package com.example.anowarahelpline.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class NewsActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView newspaperListView;

    ArrayList<HashMap<String,String>> newspaperArrayList= new ArrayList<>();

    HashMap<String,String> newspaperHasmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        progressBar = findViewById(R.id.newsPaperProgressbar);
        newspaperListView = findViewById(R.id.newspaperListView);

        RequestQueue queue = Volley.newRequestQueue(NewsActivity .this);

        String url = "https://osmanparvej.000webhostapp.com/apps/chittagongHelpLine/ctgNewsPaper.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progressBar.setVisibility(View.GONE);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String newspaperName = jsonObject.getString("name");
                        String newspaperLink = jsonObject.getString("newslink");

                        newspaperHasmap = new HashMap<>();
                        newspaperHasmap.put("name", newspaperName);
                        newspaperHasmap.put("newspaperLink", newspaperLink);
                        newspaperArrayList.add(newspaperHasmap);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                MyAdapter adapter = new MyAdapter();
                newspaperListView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                throw new RuntimeException(error);
            }
        });

        queue.add(jsonArrayRequest);
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return newspaperArrayList.size();

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
            View view = layoutInflater.inflate(R.layout.news_paper_item_list,null);

            TextView newspaperName = view.findViewById(R.id.newsPaperName);
            LinearLayout linearLayout =view.findViewById(R.id.newspapeLinerlayout);

            newspaperHasmap = newspaperArrayList.get(position);

            String namme = newspaperHasmap.get("name");
            String newspaperLink = newspaperHasmap.get("newspaperLink");
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(NewsActivity.this, NewsPaperDetailsShowActivity.class);
                    intent.putExtra("url",newspaperLink);
                    startActivity(intent);

                }
            });


            newspaperName.setText(namme);
            return view;

        }
    }

}