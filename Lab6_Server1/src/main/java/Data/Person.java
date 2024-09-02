package Data;

import java.io.Serial;
import java.io.Serializable;

public class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 67L;
    private String name;
    private float weight;
    private Color eyeColor;
    public Person(String name, float weight, Color eyeColor){
        this.name = name;
        this.weight = weight;
        this.eyeColor = eyeColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public boolean equals(Person person){
        return(person.getName() == this.name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", eyeColor=" + eyeColor +
                '}';
    }
}
