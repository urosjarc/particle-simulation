package domain;

import io.ray.api.ActorHandle;
import io.ray.api.ObjectRef;
import io.ray.api.Ray;

import java.io.Serializable;
import java.util.ArrayList;

public class SimDistributed extends Simulation implements Serializable {

    public SimDistributed(){
    }

    class CalculationArgs implements Serializable {

        public Particle p;
        public ArrayList<Particle> particles;
        public double timeDelta;

        public CalculationArgs(Particle p, ArrayList<Particle> particles, double timeDelta) {
            this.p = p;
            this.particles = particles;
            this.timeDelta = timeDelta;
        }
    }

    public static class Calculation {

        Particle p;

        public void calculate(CalculationArgs args) {
            for (Particle part : args.particles) {
                if(part != args.p)
                    args.p.addForce(part);
            }
            args.p.move(args.timeDelta);
            this.p = args.p;
        }

        public Particle getResult(){
            return this.p;
        }
    }


    @Override
    public void calculateNextPositions(double seconds) {
        //Reset before starting threads
        for (Particle p : particles) p.resetForce();

        ArrayList<ActorHandle<Calculation>> remotesTasks = new ArrayList<>();
        for (int i = 0; i < particles.size() - 1; i++) {
            ActorHandle<Calculation> calc = Ray.actor(Calculation::new).remote();
            remotesTasks.add(calc);
            CalculationArgs args = new CalculationArgs(particles.get(i), particles, seconds);
            calc.task(Calculation::calculate, args).remote();
        }

        for (int i = 0; i < remotesTasks.size() - 1; i++) {
            ActorHandle<Calculation> remoteTask = remotesTasks.get(i);
            ObjectRef<Particle> particle = remoteTask.task(Calculation::getResult).remote();
            particles.set(i, particle.get());
        }

    }
}
