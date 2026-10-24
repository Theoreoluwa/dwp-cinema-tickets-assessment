package uk.gov.dwp.uc.pairtest.exception;

public class AccountNotValidException extends RuntimeException {
    public AccountNotValidException(String message) {
        super(message);
    }
}
