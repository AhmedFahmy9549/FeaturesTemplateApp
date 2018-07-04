package com.example.sweelam.featurestemplateapp.Ui.AddProduct;

import com.android.volley.Request;
import com.example.sweelam.featurestemplateapp.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hima on 6/25/2018.
 */

public class AddProductTry extends BaseProductFragment {

    private int MethodID = Request.Method.POST;
    private String url = Constants.basicUrl + "/product-ads";


    @Override
    public void onCreateCustom() {
        //fragment name
        setmFragmentName("Add Product");
    }

    private JSONObject mJson(){
        JSONObject jsonobject_one = new JSONObject();
        JSONObject jsonobject_Two = new JSONObject();
        JSONArray PhoneArray = new JSONArray();
        try {
            PhoneArray.put(getPhone);
            if (getPhone2 != null && !getPhone2.isEmpty()) {
                PhoneArray.put(getPhone2);
            }
            jsonobject_one.put("userId", userID);
            jsonobject_one.put("title", getTitle);
            jsonobject_one.put("description", getDesc);
            jsonobject_one.put("price", getPrice);
            jsonobject_one.put("regionId", city);
            jsonobject_one.put("cityId", area);
            jsonobject_one.put("address", getAddress);
            jsonobject_one.put("status", numStatus);
            jsonobject_one.put("categoryId", category);
            jsonobject_one.put("phone", PhoneArray);

            //phone array
            //file object
            jsonobject_Two.put("file", getEncodedImage);
            jsonobject_one.put("img", jsonobject_Two);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonobject_one;
    }

    @Override
    public void submit() {
        setMethodID(MethodID);
        setUrl(url);
        setObject(mJson());
    }


}
