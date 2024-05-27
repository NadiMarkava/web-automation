package web.enums;

public enum Category {

    PHONES("Phones"),
    LAPTOPS("Laptops"),
    MONITORS("Monitors");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
