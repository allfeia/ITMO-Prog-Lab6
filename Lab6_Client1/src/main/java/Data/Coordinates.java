package Data;

import java.io.Serial;
import java.io.Serializable;

public class Coordinates implements Serializable {

    @Serial
    private static final long serialVersionUiD = 78L;

    private float x;
    private float y;

    public Coordinates(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }
    public void setX(long x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }


    public boolean equals(Coordinates c) {
        return c.getX() == this.x && c.getY() == this.y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
