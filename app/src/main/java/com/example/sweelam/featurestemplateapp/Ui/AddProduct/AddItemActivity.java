package com.example.sweelam.featurestemplateapp.Ui.AddProduct;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.sweelam.featurestemplateapp.R;
import com.example.sweelam.featurestemplateapp.utils.Utils;


public class AddItemActivity extends AppCompatActivity {
    int vald;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();

        setContentView(R.layout.add_item);
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent extra = getIntent();
        vald = extra.getIntExtra("Add", 0);

        if (vald == 1001) {
            ProductFragment();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ProductFragment() {
        AddProductTry fragment = new AddProductTry();
        Bundle arguments = new Bundle();
        arguments.putInt("duck", 55);
        fragment.setArguments(arguments);
        final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.additem_Include, fragment, Utils.addproduct);
        ft.commit();
    }






}
