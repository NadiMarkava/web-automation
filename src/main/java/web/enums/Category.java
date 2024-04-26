package web.enums;

public enum Category {

    PHONES("Phones"),
    LAPTOPS("Laptops"),
    MONITORS("Monitors");

    private String name;

    private Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
