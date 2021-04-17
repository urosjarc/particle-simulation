package domain;

import java.util.ArrayList;
import java.util.List;

public class SimParallel extends Simulation {

    public SimParallel() {
        calcTreads = new ArrayList<>();
    }

    public ArrayList<Thread> calcTreads;

    public class Calculation implements Runnable {

        public Particle particle;
        public List<Particle> particles;
        public double timeDelta;

        public Calculation(Particle p, List<Particle> particles, double timeDelta) {
            this.particle = p;
            this.particles = particles;
            this.timeDelta = timeDelta;
        }

        public void run() {
            for (Particle p : particles) {
                particle.addForce(p);
            }
            particle.move(timeDelta);
        }
    }


    @Override
    public void calculateNextPositions(double seconds) {
        //Reset before starting threads
        for (Particle p : particles) p.resetForce();

        for (int i = 0; i < particles.size() - 1; i++) {
            Calculation calc = new Calculation(
                    particles.get(i),
                    particles.subList(i + 1, particles.size()),
                    seconds
            );
            Thread calcThread = new Thread(calc);
            calcThread.start();
            calcTreads.add(calcThread);
        }

        for(Thread thread: calcTreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        calcTreads.clear();

    }
}
