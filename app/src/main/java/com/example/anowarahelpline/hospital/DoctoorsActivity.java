package com.example.anowarahelpline.hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
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
import com.android.volley.toolbox.Volley;
import com.example.anowarahelpline.R;

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
                                String phone1= clinic.getString("phone 1"); // clinic.opString("phone 1","")opstring is used for if i dont have phone1 is jsonf file it will defult  ""
                                String phone2= clinic.getString("phone 2");
                                String phone3= clinic.getString("phone 3");
                                String phone4= clinic.getString("phone 4");
                                String hospitalName = clinic.getString("hospitalname");
                                JSONArray doctorArray = clinic.getJSONArray("dcotors");


                                if(doctorArray.length()>0){
                                    for (int j = 0; j < doctorArray.length(); j++) {
                                        JSONObject doctor = doctorArray.getJSONObject(j);
                                        String name = doctor.getString("name");
                                        String specialist = doctor.getString("spacalist");
                                        String degree = doctor.getString("degree");
                                        String time = doctor.getString("time");

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("Doctor Name", name);
                                        hashMap.put("Specialist", specialist);
                                        hashMap.put("Degree", degree);
                                        hashMap.put("Time", time);
                                        hashMap.put("phone1", phone1);
                                        hashMap.put("phone2", phone2);
                                        hashMap.put("phone3", phone3);
                                        hashMap.put("phone4", phone4);
                                        hashMap.put("hospitalName",hospitalName);

                                        arrayList.add(hashMap);
                                    }

                                    filteredArrayList.addAll(arrayList); // Initially, both lists are the same
                                    listView.setAdapter(doctorAdapter);
                                    progressBar.setVisibility(View.GONE);
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DoctoorsActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("errorss",""+e.toString());
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
            TextView drHospitalNameTxt = convertView.findViewById(R.id.drHospitalName);

            HashMap<String, String> hashMap = filteredArrayList.get(position);

            String name = hashMap.get("Doctor Name");
            String specialist = hashMap.get("Specialist");
            String degree = hashMap.get("Degree");
            String time = hashMap.get("Time");
            String phone1 = hashMap.get("phone1");
            String phone2 = hashMap.get("phone2");
            String phone3 = hashMap.get("phone3");
            String phone4 = hashMap.get("phone4");
            String hospitalName = hashMap.get("hospitalName");

            
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCallDialog(phone1,phone2,phone3,phone4);
                }
            });

            drName.setText(name);
            drSpecialist.setText(specialist);
            drDegree.setText(degree);
            drTime.setText(time);
            drHospitalNameTxt.setText(hospitalName);

            return convertView;
        }
    }

    private void showCallDialog(String phone1,String phone2,String phone3,String phone4) {

        LayoutInflater layoutInflater = getLayoutInflater();
        View dialogView  = layoutInflater.inflate(R.layout.call_layout,null);

        TextView phoneNUmber1 = dialogView.findViewById(R.id.phoneNumverTxt1);
        TextView phoneNUmber2 = dialogView.findViewById(R.id.phoneNumverTxt2);
        TextView phoneNUmber3 = dialogView.findViewById(R.id.phoneNumverTxt3);
        TextView phoneNUmber4 = dialogView.findViewById(R.id.phoneNumverTxt4);


        CardView phoneNumberBtn1= dialogView.findViewById(R.id.phoneCallCardView1);
        CardView phoneNumberBtn2= dialogView.findViewById(R.id.phoneCallCardView2);
        CardView phoneNumberBtn3= dialogView.findViewById(R.id.phoneCallCardView3);
        CardView phoneNumberBtn4= dialogView.findViewById(R.id.phoneCallCardView4);

        LinearLayout phoneLayout1 = dialogView.findViewById(R.id.phoneCallLayout1);
        LinearLayout phoneLayout2 = dialogView.findViewById(R.id.phoneCallLayout2);
        LinearLayout phoneLayout3 = dialogView.findViewById(R.id.phoneCallLayout3);
        LinearLayout phoneLayout4 = dialogView.findViewById(R.id.phoneCallLayout4);

        if(!phone1.isEmpty()){
            phoneNUmber1.setText(phone1);
            phoneNumberBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone1));
                    startActivity(intent);
                }
            });
        }else{
            phoneLayout1.setVisibility(View.GONE);
        }

        if(!phone2.isEmpty()){
            phoneNUmber2.setText(phone2);
            phoneNumberBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone2));
                    startActivity(intent);
                }
            });
        }else{
            phoneLayout2.setVisibility(View.GONE);
        }

        if(!phone3.isEmpty()){
            phoneNUmber3.setText(phone3);
            phoneNumberBtn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone3));
                    startActivity(intent);
                }
            });
        }else{
            phoneLayout3.setVisibility(View.GONE);
        }

        if(!phone4.isEmpty()){
            phoneNUmber4.setText(phone4);
            phoneNumberBtn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone4));
                    startActivity(intent);
                }
            });
        }else{
            phoneLayout4.setVisibility(View.GONE);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Call Number")
                .setView(dialogView) // Set the custom view
                .setPositiveButton("Close", null)
                .show();

    }
}