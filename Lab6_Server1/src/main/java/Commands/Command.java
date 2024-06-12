package Commands;
public abstract class Command implements Executable {
    private final String name;
    private final String description;
    private Object argument;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Object getArgument() {
        return argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    @Override
    public String toString() {
        return  name + ":" + description;
    }
}
