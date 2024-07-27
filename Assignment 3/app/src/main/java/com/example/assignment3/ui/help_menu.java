package com.example.assignment3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.assignment3.R;

//help page containing references, about and how to play.
public class help_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);

        TextView text1 = findViewById(R.id.linktoCmpt276);
        text1.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text2 = findViewById(R.id.welcomeBackground);
        text2.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text3 = findViewById(R.id.theme);
        text3.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text4 = findViewById(R.id.creators);
        text4.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text5 = findViewById(R.id.catLink);
        text5.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text6 = findViewById(R.id.main_menu_link);
        text6.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text7 = findViewById(R.id.spider_link);
        text7.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text8 = findViewById(R.id.play_link);
        text8.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text9 = findViewById(R.id.bg_link);
        text9.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text10 = findViewById(R.id.option_help_link);
        text10.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text11 = findViewById(R.id.jumping_pumpkin_link);
        text11.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text12 = findViewById(R.id.balloon);
        text12.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text13 = findViewById(R.id.congrats);
        text13.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text14 = findViewById(R.id.trick_treat);
        text14.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text15 = findViewById(R.id.Mine_pumpkin);
        text15.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public static Intent buildIntent(Context context) {
        return new Intent(context, help_menu.class);
    }
}