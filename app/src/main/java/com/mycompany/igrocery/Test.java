package com.mycompany.igrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class est extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextView test = (TextView) findViewById(R.id.tvTest);

        Bundle bundle = getIntent().getExtras();
        float test5 = bundle.getFloat("storeLatitude");
        float test6 = bundle.getFloat("storeLongitude");

        String test3 = bundle.getString("storeName");
        String test4 = bundle.getString("storeAddress");
//        float test5 = bundle.getFloat("storeLatitute");
//        String test6 = getIntent().getStringExtra("storeLongitude");

        test.setText(test3 + "\n" + test4 + "\n" + test5 + "\n" + test6);

    }
}