package com.example.a2.Model;

import com.example.a2.Model.Lens;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * LensManager class models the information about a group of camera lenses.
 * Stores an Arraylist of type Lens.
 * Is iterable.
 */
public class LensManager implements Iterable<Lens>{
    private List<Lens> lenses = new ArrayList<>();
    public boolean edit_lens = false; //for editing the lens

    //Singleton support
    private static LensManager instance;
    public static LensManager getInstance() {
        if (instance == null)
            instance = new LensManager();

        return instance;
    }
    private LensManager(){
        //Private so no one can instantiate the class
    }

    //Normal Object code
    public void add(Lens lens) {
        lenses.add(lens);
    }

    public Lens get(int i) {
        return lenses.get(i);
    }

    //returns the list lens.
    public List<Lens> getLenses() { return lenses; }


    public int size() { return lenses.size(); }

    @Override
    public Iterator<Lens> iterator() {
        return lenses.iterator();
    }

    @Override
    public String toString() {
        return "LensManager{" +
                "lenses=" + lenses +
                '}';
    }
}
