import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class EmployeePayroll {

    private static final String URL = "jdbc:mysql://localhost:3306/payroll_services?useSSL=false";
    private static final String user = "root";
    private static final String password = "Ani1997@";

    private Connection establishConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver found!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        listDrivers();
        try {
            System.out.println("\nConnecting to database: " + URL);
            connection = DriverManager.getConnection(URL, user, password);
            System.out.println("Connection established with: " + connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<EmployeePayrollData> readData() {
        String sql = "SELECT * FROM employee_payroll;";
        List<EmployeePayrollData> employeePayrollData = new ArrayList<>();
        try (Connection connection = this.establishConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollData.add(new EmployeePayrollData(id, name, salary, startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollData;
    }

    private void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = driverList.nextElement();
            System.out.println("Driver: " + driverClass.getClass().getName());
        }
    }
}
