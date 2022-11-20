package common.exceptions;

public class BusManagementException extends RuntimeException{
    public BusManagementException(String message) {
        super(message);
    }

    public BusManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusManagementException(Throwable cause) {
        super(cause);
    }
}
