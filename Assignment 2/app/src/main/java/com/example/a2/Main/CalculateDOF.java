package com.example.a2.Main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2.BuildConfig;
import com.example.a2.Model.DoFCalculator;
import com.example.a2.Model.Lens;
import com.example.a2.Model.LensManager;
import com.example.a2.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class CalculateDOF extends AppCompatActivity {
    private LensManager manager;
    private static final String TAG = "CalculateDOF";
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_d_o_f);

        // Change action bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();


        ColorDrawable color
                = new ColorDrawable(Color.parseColor("#CDDC39"));


        actionBar.setBackgroundDrawable(color);

        this.setTitle("Calculate DoF");

        manager=LensManager.getInstance();
        cameraLensName();
        calculate();
        setupDeleteButton();
        setupEditButton();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, CalculateDOF.class);
    }

    private Lens getLens() {
        Intent intent = getIntent();
        index = intent.getIntExtra(MainActivity.LENS_INDEX, 0);
        LensManager manager = LensManager.getInstance();

        return manager.get(index);
    }

    private void cameraLensName() {
        TextView lensName = (TextView) findViewById(R.id.cameraText);
        Lens lens = getLens();
        String Lens=lens.getMake() + " " + lens.getFocalLength() + "mm F" + lens.getMaxAperture();
        lensName.setText(Lens);
    }

    private void calculate() {
                EditText distanceText = (EditText) findViewById(R.id.distanceText);
                EditText apertureText = (EditText) findViewById(R.id.apertureText);
                EditText cocText = (EditText) findViewById(R.id.cocText);

                distanceText.addTextChangedListener(calculateTextWatcher);
                apertureText.addTextChangedListener(calculateTextWatcher);
                cocText.addTextChangedListener(calculateTextWatcher);
    }

    TextWatcher calculateTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //Empty
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //Empty
        }

        @Override
        public void afterTextChanged(Editable s) {
            EditText distanceText = (EditText) findViewById(R.id.distanceText);
            EditText apertureText = (EditText) findViewById(R.id.apertureText);
            EditText cocText = (EditText) findViewById(R.id.cocText);

            //Conditions for when some input is not provided
            boolean cond1 = distanceText.getText().toString().length() == 0;
            boolean cond2 = apertureText.getText().toString().length() == 0;
            boolean cond3 = cocText.getText().toString().length() == 0;


            if (cond1 || cond2 || cond3) {
                return;
            }

            double distance = Double.parseDouble(distanceText.getText().toString());
            double aperture = Double.parseDouble(apertureText.getText().toString());
            double circleOfConfusion = Double.parseDouble(cocText.getText().toString());

            //Error handling
            if (circleOfConfusion <= 0) {
                Toast.makeText(getApplicationContext(), "Circle of confusion must be greater than 0", Toast.LENGTH_SHORT).show();
                return;
            }

            if (distance <= 0) {
                Toast.makeText(getApplicationContext(), "distance to subject must be greater than 0", Toast.LENGTH_SHORT).show();
                return;
            }


            DoFCalculator calculator = new DoFCalculator(getLens(), distance*1000, aperture, circleOfConfusion);

            TextView nearFocalDistText = (TextView) findViewById(R.id.nfdText);
            TextView farFocalDistText = (TextView) findViewById(R.id.ffdText);
            TextView depthOfFieldText = (TextView) findViewById(R.id.dofText);
            TextView hyperfocalDistText = (TextView) findViewById(R.id.hfdText);


            if (aperture < getLens().getMaxAperture()) {
                nearFocalDistText.setText("Invalid aperture");
                farFocalDistText.setText("Invalid aperture");
                depthOfFieldText.setText("Invalid aperture");
                hyperfocalDistText.setText("Invalid aperture");
            }

            else {
                nearFocalDistText.setText(formatM(calculator.nearFocalPoint()/1000) + "m");
                farFocalDistText.setText(formatM(calculator.farFocalPoint()/1000) + "m");
                depthOfFieldText.setText(formatM(calculator.depthOfField()/1000) + "m");
                hyperfocalDistText.setText(formatM(calculator.hyperfocalDistance()/1000) + "m");
            }
        }
    };

    private void setupDeleteButton(){
        Button button=findViewById(R.id.deletebtn);
        button.setOnClickListener(v -> {
            if(manager.size()!=0) {
                Lens lens = getLens();
                manager.getLenses().remove(lens);
                Log.i(TAG, lens + " deleted"); //Log message to indicate lens is saved
                Toast.makeText(getApplicationContext(), "Lens Deleted", Toast.LENGTH_SHORT)
                        .show(); //A message to the user
                setLastManagerforSharedPreferences(); //Store the manager to Shared Preferences
            }
            finish();
        });
    }

    private void setupEditButton() {
        Button button=findViewById(R.id.Editbtn);
        button.setOnClickListener(v -> {
            manager.edit_lens=true;
            Intent intent=LensDetail.launchIntent(CalculateDOF.this,"Edit", index);
            startActivity(intent);
            finish();
        });
    }

    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
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