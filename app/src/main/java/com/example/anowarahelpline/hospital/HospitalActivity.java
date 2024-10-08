package com.example.anowarahelpline.hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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

public class HospitalActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArrayList<HashMap<String, String>> hospitalArraylist = new ArrayList<>();
    ArrayList<HashMap<String, String>> filteredArrayList = new ArrayList<>();
    private HospitalRecylerView hospitalRecylerView;
    HashMap<String, String> hospitalHashMap;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        recyclerView = findViewById(R.id.hospital_recylerView);
        progressBar = findViewById(R.id.hospitalProgressBar);
        searchView = findViewById(R.id.hospitalSearch);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RequestQueue queue = Volley.newRequestQueue(HospitalActivity.this);
        String url = "https://osman160128.github.io/doctors/docotors.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String hospitalName = jsonObject.getString("name");
                        String hospitalAddress = jsonObject.getString("address");
                        String hospitalPhone = jsonObject.getString("phone");

                        hospitalHashMap = new HashMap<>();
                        hospitalHashMap.put("name", hospitalName);
                        hospitalHashMap.put("address", hospitalAddress);
                        hospitalHashMap.put("phone", hospitalPhone);

                        hospitalArraylist.add(hospitalHashMap);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("error", "error is " + e.toString());
                    }
                }

                hospitalRecylerView = new HospitalRecylerView(HospitalActivity.this, hospitalArraylist);
                recyclerView.setAdapter(hospitalRecylerView);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(HospitalActivity.this, "Network timeout. Please try again.", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(jsonArrayRequest);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searhText(newText);
                return true;
            }
        });
    }

    private void searhText(String newText) {
        ArrayList<HashMap<String, String>> dataSearchList = new ArrayList<>();
        for (HashMap<String, String> data : hospitalArraylist){
            //this wiil cheack that if our search item is there

            if (data.get("name").toLowerCase().contains(newText.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()){
            Toast.makeText(HospitalActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            hospitalRecylerView.setSearchList(dataSearchList);
        }
    }
}
