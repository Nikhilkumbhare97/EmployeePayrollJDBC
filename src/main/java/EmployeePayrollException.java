public class EmployeePayrollException extends Exception {

    public enum ExceptionType {
        WRONG_CREDENTIALS, CANNOT_LOAD_DRIVER
    }

    ExceptionType type;

    public EmployeePayrollException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
