package com.example.sweelam.featurestemplateapp.Gallery;

/**
 * Created by Hima on 7/4/2018.
 */

public class GalleryActivityTry extends GalleryActivity {
    String url = "http://gms-sms.com:89/gmsred/api/galleries";
    String imgArrayKey = "data";
    String imgKey = "image";


    @Override
    public void onCreateCustom() {

        setTitle("Gallery");
        setImageArrayKey(imgArrayKey);
        setImageKey(imgKey);
        setUrl(url);
    }
}
