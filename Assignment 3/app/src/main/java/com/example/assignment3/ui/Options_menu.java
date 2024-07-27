package com.example.assignment3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment3.R;
import com.example.assignment3.model.Configuration;

//options menu where user selects num of mines, grid size and resets the game
public class Options_menu extends AppCompatActivity {

    private Configuration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_menu);
        config = Configuration.getInstance();

        int rowSaved=getSavedRows(this);
        int colSaved=getSavedColumns(this);
        int mineSaved=getSavedMines(this);
        Toast.makeText(this, rowSaved + " x " + colSaved + " grid size and " + mineSaved + " mines selected",Toast.LENGTH_SHORT).show();

        setupResetButton();
        rowsColumnOption();
        numMinesOption();
    }

    //intent
    public static Intent buildIntent(Context context) {
        return new Intent(context, Options_menu.class);
    }

    private void setupResetButton() {
        Button button = findViewById(R.id.resetButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Options_menu.this, "Reset Total Number of games", Toast.LENGTH_SHORT).show();
                config.eraseTimesPlayed();
                Intent intent = play_game.reset_game(Options_menu.this);
            }
        });
    }

    //rows and columns option
    @SuppressLint("SetTextI18n")
    private void rowsColumnOption() {
        RadioGroup group = findViewById(R.id.boardSize);
        int[] rows = getResources().getIntArray(R.array.rows);
        int[] columns = getResources().getIntArray(R.array.columns);

        for(int i=0;i<rows.length;i++) {
            final int row = rows[i];
            final int col = columns[i];
            RadioButton button = new RadioButton(this);
            button.setText(row + " x " + col);
            button.setTextSize(18);

            group.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Options_menu.this, row + " rows and " + col + " columns selected.", Toast.LENGTH_SHORT).show();
                    config.setRows(row);
                    config.setColumns(col);
                    save_rows(row);
                    save_col(col);
                }
            });

            //Default button clicked
            if(row==getSavedRows(this) && col==getSavedColumns(this)){
                button.setChecked(true);
                config.setRows(row);
                config.setColumns(col);
            }
        }

    }

    //number of mines option
    @SuppressLint("SetTextI18n")
    private void numMinesOption() {
        RadioGroup group = findViewById(R.id.mines);
        int[] mine = getResources().getIntArray(R.array.num_mines);

        for(int i=0;i<mine.length;i++){
            final int numberMines = mine[i];

            RadioButton button = new RadioButton(this);
            button.setText(numberMines + " mines");
            button.setTextSize(18);

            group.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Options_menu.this, numberMines + " mines selected.",Toast.LENGTH_SHORT).show();
                    config.setMines(numberMines);
                    save_mines(numberMines);
                }
            });

            //Default mine
            if(numberMines==getSavedMines(this)){
                button.setChecked(true);
                config.setMines(numberMines);
            }
        }
    }

    private void save_rows(int row) {
        SharedPreferences prefs=this.getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt("rows",row);
        editor.apply();
    }

    private void save_col(int col) {
        SharedPreferences prefs=this.getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt("cols",col);
        editor.apply();
    }

    private void save_mines(int mine) {
        SharedPreferences prefs=this.getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt("mines",mine);
        editor.apply();
    }

    static public int getSavedRows(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("AppPreferences", MODE_PRIVATE);
        int def_row=context.getResources().getInteger(R.integer.default_rows);
        return  prefs.getInt("Rows", def_row);
    }

    static public int getSavedColumns(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("AppPreferences", MODE_PRIVATE);
        int def_col=context.getResources().getInteger(R.integer.default_columns);
        return prefs.getInt("Columns", def_col);
    }

    static public int getSavedMines(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("AppPreferences", MODE_PRIVATE);
        int def_mines=context.getResources().getInteger(R.integer.default_mines);
        return prefs.getInt("Mines", def_mines);
    }

}