package com.example.sweelam.featurestemplateapp.ServiceProducts;

import android.app.ProgressDialog;
import android.os.Bundle;
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


public class ProductActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    private ArrayList<ProServModel> modelArrayList;
    private ArrayList<String> modelArrayList2;
    private RequestQueue mRequestQueue;
    private  String data_description, data_name, data_logo , images;
    int language = 1;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_product);

        language = getIdLANG();

        if(language==1){
            setTitle("المنتجات");
        }else{
            setTitle("Products");
        }

        //recycler View horizon orientation
        mRecyclerView = findViewById(R.id.Recycler_Product);
        LinearLayoutManager LayoutManagaer = new GridLayoutManager(ProductActivity.this, 2);
        mRecyclerView.setLayoutManager(LayoutManagaer);


        parseJSON(language);

    }

    private void parseJSON(final int x) {

        modelArrayList = new ArrayList<>();

        String url = "http://gms-sms.com:89/gmsred/api/products";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONresponse(response,x);

/*
                save that string in dataBase

                response.toString();
*/

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
        mRequestQueue = Volley.newRequestQueue(ProductActivity.this);
        mRequestQueue.add(request);
    }

    private void JSONresponse(JSONObject response,int x){
        try {
            JSONArray dataArray = response.getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject objectdata = dataArray.getJSONObject(i);
                int data_id = objectdata.getInt("id");

                //String data_description, data_name, data_logo , images;
                if (x == 1) {
                    data_name = objectdata.getString("ar_name");
                    data_description = objectdata.getString("ar_description");
                } else {
                    data_name = objectdata.getString("en_name");
                    data_description = objectdata.getString("en_description");
                }

                data_logo = "http://gms-sms.com:89" + objectdata.getString("logo");

                modelArrayList.add(new ProServModel(data_name, data_description, data_logo));

            }
            mAdapter = new ProductAdapter(ProductActivity.this, modelArrayList,x);
            mRecyclerView.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
