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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        listDrivers();
        try {
            connection = DriverManager.getConnection(URL, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<EmployeePayrollData> readData(String sql) {
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

    int updateEmployeeDataUsingStatement() {
        String sql = String.format("update employee_payroll set salary = %f where name = '%s';", 2500000.0, "Mark");
        try (Connection connection = this.establishConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    int updateEmployeeDataUsingPreparedStatement(String name, double salary) {
        String sql = String.format("update employee_payroll set salary = %f where name = '%s';", salary, name);
        try (Connection connection = this.establishConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            return prepareStatement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            driverList.nextElement();
        }
    }
}
