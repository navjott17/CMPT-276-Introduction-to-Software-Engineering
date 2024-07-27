import Model.LensManager;
import Text_UI.CameraTextUI;

/**
 * Launch application
 */
public class Main {
    public static void main(String args[]) {
        LensManager manager = new LensManager();
        CameraTextUI ui = new CameraTextUI(manager);
        ui.show();
    }
}
