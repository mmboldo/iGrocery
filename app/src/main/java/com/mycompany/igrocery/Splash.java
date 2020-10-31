package com.mycompany.igrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3500;

    //variables
    Animation topAnim, bottomAnim;
    ImageView imageIcon, imageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        imageIcon = findViewById(R.id.iconIV);
        imageText = findViewById(R.id.logoNameIV);

        imageIcon.setAnimation(topAnim);
        imageText.setAnimation(bottomAnim);

        //Splash
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

    }
}