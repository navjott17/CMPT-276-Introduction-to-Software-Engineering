package com.example.a2.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.a2.Model.Lens;
import com.example.a2.Model.LensManager;
import com.example.a2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Main Activity";
    public static final String THE_ANSWER = "the answer";
    public static final String LENS_INDEX = "parameter name";
    private LensManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ColorDrawable color
                = new ColorDrawable(Color.parseColor("#CDDC39"));

        // Set BackgroundDrawable
        toolbar.setBackgroundDrawable(color);

        //floating action button on click (goes to lens detail page)
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
           Intent i=LensDetail.launchIntent(MainActivity.this,"Hello",0);
           startActivityForResult(i, 42);
        });

        manager= LensManager.getInstance();
        getLastManagerforSharedPreferences();
        populateListView();
        registerClickedCallback();
    }

    //refreshes the listview to add the lens that the user has saved.
    @Override
    protected void onResume() {
        super.onResume();
        populateListView();
    }

    //populating lens manager
    public void populateLensManager(){
        manager.add(new Lens("Canon", 1.8, 50));
        manager.add(new Lens("Tamron", 2.8, 90));
        manager.add(new Lens("Sigma", 2.8, 200));
        manager.add(new Lens("Nikon", 4, 200));
    }

    //populate the lens on the front page.
    public void populateListView() {

        String[] list_of_lens=new String[manager.size()];
        for(int i=0;i<manager.size();i++) {
            Lens temp=manager.get(i);
            list_of_lens[i] = temp.getMake() + "  " +temp.getFocalLength()+"mm  F"+temp.getMaxAperture();
        }

        //for empty state when there is no lens to display
        if(manager.size() == 0) {
            String[] temp=new String[1];
            temp[0]="Click the + button to add lens.";
            TextView text=(TextView) findViewById(R.id.textView5);
            text.setText("No Lens to display");
            ArrayAdapter<String> temp_adapter=new ArrayAdapter<String>(this,R.layout.lens_list, temp);
            ListView list = (ListView) findViewById(R.id.listview);
            list.setAdapter(temp_adapter);
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.lens_list, list_of_lens);
            ListView list = (ListView) findViewById(R.id.listview);
            TextView text1=(TextView) findViewById(R.id.textView5);
            text1.setText("Select the Lens to use : ");
            list.setAdapter(adapter);
        }
    }

    private void registerClickedCallback() {
        ListView list = (ListView) findViewById(R.id.listview);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView text_view=(TextView) view;
                text_view.setTextColor(Color.YELLOW);
                Intent intent = CalculateDOF.makeIntent(MainActivity.this);
                intent.putExtra(LENS_INDEX, position);
                startActivity(intent);
            }
        });
    }

    public void getLastManagerforSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("manager", MODE_PRIVATE);
        int size = prefs.getInt("size", 0);
        Log.i(TAG, "size is: " + size);
        LensManager manager = LensManager.getInstance();
        if (size == 0) {
            populateLensManager();
        }
        else {
            for(int i = 0; i < size; i++) {
                String make = prefs.getString("make " + i, null);
                double maxAperture = Double.parseDouble(prefs.getString("maxAperture " + i, "0"));
                int focalLength = prefs.getInt("focalLength " + i, 0);

                Lens lens =new Lens(make, maxAperture, focalLength);

                manager.add(lens);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int answer;
        switch(requestCode){
            case 1:
                answer = data.getIntExtra(THE_ANSWER, 0);
                break;
            case 2:
                answer = data.getIntExtra(THE_ANSWER, 0);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}