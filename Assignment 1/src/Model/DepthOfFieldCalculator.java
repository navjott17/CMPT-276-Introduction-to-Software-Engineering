package Model;

public class DepthOfFieldCalculator {
    private double COC;
    private Lens lens;
    private double distance;
    private double aperture;

    //default constructor
    public DepthOfFieldCalculator() {
        this.COC=0.029;
        this.distance=0;
        this.aperture=0;
        this.lens=null;
    }

    //non-default constructor
    public DepthOfFieldCalculator(double COC, Lens lens, double distance, double aperture) {
        this.COC = COC;
        this.lens = lens;
        this.distance = distance;
        this.aperture = aperture;
    }

    //getters
    public double getCOC() {
        return COC;
    }

    public Lens getLens() {
        return lens;
    }

    public double getDistance() {
        return distance;
    }

    public double getAperture() {
        return aperture;
    }

    //setters
    public void setLens(Lens lens) {
        this.lens = lens;
    }

    public void setCOC(double COC) {
        this.COC = COC;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setAperture(double aperture) {
        this.aperture = aperture;
    }

    //returns the hyper-focal distance
    public double hyperfocal_distance(){
        return (lens.getFocal_length() * lens.getFocal_length()) / (aperture * COC);
    }

    //returns the near focal point
    public double near_focal_point(){
        return (hyperfocal_distance()*(1000*distance))/(hyperfocal_distance()+((1000*distance) - lens.getFocal_length()));
    }

    //returns the far focal point
    public double far_focal_point(){
        double ff_point;
        if(1000*distance>hyperfocal_distance()){
            ff_point=Double.POSITIVE_INFINITY;
        }
        else {
            ff_point = (hyperfocal_distance() * (1000*distance)) / (hyperfocal_distance() - ((1000*distance) - lens.getFocal_length()));
        }
        return ff_point;
    }

    //returns the depth of field
    public double depth_of_field(){
        double depth;
        if(1000*distance>hyperfocal_distance()){
            depth=Double.POSITIVE_INFINITY;
        }
        else {
            depth = far_focal_point() - near_focal_point();
        }
        return depth;
    }
}
