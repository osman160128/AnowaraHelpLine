package com.example.anowarahelpline.hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

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
import com.example.anowarahelpline.blood.BloodGroupModel;
import com.example.anowarahelpline.news.NewsActivity;
import com.example.anowarahelpline.news.NewsPaperDetailsShowActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctoorsActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> filteredArrayList = new ArrayList<>(); // New filtered list
    ListView listView;
    ProgressBar progressBar;
    SearchView searchView;
    DoctorAdapter doctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctoors);

        listView = findViewById(R.id.drListView);
        progressBar = findViewById(R.id.drPrograssbar);
        searchView = findViewById(R.id.doctorSearch);
        doctorAdapter = new DoctorAdapter();

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

                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("Doctor Name", name);
                                    hashMap.put("Specialist", specialist);
                                    hashMap.put("Degree", degree);
                                    hashMap.put("phone", phone);
                                    hashMap.put("Time", time);

                                    arrayList.add(hashMap);
                                }

                                filteredArrayList.addAll(arrayList); // Initially, both lists are the same
                                listView.setAdapter(doctorAdapter);
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DoctoorsActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DoctoorsActivity.this, "Network timeout. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText(newText); // Filter list based on search query
                return true;
            }
        });
    }

    private void searchText(String newText) {
        filteredArrayList.clear();
        if (newText.isEmpty()) {
            filteredArrayList.addAll(arrayList); // If no query, show all
        } else {
            for (HashMap<String, String> data : arrayList) {
                if (data.get("Doctor Name").toLowerCase().contains(newText.toLowerCase())) {
                    filteredArrayList.add(data); // Add matching doctors to filtered list
                }
            }
        }
        doctorAdapter.notifyDataSetChanged(); // Notify adapter about data changes
    }

    public class DoctorAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return filteredArrayList.size(); // Use filtered list size
        }

        @Override
        public Object getItem(int position) {
            return filteredArrayList.get(position); // Get item from filtered list
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = getLayoutInflater();
                convertView = layoutInflater.inflate(R.layout.dotor_name, null);
            }

            TextView drName = convertView.findViewById(R.id.drNameTxt);
            TextView drDegree = convertView.findViewById(R.id.drDegreeTxt);
            TextView drSpecialist = convertView.findViewById(R.id.drSpecialistTxt);
            TextView drTime = convertView.findViewById(R.id.drTimeTxt);
            TextView call = convertView.findViewById(R.id.callForName);

            HashMap<String, String> hashMap = filteredArrayList.get(position);

            String name = hashMap.get("Doctor Name");
            String specialist = hashMap.get("Specialist");
            String degree = hashMap.get("Degree");
            String phone = hashMap.get("phone");
            String time = hashMap.get("Time");

            call.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            });

            drName.setText(name);
            drSpecialist.setText(specialist);
            drDegree.setText(degree);
            drTime.setText(time);

            return convertView;
        }
    }
}