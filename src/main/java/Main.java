import gui.ControlsFrame;
import io.ray.api.Ray;

import javax.swing.JFrame;


public class Main{
    public static void main(String[] args) {
        System.setProperty("ray.address", "192.168.43.69:6379");
        System.setProperty("ray.local-mode", "true");
        System.setProperty("redis.password", "5241590000000000");
        System.setProperty("ray.job.code-search-path", "/home/urosjarc/vcs/electric-sim/build/libs/electric-sim.jar");
        Ray.init();
        JFrame controls = new JFrame("ControlsGrame");
        controls.setSize(800, 600);
        controls.add(new ControlsFrame().mainPanel);
        controls.setVisible(true);
    }
}

