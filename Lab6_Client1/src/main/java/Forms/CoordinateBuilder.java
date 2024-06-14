package Forms;

import Data.Coordinates;

public class CoordinateBuilder {
    private float x;
    private float y;

    public void withX(Float x){
        this.x = x;
    }

    public void withY(Float y){
        if (y > 347) throw new IllegalArgumentException("Y can't be more then 347");
        this.y = y;
    }

    public Coordinates build(){
        return new Coordinates(x, y);
    }
}
