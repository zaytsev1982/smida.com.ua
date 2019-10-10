package ua.com.smida.exception;

public class CompanyCodeNotFoundException extends RuntimeException {

    public CompanyCodeNotFoundException(String message) {
        super(message);
    }
}
