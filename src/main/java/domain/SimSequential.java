package domain;

public class SimSequential extends Simulation {

    @Override
    public void calculateNextPositions(double seconds){
        //Reset all particle forces
        for (Particle p : particles) p.resetForce();

        //Calculate forces!
        for (int i = 0; i < particles.size()-1; i++) {
            for (int j = i+1; j < particles.size(); j++) {
                Particle p1 =particles.get(i);
                Particle p2 =particles.get(j);
                p1.addForce(p2);
            }
        }

        //Move particles!
        for (Particle p : particles) p.move(seconds);
    }
}
