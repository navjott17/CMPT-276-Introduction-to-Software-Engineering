package Text_UI;
import Model.Lens;
import Model.LensManager;
import Model.DepthOfFieldCalculator;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Sample (incomplete) text UI to interact with the user.
 * You may change any part of this!
 */
public class CameraTextUI {
    private static final double COC = 0.029;    // "Circle of Confusion" for a "Full Frame" camera
    private LensManager manager;
    private Scanner in = new Scanner(System.in);// Read from keyboard


    public CameraTextUI(LensManager manager) {
        // Accept and store a reference to the lens manager (the model)
        // (this design is called "dependency injection")
        this.manager = manager;

        // Populate lenses (Make, max aperture (smallest supported F number), focal length [mm]):
        manager.add(new Lens("Canon", 1.8, 50));
        manager.add(new Lens("Tamron", 2.8, 90));
        manager.add(new Lens("Sigma", 2.8, 200));
        manager.add(new Lens("Nikon", 4, 200));
    }

    public void show() {
        while(true) {
            manager.iterator();
            int size=manager.getLens().size();
            int choice=in.nextInt();
            while (choice < -1 || choice >= size) {
                System.out.println("ERROR: Invalid lens index.\n");
                manager.iterator();
                choice = in.nextInt();
            }
            if (choice == -1)
                break;
            else {
                System.out.println("Aperture [the F number]: ");
                double temp_aperture = in.nextDouble();
                double max_aperture = manager.retrieve(choice).getMaximum_aperture();
                if (temp_aperture < max_aperture) {
                    System.out.println("ERROR: This aperture is not possible with this lens.\n");
                    continue;
                }
                System.out.println("Distance to subject[m]: ");
                double temp_dist = in.nextDouble();
                DepthOfFieldCalculator dof = new DepthOfFieldCalculator(COC,manager.retrieve(choice),temp_dist,temp_aperture);

                System.out.println("\t"+"In focus: "+formatM(dof.near_focal_point()/1000)+"m to "+
                        formatM(dof.far_focal_point()/1000)+"m [DOF = "+formatM(dof.depth_of_field()/1000)+"m]");
                System.out.println("\tHyperfocal point: "+formatM(dof.hyperfocal_distance()/1000)+"m\n");
            }
        }
    }

    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }
}
