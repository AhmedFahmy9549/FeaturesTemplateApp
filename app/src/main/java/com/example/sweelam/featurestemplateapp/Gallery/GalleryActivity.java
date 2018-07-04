package com.example.sweelam.featurestemplateapp.Gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sweelam.featurestemplateapp.BaseActivity;
import com.example.sweelam.featurestemplateapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public abstract class GalleryActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;
    private ArrayList<String> mArrayList;
    private RequestQueue mRequestQueue;
    private  String  data_logo,url,imageArrayKey,imageKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_gallery);

        //recycler View horizon orientation
        mRecyclerView = findViewById(R.id.Recycler_Galleryz);
        LinearLayoutManager LayoutManagaer = new GridLayoutManager(GalleryActivity.this, 3);
        mRecyclerView.setLayoutManager(LayoutManagaer);
        onCreateCustom();
        parseJSON(1);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImageArrayKey(String imageArrayKey) {
        this.imageArrayKey = imageArrayKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    private void parseJSON(final int x) {

        mArrayList = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray(imageArrayKey);
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject objectdata = dataArray.getJSONObject(i);

                        data_logo = "http://gms-sms.com:89" + objectdata.getString(imageKey);

                        mArrayList.add(data_logo);

                    }
                    mAdapter = new GalleryAdapter(GalleryActivity.this,mArrayList,x);
                    mRecyclerView.setAdapter(mAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        mRequestQueue = Volley.newRequestQueue(GalleryActivity.this);
        mRequestQueue.add(request);
    }
    public abstract void onCreateCustom();
}
