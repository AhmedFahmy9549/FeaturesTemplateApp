package com.example.sweelam.featurestemplateapp.Gallery;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.sweelam.featurestemplateapp.R;

import java.util.ArrayList;


public class DetailsGalleryFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    SliderLayout mDemoSlider;
    View view;
    ArrayList<String> modelServices;
    private int lolo;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.details_gallery, container, false);
        mDemoSlider = view.findViewById(R.id.GalleriesSilder);

        //add photo
        Bundle arguments = getArguments();

        modelServices = arguments.getStringArrayList("ArraylistOFimg");
        lolo = arguments.getInt("imgnum",0);
        Log.e("duuuuu",modelServices.toString());

        loadIMG(modelServices);



        return view;
    }

    public void loadIMG(ArrayList<String> arryListy){
        for (int i = 0; i < arryListy.size(); i++) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .image(arryListy.get(i))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putInt("currentImg", i);

            mDemoSlider.addSlider(textSliderView);
        }
        // you can change animasi, time page and anythink.. read more on github
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
/*
                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
*/
        mDemoSlider.setCurrentPosition(lolo);
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

    }


    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
