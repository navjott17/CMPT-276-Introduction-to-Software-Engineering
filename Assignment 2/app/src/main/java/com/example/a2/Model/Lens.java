package com.example.a2.Model;

/**
* Lens class models the information about a single  camera lens.
* Data includes lens make, maximum aperture and focal length.
*/
public class Lens {
    private String make;
    private double maxAperture;
    private int focalLength;

    //Constructors
    public Lens(String make, double maxAperture, int focalLength) {
        this.make = make;
        this.maxAperture = maxAperture;
        this.focalLength = focalLength;
    }

    //Getters and Setters
    public String getMake() {
        return make;
    }

    public double getMaxAperture() {
        return maxAperture;
    }

    public int getFocalLength() {
        return focalLength;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setMaxAperture(double maxAperture) {
        this.maxAperture = maxAperture;
    }

    public void setFocalLength(int focalLength) {
        this.focalLength = focalLength;
    }

    //Other member functions
    @Override
    public String toString() {
        return "Lens{" +
                "make='" + make + '\'' +
                ", maxAperture=" + maxAperture +
                ", focalLength=" + focalLength +
                '}';
    }
}
