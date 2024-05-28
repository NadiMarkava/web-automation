package web.enums;

public enum AlertMessage {

    SUCCESS_SIGN_UP("Sign up successful."),
    USER_EXISTS("This user already exist."),
    FILL_OUT_NAME_PASSWORD("Please fill out Username and Password."),
    USER_DOES_NOT_EXIST("User does not exist."),
    WRONG_PASSWORD("Wrong password."),
    MESSAGE("Thanks for the message!!");

    private final String text;

    AlertMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
