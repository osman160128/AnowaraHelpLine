package com.example.anowarahelpline.ambulance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

public class AmbulanceAcitivity extends AppCompatActivity {


    ProgressBar progressBar;

    ListView listView;



    HashMap<String, String> ambulanceHashMap;

    ArrayList<HashMap<String, String>> ambulanceArraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_acitivity);

        progressBar = findViewById(R.id.ambulancrPrograssbar);
        listView = findViewById(R.id.ambulanceListView);


        RequestQueue queue = Volley.newRequestQueue(AmbulanceAcitivity.this);

        String url = "https://osmanparvej.000webhostapp.com/apps/chittagongHelpLine/ambulanceList.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String ambulanceName = jsonObject.getString("title");
                        String ambulancePhone = jsonObject.getString("phone");
                        String ambulanceLocation = jsonObject.getString("location");


                        ambulanceHashMap = new HashMap<>();
                        ambulanceHashMap.put("name", ambulanceName);
                        ambulanceHashMap.put("phone", ambulancePhone);
                        ambulanceHashMap.put("location", ambulanceLocation);
                        ambulanceArraylist.add(ambulanceHashMap);


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                MyAdapter adapter = new MyAdapter();
                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AmbulanceAcitivity.this, "Network timeout. Please try again.", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(jsonArrayRequest);


    }


    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ambulanceArraylist.size();
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
            View view = layoutInflater.inflate(R.layout.ambulance_item,null);

            TextView ambulanceName = view.findViewById(R.id.ambulanceListViewName);
            TextView ambulancePhone = view.findViewById(R.id.ambulanceListViewPhone);
            TextView ambulanceLocation = view.findViewById(R.id.ambulanceListViewLocation);
            CardView call_ambulance = view.findViewById(R.id.call_ambulance);

            ambulanceHashMap = ambulanceArraylist.get(position);

            String name = ambulanceHashMap.get("name");
            String phone = ambulanceHashMap.get("phone");
            String location = ambulanceHashMap.get("location");

            ambulanceName.setText(name);
            ambulancePhone.setText(phone);
            ambulanceLocation.setText(location);
            call_ambulance.setOnClickListener(new View.OnClickListener() {
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