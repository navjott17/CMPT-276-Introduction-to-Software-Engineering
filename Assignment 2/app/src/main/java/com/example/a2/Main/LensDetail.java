package com.example.a2.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.a2.Model.Lens;
import com.example.a2.Model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2.R;

public class LensDetail extends AppCompatActivity {
    private static final String TAG = "LensDetail"; //Tag for log messages
    private LensManager manager;
    private int index;

    public static Intent launchIntent(Context c,String message, int index){
        Intent intent=new Intent(c, LensDetail.class);
        intent.putExtra("Edit Lens", message);
        intent.putExtra("Lens Index", index);
        return intent;
    }

    private void extractData(){
        Intent intent=getIntent();
        String message = intent.getStringExtra("Edit Lens");
        index=intent.getIntExtra("Lens Index", 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lens_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Change Action Bar Color
        ColorDrawable color
                = new ColorDrawable(Color.parseColor("#CDDC39"));

        // Set BackgroundDrawable
        toolbar.setBackgroundDrawable(color);

        manager=LensManager.getInstance();
        setupSaveButton();
        setupCancelButton();
        pre_populate();
    }


    private void setupSaveButton() {
        Button btn = findViewById(R.id.saveBtn);
        btn.setOnClickListener(v -> {
            EditText makeText = findViewById(R.id.makeText);
            EditText focalLengthText = findViewById(R.id.focalLengthText);
            EditText apertureText = findViewById(R.id.apertureText);

            //Conditions for when some input is not provided
            boolean cond1 = makeText.getText().toString().length() == 0;
            boolean cond2 = focalLengthText.getText().toString().length() == 0;
            boolean cond3 = apertureText.getText().toString().length() == 0;


            if (cond1 || cond2 || cond3) {
                Toast.makeText(getApplicationContext(), "Must enter all values!", Toast.LENGTH_SHORT).show();
                return;
            }

            String make = makeText.getText().toString();
            int focalLength = Integer.parseInt(focalLengthText.getText().toString());
            double aperture = Double.parseDouble(apertureText.getText().toString());

            //Error handling
            if (focalLength <= 0) {
                Toast.makeText(getApplicationContext(), "Focal Length must be greater than 0", Toast.LENGTH_SHORT).show();
                return;
            }

            if (aperture < 1.4) {
                Toast.makeText(getApplicationContext(), "Aperture must be greater than or equal to 1.4", Toast.LENGTH_SHORT).show();
                return;
            }

            Lens lens = new Lens(make, aperture, focalLength); //Create temporary lens object
            if(manager.edit_lens)
            {
                manager.get(index).setMake(make);
                manager.get(index).setFocalLength(focalLength);
                manager.get(index).setMaxAperture(aperture);
                Toast.makeText(getApplicationContext(), "Lens Edited", Toast.LENGTH_SHORT)
                        .show(); //A message to the user
            } else {
                manager.add(lens);
                Toast.makeText(getApplicationContext(), "Lens Saved", Toast.LENGTH_SHORT)
                        .show(); //A message to the user
                index = manager.size() - 1;
            }
            setLastManagerforSharedPreferences(); //Store the manager to Shared Preferences
            Log.i(TAG, lens + "saved"); //Log message to indicate lens is saved
            finish(); //kills the LensDetail Activity
            Log.i(TAG, "Killed Lens Detail Activity");
            manager.edit_lens=false;
        });

    }

    private void setupCancelButton() {
        Button btn = findViewById(R.id.cancelBtn);
        btn.setOnClickListener(v -> {
            manager.edit_lens=false;
            finish(); //Kills the LensDetail Activity
            Log.i(TAG, "Killed Lens Detail Activity");
        });
    }

    private void pre_populate() {
        if(manager.edit_lens)
        {
            extractData();
            TextView make=(TextView)findViewById(R.id.makeText);
            make.setText(manager.get(index).getMake());
            TextView focalLength=(TextView)findViewById(R.id.focalLengthText);
            focalLength.setText(""+manager.get(index).getFocalLength());
            TextView aperture=(TextView)findViewById(R.id.apertureText);
            String ap=Double.toString(manager.get(index).getMaxAperture());
            aperture.setText(""+ap);
        }
    }

    public void setLastManagerforSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("manager", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        LensManager manager = LensManager.getInstance();
        editor.putInt("size", manager.size());
        int i = 0;
        for(Lens lens : manager) {
            editor.putString("make " + i, lens.getMake());
            editor.putString("maxAperture " + i, "" + lens.getMaxAperture());
            editor.putInt("focalLength " + i, lens.getFocalLength());
            i++;
        }
        editor.apply();
    }
}