package Forms;

import Data.Color;
import Data.Person;

public class PersonBuilder {
    private String name;
    private float weight;
    private Color eyeColor;

    public PersonBuilder(){}

    public void withOperatorsName(String name) {
        if (name.isBlank()) throw new NullPointerException("call poor operator...");
        this.name = name;
    }

    public void withWeight(float weight) {
        if (weight <= 0) throw new IllegalArgumentException("ha-ha operator is a ghost?");
        this.weight = weight;
    }

    public void withEyeColor(Color eyeColor) {
        if (eyeColor == null) throw new NullPointerException("Is he blind?");
        this.eyeColor = eyeColor;
    }

    public Person build(){
        return new Person(name, weight, eyeColor);
    }

}
