package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class Simulation {

    public void printStatistics() {
    }

    public class Statistics {

        public long average;
        public long total;
        public long numCycles;

        public Statistics(ArrayList<Long> cycles){
            this.total = cycles.stream().mapToLong(Long::longValue).sum();
            this.average = this.total/cycles.size();
            this.numCycles = cycles.size();
        }
        public void print(){
            System.out.println("\n=== " + numCycles + " cycles statistics ======");
            System.out.println("Cycles average: " + average + " ms");
            System.out.println("Cycles total: " + total + " ms");
            System.out.println("===============================");
        }
    }
    public ArrayList<Particle> particles;
    public double height;
    public double width;
    public int numParticles;
    public int numCycles;

    public ArrayList<Long> cycles;
    long startTimer;
    long endTimer;

    public void startTime() {
        startTimer = System.nanoTime();
    }

    public long endTime() {
        endTimer = System.nanoTime();
        long cycleTime = (endTimer - startTimer)/1000000;
        cycles.add(cycleTime);
        return cycleTime;
    }

    public Statistics cycleStatistics(){
        return new Statistics(cycles);
    }


    public Simulation() {
        this.particles = new ArrayList<>();
        this.cycles = new ArrayList<Long>();
    }

    public void createBounds(int numPart) {
        particles = new ArrayList<>();

        //UP bound

        double dx = width / numPart;
        double dy = height / numPart;
        double x = 0;
        double y = 0;

        for (int i = 0; i < numPart + 1; i++) {
            //UP DOWN
            particles.add(new Particle(true, x, 0, new BigDecimal("1e-15"), new BigDecimal("1")));
            particles.add(new Particle(true, x, height, new BigDecimal("1e-15"), new BigDecimal("1")));

            //LEFT RIGHT
            particles.add(new Particle(true, 0, y, new BigDecimal("1e-15"), new BigDecimal("1")));
            particles.add(new Particle(true, width, y, new BigDecimal("1e-15"), new BigDecimal("1")));

            //Update cord
            x += dx;
            y += dy;
        }
    }

    public void createRandomParticles() {
        Random r = new Random();
        for (int i = 0; i < numParticles - particles.size(); i++) {
            double x = Math.abs(r.nextDouble()) % height;
            double y = Math.abs(r.nextDouble()) % width;
            particles.add(new Particle(false, x, y, new BigDecimal("1e-15"), new BigDecimal("1")));
        }
    }

    public void createFileParticles(String filePath) {
        System.out.println("TODO!");
    }

    public void calculateNextPositions(double seconds) {

    }
}
