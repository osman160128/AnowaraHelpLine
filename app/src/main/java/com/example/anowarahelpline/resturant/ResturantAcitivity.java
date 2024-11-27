package com.example.anowarahelpline.resturant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.example.anowarahelpline.upozillaproshod.UnionProsihod;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ResturantAcitivity extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;

    ArrayList<HashMap<String,String>>  resturantList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_acitivity);

        listView = findViewById(R.id.resturantListView);
        String url = "https://osman160128.github.io/resturant/resturantapi.json";

        progressBar = findViewById(R.id.resturantPrograssbar);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // URL to fetch data (Replace with your actual URL or local JSON file)
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progressBar.setVisibility(View.GONE);
                // Parse the JSON response
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject restaurantJson = response.getJSONObject(i);
                        String name = restaurantJson.getString("name");
                        String address = restaurantJson.getString("address");
                        String phone = restaurantJson.getString("phone");
                        String url = restaurantJson.getString("url");

                        HashMap<String, String> hashMap = new HashMap<>();

                        hashMap.put("name",name);
                        hashMap.put("address",address);
                        hashMap.put("phone",phone);
                        hashMap.put("url",url);
                        Log.d("image url",url);

                        resturantList.add(hashMap);

                    }

                    ResturanAdapter resturanAdapter = new ResturanAdapter();
                    listView.setAdapter(resturanAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ResturantAcitivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ResturantAcitivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    public class ResturanAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return resturantList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View views = layoutInflater.inflate(R.layout.resturant_item,null);

            TextView resturntName = views.findViewById(R.id.resturantName);
            TextView resturantPhone = views.findViewById(R.id.resturantPhone);
            TextView resturantAddress= views.findViewById(R.id.resturantAddress);
            LinearLayout callLayout = views.findViewById(R.id.callResturanBtn);
            ImageView imageView = views.findViewById(R.id.resturantImage);

            HashMap<String,String> hashMap = resturantList.get(i);
            String name =hashMap.get("name");
            String address = hashMap.get("address");
            String phone = hashMap.get("phone");
            String imageUrl = hashMap.get("url");

            Log.d("image url",imageUrl);

            resturntName.setText(name);
            resturantPhone.setText(phone);
            resturantAddress.setText(address);

            Picasso.get()
                    .load(imageUrl)
                    .into(imageView);

            callLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intents = new Intent(Intent.ACTION_DIAL);
                    intents.setData(Uri.parse("tel:"+phone));
                    startActivity(intents);
                }
            });
            return views;
        }
    }
}