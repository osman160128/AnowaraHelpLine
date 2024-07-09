package com.example.anowarahelpline.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.anowarahelpline.R;
import com.example.anowarahelpline.news.NewsActivity;
import com.example.anowarahelpline.news.NewsPaperDetailsShowActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctoorsActivity extends AppCompatActivity {


    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();

    ListView listView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctoors);

        listView = findViewById(R.id.drListView);
        progressBar =findViewById(R.id.drPrograssbar);

        String url = "https://osman160128.github.io/doctors/docotors.json";

        RequestQueue requestQueue = Volley.newRequestQueue(this);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject clinic = response.getJSONObject(i);
                                JSONArray doctorArray = clinic.getJSONArray("dcotors");

                                for (int j = 0; j < doctorArray.length(); j++) {
                                    JSONObject doctor = doctorArray.getJSONObject(j);
                                    String name = doctor.getString("name");
                                    String specialist = doctor.getString("spacalist");
                                    String degree = doctor.getString("degree");
                                    String phone = doctor.getString("phone");
                                    String time = doctor.getString("time");


                                    HashMap<String,String> hashMap = new HashMap<>();

                                    hashMap.put("Doctor Name",name);
                                    hashMap.put( "Specialist", specialist);
                                    hashMap.put("Degree", degree);
                                    hashMap.put("phone", phone);
                                    hashMap.put( "Time" , time);

                                    arrayList.add(hashMap);

                                    DoctorAdapter doctorAdapter = new DoctorAdapter();
                                    listView.setAdapter(doctorAdapter);
                                    progressBar.setVisibility(View.GONE);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DoctoorsActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DoctoorsActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();

            }

        });

        requestQueue.add(jsonArrayRequest);
    }


public  class DoctorAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return arrayList.size();
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
        View view = layoutInflater.inflate(R.layout.dotor_name,null);

        TextView drName = view.findViewById(R.id.drNameTxt);
        TextView drDegree = view.findViewById(R.id.drDegreeTxt);
        TextView drSpecelist= view.findViewById(R.id.drSpecialistTxt);
        TextView drTime = view.findViewById(R.id.drTimeTxt);
        TextView call = view.findViewById(R.id.callForName);

        HashMap<String,String> hashMap = arrayList.get(position);

        String name = hashMap.get("Doctor Name");
        String specialist = hashMap.get("Specialist");
        String degree = hashMap.get("Degree");
        String phone = hashMap.get("phone");
        String drtime = hashMap.get( "Time");

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(Intent.ACTION_DIAL);
                intents.setData(Uri.parse("tel:"+phone));
                startActivity(intents);
            }
        });

        drName.setText(name);
        drSpecelist.setText(specialist);
        drDegree.setText(degree);
        drTime.setText(drtime);


        return view;
    }
   }
}