/**
 * Game Logic class provides logic to the game
 * Contains field, scans, revealed
 */
package com.example.assignment3.model;

import java.util.ArrayList;
import java.util.List;

//game logic i.e, how the game works
public class GameLogic {
    private List<Tile> field = new ArrayList(); //sets up the field of tiles
    private int scans = 0;
    private int revealed = 0;

    Configuration config = Configuration.getInstance();

    private void chooseMineTile() {
        int row = (int)(Math.random()*config.getRows());
        int column = (int)(Math.random()*config.getColumns());
        if (getTile(row, column).isHasMine() == true) {
            chooseMineTile();
        }
        getTile(row, column).setHasMine(true);
    }

    //Populates the field with tiles
    public void populateField() {
        int rows = config.getRows();
        int columns = config.getColumns();
        int mines = config.getMines();

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                Tile tile = new Tile();
                tile.setRow(i);
                tile.setColumn(j);
                field.add(tile);
            }
        }

        for (int i = 0; i < mines; i++) {
            chooseMineTile();
        }
    }

    public Tile getTile(int row, int column) {
        return field.get(row*config.getColumns() + column);
    }

    public int scan(Tile tile) {
        int hiddenMines = 0;
        for(int i = 0; i < config.getRows(); i++) {
            if(getTile(i, tile.getColumn()).isHasMine() && !getTile(i, tile.getColumn()).isOpened())
                hiddenMines++;
        }

        for(int i = 0; i < config.getColumns(); i++) {
            if (getTile(tile.getRow(), i).isHasMine() && !getTile(tile.getRow(), i).isOpened())
                hiddenMines++;
        }

        if(tile.isHasMine() && !tile.isOpened())
            hiddenMines--;

        return hiddenMines;
    }


    public int getScans() {
        return scans;
    }

    public void setScans(int scans) {
        this.scans = scans;
    }

    public int getRevealed() {
        return revealed;
    }

    public void setRevealed(int revealed) {
        this.revealed = revealed;
    }

    public boolean isFinished() {
        boolean finished = true;
        for (Tile t : field) {
            if (!t.isOpened())
                finished = false;
            if(getRevealed()==config.getMines()) {
                return true;
            }
        }

        return finished;
    }
}
