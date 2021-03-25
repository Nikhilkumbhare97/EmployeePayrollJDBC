import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class EmployeePayroll {

    private static final String URL = "jdbc:mysql://localhost:3306/payroll_services?useSSL=false";
    private static final String user = "root";
    private static final String password = "Ani1997@";

    private void establishConnection() throws EmployeePayrollException {
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver found!");
        } catch (ClassNotFoundException e) {
            throw new EmployeePayrollException("Cannot find the JDBC Driver!!", EmployeePayrollException.ExceptionType.CANNOT_LOAD_DRIVER);
        }
        listDrivers();
        try {
            System.out.println("\nConnecting to database: " + URL);
            connection = DriverManager.getConnection(URL, user, password);
            System.out.println("Connection established with: " + connection);
        } catch (SQLException e) {
            throw new EmployeePayrollException("Cannot connect to the JDBC Driver!!",
                    EmployeePayrollException.ExceptionType.WRONG_CREDENTIALS);
        }
    }

    private void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = driverList.nextElement();
            System.out.println("Driver: " + driverClass.getClass().getName());
        }
    }

    public static void main(String[] args) throws EmployeePayrollException {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        employeePayroll.establishConnection();
    }
}
