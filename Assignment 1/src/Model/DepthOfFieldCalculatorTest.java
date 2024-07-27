package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepthOfFieldCalculatorTest {
    @Test
    void Test_getCOC() {
        DepthOfFieldCalculator dof=new DepthOfFieldCalculator();
        dof.setCOC(0.029);
        assertEquals(0.029, dof.getCOC());
    }

    @Test
    void Test_getLens() {
        DepthOfFieldCalculator dof=new DepthOfFieldCalculator();
        Lens lens=new Lens("Sony", 1.6, 100);
        dof.setLens(lens);
        assertEquals("Sony",dof.getLens().getMake());
        assertEquals(1.6,dof.getLens().getMaximum_aperture());
        assertEquals(100,dof.getLens().getFocal_length());
    }

    @Test
    void Test_getDistance() {
        DepthOfFieldCalculator dof=new DepthOfFieldCalculator();
        dof.setDistance(20);
        assertEquals(20,dof.getDistance());
    }

    @Test
    void Test_getAperture() {
        DepthOfFieldCalculator dof=new DepthOfFieldCalculator();
        dof.setAperture(11);
        assertEquals(11,dof.getAperture());
    }

    @Test
    void Test_hyperfocal_distance() {
        //Combination 1
        Lens lens=new Lens("Sony", 1.6, 100);
        DepthOfFieldCalculator dof=new DepthOfFieldCalculator(0.029, lens, 1, 7);
        double hf_dist=dof.hyperfocal_distance()/1000;
        assertEquals(49.26,hf_dist, 0.01);

        //Combination 2
        lens.setMake("Canon");
        lens.setMaximum_aperture(1.4);
        lens.setFocal_length(50);
        dof.setLens(lens);
        dof.setDistance(1.1);
        dof.setAperture(9);
        hf_dist=dof.hyperfocal_distance()/1000;
        assertEquals(9.58,hf_dist,0.01);

        //combination 3
        lens.setMake("Tamron");
        lens.setMaximum_aperture(2.9);
        lens.setFocal_length(180);
        dof.setLens(lens);
        dof.setDistance(70);
        dof.setAperture(20);
        hf_dist=dof.hyperfocal_distance()/1000;
        assertEquals(55.86,hf_dist,0.01);
    }

    @Test
    void Test_near_focal_point() {
        //Combination 1
        Lens lens1=new Lens("Sony", 1.6, 100);
        DepthOfFieldCalculator dof=new DepthOfFieldCalculator(0.029, lens1, 1, 7);
        double nf_point=dof.near_focal_point()/1000;
        assertEquals(0.98,nf_point, 0.01);

        //Combination 2
        Lens lens2=new Lens("Canon",1.4,50);
        dof.setLens(lens2);
        dof.setDistance(1.1);
        dof.setAperture(9);
        nf_point=dof.near_focal_point()/1000;
        assertEquals(0.99,nf_point,0.01);

        //Combination 3
        Lens lens3=new Lens("Tamron",2.9,180);
        dof.setLens(lens3);
        dof.setDistance(70);
        dof.setAperture(20);
        nf_point=dof.near_focal_point()/1000;
        assertEquals(31.11,nf_point,0.01);
    }

    @Test
    void Test_far_focal_point() {
        //Combination 1
        Lens lens1=new Lens("Sony", 1.6, 100);
        DepthOfFieldCalculator dof=new DepthOfFieldCalculator(0.029, lens1, 1, 7);
        double ff_point=dof.far_focal_point()/1000;
        assertEquals(1.02,ff_point, 0.01);

        //Combination 2
        Lens lens2=new Lens("Canon",1.4,50);
        dof.setLens(lens2);
        dof.setDistance(1.1);
        dof.setAperture(9);
        ff_point=dof.far_focal_point()/1000;
        assertEquals(1.24,ff_point,0.01);

        //Combination 3
        Lens lens3=new Lens("Tamron",2.9,180);
        dof.setLens(lens3);
        dof.setDistance(70);
        dof.setAperture(20);
        ff_point=dof.far_focal_point()/1000;
        assertEquals(Double.POSITIVE_INFINITY,ff_point,0.01);
    }

    @Test
    void Test_depth_of_field() {
        //Combination 1
        Lens lens=new Lens("Sony", 1.6, 100);
        DepthOfFieldCalculator dof=new DepthOfFieldCalculator(0.029, lens, 1, 7);
        double depth=dof.depth_of_field()/1000;
        assertEquals(0.04,depth, 0.01);

        //Combination 2
        Lens lens2=new Lens("Canon",1.4,50);
        dof.setLens(lens2);
        dof.setDistance(1.1);
        dof.setAperture(9);
        depth=dof.depth_of_field()/1000;
        assertEquals(0.24,depth,0.01);

        //Combination 3
        Lens lens3=new Lens("Tamron",2.9,180);
        dof.setLens(lens3);
        dof.setDistance(70);
        dof.setAperture(20);
        depth=dof.depth_of_field()/1000;
        assertEquals(Double.POSITIVE_INFINITY,depth,0.01);
    }


}