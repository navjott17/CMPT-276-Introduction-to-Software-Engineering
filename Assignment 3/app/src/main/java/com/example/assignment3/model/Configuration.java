/**
 * Configuration class stores user choices in the options page
 * Contains rows, columns, mines and times played
 * Is a Singleton class
 */
package com.example.assignment3.model;

//singleton class for setting mines, rows and columns
public class Configuration {
    private int rows=4;
    private int columns=6;
    private int mines=10;
    private int timesPlayed = 0;

    //Singleton support
    private static Configuration instance;
    public static Configuration getInstance() {
        if (instance == null)
            instance = new Configuration();

        return instance;
    }
    private Configuration(){
        //Private so no one can instantiate the class
    }

    //Normal object code

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public void eraseTimesPlayed(){
        this.timesPlayed = 0;
    }
}

