package gui;

import domain.SimDistributed;
import domain.SimParallel;
import domain.SimSequential;
import domain.Simulation;
import gui.DrawingPanel;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

public class ControlsFrame {
    private JComboBox typeOfProgram;
    private JSpinner numCycles;
    private JSpinner numParticles;
    private JButton startButton;
    public JPanel mainPanel;
    private JSpinner windowWidth;
    private JSpinner windowHeight;
    private JFrame drawingFrame;


    public ControlsFrame() {
        windowHeight.setValue(600);
        windowWidth.setValue(800);
        numCycles.setValue(100);
        numParticles.setValue(2000);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String executionType = (String) typeOfProgram.getSelectedItem();
                int numCyclesValue = (int) numCycles.getValue();
                int numParticlesValue = (int) numParticles.getValue();
                int windowHeightValue = (int) windowHeight.getValue();
                int windowWidthValue = (int) windowWidth.getValue();

                Simulation sim = null;
                switch (Objects.requireNonNull(executionType)) {
                    case "sequential" -> sim = new SimSequential();
                    case "parallel" -> sim = new SimParallel();
                    case "distributed" -> sim = new SimDistributed();
                }

                sim.numParticles = numParticlesValue;
                sim.numCycles = numCyclesValue;
                sim.height = 10e-5;
                sim.width = 10e-5;

                sim.createBounds(10);
                sim.createRandomParticles();

                drawingFrame = new JFrame();
                drawingFrame.setSize(windowWidthValue, windowHeightValue);
                drawingFrame.add(new DrawingPanel(sim, numCyclesValue, windowWidthValue, windowHeightValue));
                drawingFrame.setVisible(true);
            }
        });
    }
}
