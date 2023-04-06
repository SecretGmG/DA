package bouncingBalls;

import javax.swing.*;

/**
 * Main program that displays a bouncing balls simulation.
 */
public class BouncingBalls {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Bouncing Balls");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int w = 500;
        int h = 500;
        int n = 2000;
        float r = 4.f;
        float v = 0.2f;

        System.out.printf(
                "width, height: %d, %d \n" +
                "number of particles %d \n" +
                "radius of particles %.3f \n"
                ,w,h,n,r);

        // Initialize the simulation and add it to the main frame.
        BouncingBallsSimulation simulation = new MyBouncingBallsSimulation(w, h, n, r, v, 4);
        frame.add(simulation);
        simulation.setVisible(true);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        // Start the simulation.
        simulation.start();
    }
}
