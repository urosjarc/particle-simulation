package gui;

import domain.Particle;
import domain.Simulation;
import domain.Vector2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingPanel extends JPanel implements ActionListener {

    public Simulation simulation;
    public int numCycles;
    public int cycle = 0;
    public Vector2D scale;

    public final int DELAY = 17;
    public Timer timer;

    public DrawingPanel(Simulation simulation, int numCycles, int width, int height) {
        scale = new Vector2D(width/simulation.width, height/simulation.height);
        this.numCycles = numCycles;
        this.simulation = simulation;
        startTimer();
    }

    private void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.black);

        if(cycle == 0) System.out.println("Simulation started...");

        simulation.startTime();
        simulation.calculateNextPositions(DELAY/1000.0);
        long cycleTime = simulation.endTime();
        cycle++;
        System.out.println(cycle + ". cycle: " + cycleTime + " ms");
        for (Particle p : simulation.particles) {
            int x = p.position.x.multiply(scale.x).toBigInteger().intValue();
            int y = p.position.y.multiply(scale.y).toBigInteger().intValue();
            g2d.fillOval(x-5, y-5, 10, 10);
        }

        if(cycle == simulation.numCycles) {
            Simulation.Statistics stat = simulation.cycleStatistics();
            stat.print();
            this.setVisible(false);
        }
    }

    public void startTimer(){
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
