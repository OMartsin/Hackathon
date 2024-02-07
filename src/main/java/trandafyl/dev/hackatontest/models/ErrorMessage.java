package trandafyl.dev.hackatontest.models;



public record ErrorMessage(String message) {

    public static ErrorMessage from(final String message) {
        return new ErrorMessage(message);
    }
}