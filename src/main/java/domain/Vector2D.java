package domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

public class Vector2D implements Serializable {
    static public MathContext mc = new MathContext(10);
    public BigDecimal x;
    public BigDecimal y;

    public Vector2D(double x, double y) {
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
    }

    public Vector2D(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    public void sum(Vector2D v) {
        x = x.add(v.x);
        y = y.add(v.y);
    }

    public Vector2D substract(Vector2D v) {
        return new Vector2D(x.subtract(v.x), y.subtract(v.y));
    }

    public BigDecimal distance(Vector2D v) {
        return v.x.subtract(x).pow(2).add(v.y.subtract(y).pow(2)).sqrt(mc);
    }

    public BigDecimal size() {
        return x.pow(2).add(y.pow(2)).sqrt(mc);
    }

    public Vector2D multiply(BigDecimal skalar) {
        return new Vector2D(x.multiply(skalar), y.multiply(skalar));
    }

    public Vector2D normalize() {
        BigDecimal s = size();
        return new Vector2D(x.divide(s,mc), y.divide(s, mc));
    }
}
