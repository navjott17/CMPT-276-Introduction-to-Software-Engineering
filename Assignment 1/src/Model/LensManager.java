package Model;

import java.util.ArrayList;

public class LensManager {
    private ArrayList<Lens> lens=new ArrayList<>();

    //adds the new Lens object l to the arraylist lens.
    public void add(Lens l){
        lens.add(l);
    }

    //retrieves a specific lens by its index.
    public Lens retrieve(int index){
        return lens.get(index);
    }

    //returns the arraylist lens.
    public ArrayList<Lens> getLens() {
        return lens;
    }

    //prints all the lens in the arraylist lens.
    public void iterator(){
        System.out.println("Lenses to pick from: ");
        int size=lens.size();
        for(int i=0;i<size;i++){
            String s = retrieve(i).getMake();
            double aperture=retrieve(i).getMaximum_aperture();
            int fLength=retrieve(i).getFocal_length();
            System.out.println(i + ". " + s + "  " + fLength + "mm  F" + aperture);
        }
        System.out.println("(-1 to exit)");
        System.out.println(": ");
    }

}
