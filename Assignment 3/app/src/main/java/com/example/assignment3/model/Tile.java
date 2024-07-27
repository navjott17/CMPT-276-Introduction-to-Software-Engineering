/**
 * Tile class models a single tile used in the game
 * Contains row, column, hasMine, isOpened, displaysText
 */
package com.example.assignment3.model;

//build the tiles to play the game
public class Tile {
    private int row;
    private int column;
    private boolean hasMine; //Is true when the tile contains a mine
    private boolean isOpened; //Is true when the tile is opened by user
    private boolean dislaysText = false;

    public Tile() {
        this.hasMine = false;
        this.isOpened = false;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isHasMine() {
        return hasMine;
    }

    public void setHasMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        this.isOpened = opened;
    }

    public boolean getDislaysText() {
        return dislaysText;
    }

    public void setDislaysText(boolean dislaysText) {
        this.dislaysText = dislaysText;
    }
}
