package game;

public class Item {

    private String name;
    private String description;
    private boolean portable;

    public Item(String name, String description, boolean portable) {
        this.name = name;
        this.description = description;
        this.portable = portable;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPortable() {
        return portable;
    }
}