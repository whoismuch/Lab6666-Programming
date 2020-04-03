package common.command;


public class CommandDescription{
    private String name;
    private String arg;

    public CommandDescription(String name, String arg) {
        this.setName(name);
        this.setArg(arg);
    }

    @Override
    public String toString() {
        return "CommandDescription{" +
                "name='" + name + '\'' +
                ", arg='" + arg + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getArg() {
        return arg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
