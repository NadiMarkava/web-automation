package web.enums;

public enum Navbar {

    HOME("Home "),
    CONTACT("Contact"),
    ABOUT_US("About us"),
    CART("Cart"),
    LOG_IN("Log in"),
    SIGN_UP("Sign up");

    private final String name;

    Navbar(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
