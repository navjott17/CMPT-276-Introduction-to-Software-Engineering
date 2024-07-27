package com.example.a2.Model;

/**
 * DoFCalculator class calculates several parameters regarding the depth of field.
 * Calculates hyperfocal distance, near focal point, far focal point, and depth of field.
 * Data includes lens, distance, aperture, circleOfConfusion
 */
public class DoFCalculator {
    private Lens lens;
    private double distance;
    private double aperture;
    private double circleOfConfusion;

    //Constructors
    public DoFCalculator(Lens lens, double distance, double aperture, double circleOfConfusion) {
        this.lens = lens;
        this.distance = distance;
        this.aperture = aperture;
        this.circleOfConfusion = circleOfConfusion;
    }

    //Getters and Setters
    public Lens getLens() {
        return lens;
    }

    public void setLens(Lens lens) {
        this.lens = lens;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public double getAperture() {
        return aperture;
    }

    public void setAperture(float aperture) {
        this.aperture = aperture;
    }

    public double getCircleOfConfusion() {
        return circleOfConfusion;
    }

    public void setCircleOfConfusion(float circleOfConfusion) {
        this.circleOfConfusion = circleOfConfusion;
    }

    //Other member functions
    public double hyperfocalDistance() {
        return Math.pow(lens.getFocalLength(), 2)
                / (aperture * circleOfConfusion);
    }

    public double nearFocalPoint() {
        return (hyperfocalDistance() * distance)
                / (hyperfocalDistance() + (distance - lens.getFocalLength()));
    }

    public double farFocalPoint() {
        if (distance > hyperfocalDistance())
            return Double.POSITIVE_INFINITY;

        else
            return (hyperfocalDistance() * distance)
                    / (hyperfocalDistance() - (distance - lens.getFocalLength()));
    }

    public double depthOfField() {
        return (farFocalPoint() - nearFocalPoint());
    }

    @Override
    public String toString() {
        return "DoFCalculator{" +
                "lens=" + lens +
                ", distance=" + distance +
                ", aperture=" + aperture +
                ", circleOfConfusion=" + circleOfConfusion +
                '}';
    }
}
