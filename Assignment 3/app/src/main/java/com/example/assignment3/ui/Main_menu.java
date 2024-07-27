package com.example.assignment3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.assignment3.R;
import com.example.assignment3.model.Configuration;

//main menu page which advances to play, options and help
public class Main_menu extends AppCompatActivity {


    //private LottieAnimationView playbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        play_button();
        optionsMenu();
        helpPage();
    }

    //intent
    public static Intent makeIntent(Context context){
        return new Intent(context, Main_menu.class);
    }

    //play the game
    private void play_button() {
        LottieAnimationView playbtn = (LottieAnimationView) findViewById(R.id.play_btn);
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = play_game.buildIntent(Main_menu.this);
                startActivity(intent);
            }
        });
    }

    //options menu
    private void optionsMenu() {
        Button btn = (Button) findViewById(R.id.optionsbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Options_menu.buildIntent(Main_menu.this);
                startActivity(intent);
            }
        });
    }

    //Help
    private void helpPage() {
        Button btn = (Button) findViewById(R.id.helpbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = help_menu.buildIntent(Main_menu.this);
                startActivity(intent);
            }
        });
    }
}