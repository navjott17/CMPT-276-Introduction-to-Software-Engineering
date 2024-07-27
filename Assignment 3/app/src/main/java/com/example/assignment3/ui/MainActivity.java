package com.example.assignment3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assignment3.R;
import com.example.assignment3.model.Configuration;

//welcome page
public class MainActivity extends AppCompatActivity {
    private static int TIME_OUT = 8000;
    private Handler handler;
    private Runnable runnable;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                next_activity();
            }
        };

        splash_time();
        skipbutton();
    }

    //10 sec timer to go to the main menu
    private void splash_time() {
        handler.postDelayed(runnable, TIME_OUT);
    }

    //skips the animation and proceed to main menu
    private void skipbutton() {
        Button btn = (Button) findViewById(R.id.skip_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_activity();
            }
        });
    }

    //switching to next activity
    private void next_activity() {
        handler.removeCallbacks(runnable);
        Intent intent = Main_menu.makeIntent(MainActivity.this);
        startActivity(intent);
        finish();
    }

}