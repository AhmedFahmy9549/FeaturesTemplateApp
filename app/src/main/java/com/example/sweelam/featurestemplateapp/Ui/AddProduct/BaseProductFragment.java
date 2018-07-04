package com.example.sweelam.featurestemplateapp.Ui.AddProduct;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sweelam.featurestemplateapp.R;
import com.example.sweelam.featurestemplateapp.Ui.AddProduct.Models.LocationModel;
import com.example.sweelam.featurestemplateapp.utils.CustomToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

/**
 * Created by Hima on 6/25/2018.
 */

public abstract class  BaseProductFragment extends BaseAddFragment {
    public EditText EdPrice;
    ArrayList<String> CategoryNameArray;
    ArrayList<LocationModel> arrayModel;
    public Spinner spinnerCategory;
    public int category, numStatus, radiogroubValidation = 55, numRate;
    public String getTitle = "", getPrice = "", getDesc = "", getAddress = "", getPhone = "", getPhone2 = "" ;
    public RadioGroup radioGroupStatus;
    private RequestQueue mRequestQueue;
    private String url;
    private int MethodID;
    private JSONObject object;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_product, parent, false);

        //Now specific components here (you can initialize Buttons etc)
        initProductExtras(R.id.Add_product_Price,R.id.Add_product_spinner_category,R.id.Add_product_radio_group_status);
        initViews(R.id.Add_product_Title, R.id.Add_product_Phone, R.id.Add_product_Phone2,
                R.id.Add_product_Desc, R.id.Add_product_Adress, R.id.Add_product_spinner_city,
                R.id.Add_product_spinner_area, R.id.Add_product_linear_area, R.id.Add_product_Image,
                R.id.Add_product_Add2ndPhone, R.id.Add_product_FinishBtn);
        onCreateCustom();
/*
        getCategory();
*/
        setStatus();
        return view;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethodID(int methodID) {
        MethodID = methodID;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }

    public void postProduct(String url, int MethodID, JSONObject object) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                MethodID, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("addpro", "res" + response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("addpro", "err" + error);
                if (error == null || error.networkResponse == null) {
                    return;
                }

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                Log.e("addpro", "statusCode  " + statusCode);

                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data, "UTF-8");
                    Log.e("addpro", "body " + body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }
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
        mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(jsonObjReq);
    }

    @Override
    public void Validation() {

        getTitle = EdTitle.getText().toString();
        getPrice = EdPrice.getText().toString();
        getDesc = EdDesc.getText().toString();
        getAddress = EdAddress.getText().toString();
        getPhone = EdPhone.getText().toString();
        getPhone2 = EdPhone2.getText().toString();

        //getTitle,getPrice,getDesc,getAddress,getPhone,city,area,category,numStatus,getEncodedImage
        if (getTitle.equals("") || getTitle.length() == 0
                || getPrice.equals("") || getPrice.length() == 0
                || getDesc.equals("") || getDesc.length() == 0
                || getAddress.equals("") || getAddress.length() == 0
                || getPhone.equals("") || getPhone.length() == 0) {
            new CustomToast().Show_Toast(getActivity(), view, "All fields are required.");
        } else if (category == -1) {
            new CustomToast().Show_Toast(getActivity(), view, "Please Select Product Category.");
        } else if (numStatus == 55) {
            new CustomToast().Show_Toast(getActivity(), view, "Please Select Product Status.");
        } else if (getDesc.length() < 21) {
            new CustomToast().Show_Toast(getActivity(), view, "description must be higher than 20 letter");
        } else if (getEncodedImage.equals("") || getEncodedImage.length() == 0) {
            new CustomToast().Show_Toast(getActivity(), view, "Please add Image");
        } else {
            Toast.makeText(getActivity(), "Do Do.", Toast.LENGTH_SHORT)
                    .show();
            submit();
            postProduct(url,MethodID,object);
        }
    }

    public abstract void submit();

/*
    public void getCategory() {
        CategoryNameArray = new ArrayList<>();
        arrayModel = new ArrayList<>();


        GetProductAdCategoriesRequest getProductAdCategoriesRequest = new GetProductAdCategoriesRequest(getActivity(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String categName = object.getString("en_name");
                        int categId = object.getInt("id");
                        LocationModel model = new LocationModel(categName, categId);
                        CategoryNameArray.add(categName);
                        arrayModel.add(model);
                    }
                    if (getActivity() != null) {
                        HintSpinner<String> hintSpinner = new HintSpinner<>(
                                spinnerCategory,
                                // Default layout - You don't need to pass in any layout id, just your hint text and
                                // your list data
                                new HintAdapter<String>(getActivity(), "Choose Category", CategoryNameArray),
                                new HintSpinner.Callback<String>() {
                                    @Override
                                    public void onItemSelected(int position, String itemAtPosition) {
                                        // Here you handle the on item selected event (this skips the hint selected event)
                                        //String item = adapterView.getItemAtPosition(i).toString();

                                        LocationModel locationModel = arrayModel.get(position);
                                        category = locationModel.getLocId();
                                        //toshazly
                                    }
                                });
                        hintSpinner.init();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        getProductAdCategoriesRequest.start();


    }
*/

    public void setStatus() {
        // get selected radio button from radioGroup
        radioGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {

                    case R.id.Add_product_radio_status1:
                        numStatus = 1;
                        radiogroubValidation = 1;
                        break;
                    case R.id.Add_product_radio_status2:
                        numStatus = 2;
                        radiogroubValidation = 2;
                        break;
                    default:
                        numRate = 55;
                        radiogroubValidation = 55;
                        break;
                }
            }
        });


    }

    public void initProductExtras(int price , int category , int status) {
        EdPrice = view.findViewById(price);
        spinnerCategory = view.findViewById(category);
        radioGroupStatus = view.findViewById(status);
    }

    public abstract void onCreateCustom();


}
