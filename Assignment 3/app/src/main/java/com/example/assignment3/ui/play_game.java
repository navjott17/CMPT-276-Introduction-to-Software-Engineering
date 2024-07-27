package com.example.assignment3.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment3.R;
import com.example.assignment3.model.Configuration;
import com.example.assignment3.model.GameLogic;
import com.example.assignment3.model.Tile;

import org.w3c.dom.Text;

//game page
public class play_game extends AppCompatActivity {

    private Configuration config = Configuration.getInstance();
    Button[][] buttons = new Button[config.getRows()][config.getColumns()];
    GameLogic logic = new GameLogic();
    boolean[][] mine_revealed;
    static boolean track_reset_game=false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        total_times_played();
        populateButtons();
    }

    @SuppressLint("SetTextI18n")
    private void total_times_played() {
        int times_played = get_total_games(this);
        times_played++;

        if(track_reset_game)
            times_played=0;

        config.setTimesPlayed(times_played);
        track_reset_game=false;

        TextView text = findViewById(R.id.games_played);
        text.setText("Total Games Played : " + times_played);

        save_game(times_played);
    }

    @SuppressLint("SetTextI18n")
    private void populateButtons() {
        mine_revealed = new boolean[config.getRows()][config.getColumns()];
        for (int i = 0; i < config.getRows(); i++)
            for (int j = 0; j < config.getColumns(); j++) {
                mine_revealed[i][j] = true;
            }

        TextView totalPumpkins = findViewById(R.id.totalPumpkins);
        final TextView revealedText = findViewById(R.id.revealed);
        final TextView scansText = findViewById(R.id.scans);

        TableLayout table = findViewById(R.id.tiles);
        logic.populateField();

        totalPumpkins.setText("Number of pumpkins: " + config.getMines());
        revealedText.setText("Pumpkins found: 0");
        scansText.setText("Scans: 0");

        for (int row = 0; row < config.getRows(); row++ ) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);

            for (int col = 0; col < config.getColumns(); col++) {
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));
                final int ROW = row;
                final int COL = col;

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Tile currentTile = logic.getTile(ROW, COL);

                        if (currentTile.isHasMine() && !currentTile.isOpened()) {
                            currentTile.setOpened(true);
                            logic.setRevealed(logic.getRevealed() + 1);
                            revealedText.setText("Pumpkins found: " + logic.getRevealed());
                            showMine(ROW, COL);

                            updateScans(ROW, COL);
                        }
                        else if(!currentTile.isHasMine() && !currentTile.isOpened()){
                            currentTile.setOpened(true);
                            logic.setScans(logic.getScans() + 1);
                            scansText.setText("Scans: " + logic.getScans());
                            showHiddenMines(ROW, COL);
                            currentTile.setDislaysText(true);
                            updateScans(ROW, COL);
                        }
                        else if(currentTile.isHasMine() && currentTile.isOpened()){
                            if(mine_revealed[ROW][COL]) {
                                logic.setScans(logic.getScans() + 1);
                                scansText.setText("Scans: " + logic.getScans());
                                showHiddenMines(ROW, COL);
                                currentTile.setDislaysText(true);
                                mine_revealed[ROW][COL]=false;
                                updateScans(ROW, COL);
                            }
                            else
                            {
                                scansText.setText("Scans: " + logic.getScans());
                                showHiddenMines(ROW, COL);
                                currentTile.setDislaysText(true);
                                updateScans(ROW, COL);
                            }
                        }
                        else if(!currentTile.isHasMine() && currentTile.isOpened()) {
                            scansText.setText("Scans: " + logic.getScans());
                            showHiddenMines(ROW, COL);
                            currentTile.setDislaysText(true);
                            updateScans(ROW, COL);
                        }

                        if (logic.isFinished()) {
                            FragmentManager manager = getSupportFragmentManager();
                            MessageFragment dialog = new MessageFragment();
                            dialog.show(manager, "MessageDialog");
                        }
                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;
            }

        }

    }

    private void updateScans(int row, int col) {
        for (int i = 0; i < config.getColumns(); i++) {
            if (logic.getTile(row, i).getDislaysText())
                showHiddenMines(row, i);
        }

        for (int i = 0; i < config.getRows(); i++) {
            if (logic.getTile(i, col).getDislaysText())
                showHiddenMines(i, col);
        }
    }


    @SuppressLint("SetTextI18n")
    private void showHiddenMines(int row, int col) {
        Button button = buttons[row][col];
        Tile currentTile = logic.getTile(row, col);

        int hiddenMines = logic.scan(currentTile);
        button.setText(hiddenMines + "");
    }

    private void showMine(int row, int col) {
        Button button = buttons[row][col];

        lockButtonSizes();

        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pumpkin);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap));
    }

    private void lockButtonSizes() {
        for (int row = 0; row < config.getRows(); row++) {
            for (int col = 0; col < config.getColumns(); col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    public static Intent buildIntent(Context context) {
        return new Intent(context, play_game.class);
    }

    //saves the number of games played.
    private void save_game(int games){
        SharedPreferences prefs = this.getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor =prefs.edit();
        editor.putInt("Total Games Played",games);
        editor.apply();
    }

    //get total games
    static public int get_total_games(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("AppPreferences",MODE_PRIVATE);
        return prefs.getInt("Total Games Played", 0);
    }

    //reset number of games played.
    static public Intent reset_game(Context context) {
        track_reset_game=true;
        return new Intent(context, play_game.class);
    }
}