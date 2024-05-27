package web.enums;

public enum NavBarMenuOption {

    HOME("Home "),
    CONTACT("Contact"),
    ABOUT_US("About us"),
    CART("Cart"),
    LOG_IN("Log in"),
    LOG_OUT("Log out"),
    WELCOME("Welcome test"),
    SIGN_UP("Sign up");

    private final String name;

    NavBarMenuOption(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
