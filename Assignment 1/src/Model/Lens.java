package Model;

public class Lens {
    private String make;
    private double maximum_aperture;
    private int focal_length;

    //default constructor
    public Lens() {
    }

    //non-default constructor
    public Lens(String make, double maximum_aperture, int focal_length) {
        this.make = make;
        this.maximum_aperture = maximum_aperture;
        this.focal_length = focal_length;
    }

    //getters
    public String getMake() {
        return make;
    }

    public double getMaximum_aperture() {
        return maximum_aperture;
    }

    public int getFocal_length() {
        return focal_length;
    }

    //setters
    public void setMake(String make) {
        this.make = make;
    }

    public void setMaximum_aperture(double maximum_aperture) {
        this.maximum_aperture = maximum_aperture;
    }

    public void setFocal_length(int focal_length) {
        this.focal_length = focal_length;
    }

}
