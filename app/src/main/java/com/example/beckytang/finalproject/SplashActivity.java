package com.example.beckytang.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private Timer t;
    private int timerTime = 1000;
    private Animation shutterUpAnim;
    private Animation shutterDownAnim;
    // private ImageView ivLogo;
    private TextView tvTopShutter;
    private TextView tvBottomShutter;
    private boolean startAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        t = new Timer();

        // ivLogo = (ImageView) findViewById(R.id.ivLogo);
        shutterUpAnim = AnimationUtils.loadAnimation(this, R.anim.translate_up);
        shutterDownAnim = AnimationUtils.loadAnimation(this, R.anim.translate_down);
        tvTopShutter = (TextView) findViewById(R.id.tvTopShutter);
        tvBottomShutter = (TextView) findViewById(R.id.tvBottomShutter);
        startAnimation = true;


        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (startAnimation) {
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    tvTopShutter.setVisibility(View.VISIBLE);
                                    tvBottomShutter.setVisibility(View.VISIBLE);
                                    tvTopShutter.startAnimation(shutterDownAnim);
                                    tvBottomShutter.startAnimation(shutterUpAnim);
                                    //   ivLogo.startAnimation(shutterAnim);
                                }
                            }
                    );
                    startAnimation = false;
                } else {
                    onShutterEnd();
                }
            }
        }, 2000, timerTime);

    }

    private void onShutterEnd() {
        Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentMain);

        t.cancel();
        t.purge();

        finish();
    }

}

