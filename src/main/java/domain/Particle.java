package domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Particle implements Serializable {
    public BigDecimal mass;
    public BigDecimal charge;
    public boolean fixed;

    public Vector2D force;
    public Vector2D acceleration;
    public Vector2D speed;
    public Vector2D position;

    public Particle(boolean fixed, double x, double y, BigDecimal mass, BigDecimal charge) {
        this.fixed = fixed;

        this.mass = mass;
        this.charge = charge;
        position = new Vector2D(x, y);
        speed = new Vector2D(0, 0);
        acceleration = new Vector2D(0, 0);
        force = new Vector2D(0, 0);
    }

    public void resetForce() {
        force.x = new BigDecimal(0);
        force.y = new BigDecimal(0);
    }

    public void addForce(Particle p) {
        BigDecimal r = position.distance(p.position);
        if(r.compareTo(new BigDecimal(0)) == 0) return;
        BigDecimal F = charge.multiply(p.charge).divide(r.pow(2), Vector2D.mc);

        Vector2D Fv;
        if (0 > F.compareTo(new BigDecimal(0))) Fv = position.substract(p.position);
        else Fv = p.position.substract(position);
        Fv = Fv.normalize().multiply(F);

        p.force.sum(Fv);
        force.sum(Fv.multiply(new BigDecimal(-1)));
    }

    public void move(double seconds) {
        if(fixed) return;
        acceleration = force.multiply(mass.divide(new BigDecimal(1), Vector2D.mc));
        speed.sum(acceleration.multiply(new BigDecimal(seconds)));
        position.sum(speed.multiply(new BigDecimal(seconds)));
    }
}
