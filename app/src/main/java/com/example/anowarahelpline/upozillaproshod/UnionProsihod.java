package com.example.anowarahelpline.upozillaproshod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.trusted.sharing.ShareTarget;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLanguage;
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

public class UnionProsihod extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayList<String> unionList = new ArrayList<>();

    ArrayList<ArrayList<HashMap<String,String>>> arrayLists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_union_prosihod);

        listView = findViewById(R.id.union_porshid_listview);
        progressBar = findViewById(R.id.union_porshod_prograssbar);

        String url="https://osman160128.github.io/uniporishod/union.json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        UnionPorshodList unionPorshodListAdapter = new UnionPorshodList();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                    try {
                        for(int i = 0;i<response.length();i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String unionName = jsonObject.getString("up name");

                            ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
                            JSONArray upMemberArray = jsonObject.getJSONArray("up mamber");

                            for(int j = 0;j<upMemberArray.length();j++){
                                JSONObject jsonObjectUpMember = upMemberArray.getJSONObject(j);
                                String name = jsonObjectUpMember.getString("name");
                                String post = jsonObjectUpMember.getString("post");
                                String phone = jsonObjectUpMember.getString("phone");

                                HashMap<String,String> hashMap =new HashMap<>();

                                hashMap.put("name",name);
                                hashMap.put("post",post);
                                hashMap.put("phone",phone);

                                arrayList.add(hashMap);

                            }
                            arrayLists.add(arrayList);
                            unionList.add(unionName);

                            listView.setAdapter(unionPorshodListAdapter);
                            progressBar.setVisibility(View.GONE);


                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);

                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UnionProsihod.this, ""+error, Toast.LENGTH_SHORT).show();
                Log.d("union error",error.getMessage());
                progressBar.setVisibility(View.GONE);

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    public class UnionPorshodList extends BaseAdapter{

        @Override
        public int getCount() {
            return unionList.size();
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
            View view = layoutInflater.inflate(R.layout.union_porishod_item,null);

            TextView unionNameTxt = view.findViewById(R.id.unionName);

            String unionName = unionList.get(position);
            unionNameTxt.setText(unionName);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UnionProsihod.this, UnioinPorishiodMamberInfo.class);
                    intent.putExtra("info list",arrayLists.get(position));
                    intent.putExtra("uniName",unionName);
                    startActivity(intent);
                }
            });
            return view;
        }
    }
}