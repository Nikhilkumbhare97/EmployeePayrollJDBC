import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class EmployeePayroll {

    private Connection establishConnection() throws SQLException {
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        listDrivers();
        String URL = "jdbc:mysql://localhost:3306/payroll_services?useSSL=false";
        String user = "root";
        String password = "Ani1997@";
        connection = DriverManager.getConnection(URL, user, password);
        System.out.println(connection);
        return connection;
    }

    public List<EmployeePayrollData> readData(String sql) {
        List<EmployeePayrollData> employeePayrollData = new ArrayList<>();
        try (Connection connection = this.establishConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollData.add(new EmployeePayrollData(name, gender, salary, startDate));
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

    public double functionsByGender(String sql, String fn) throws SQLException {
        establishConnection();
        ResultSet resultSet;
        double result = 0;
        try {
            resultSet = establishConnection().createStatement().executeQuery(sql);
            resultSet.next();
            result = resultSet.getDouble(fn);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    public int dataInsertionInDatabase(String name, String gender, double salary, LocalDate start) throws SQLException {
        try {
            Connection connection = this.establishConnection();
            System.out.println(connection);
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employee_payroll(name,gender,salary,start) values(?,?,?,?); ");
            preparedStatement.setNString(1, name);
            preparedStatement.setNString(2, gender);
            preparedStatement.setDouble(3, salary);
            preparedStatement.setDate(4, Date.valueOf(start));
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            establishConnection().rollback();
        } finally {
            establishConnection().close();
        }
        return 1;
    }

    public int dataInsertionInPayTableDatabase(Integer employee_id, double salary) throws SQLException {

        try {
            Connection connection = this.establishConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employee_details(employee_id,salary) values(?,?); ");
            preparedStatement.setInt(1, employee_id);
            preparedStatement.setDouble(2, salary);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            establishConnection().rollback();
        } finally {
            establishConnection().close();
        }
        return 1;
    }

    public int dataDeletionInDatabase(String name) throws SQLException {
        try {
            Connection connection = this.establishConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee_payroll where name=?; ");
            preparedStatement.setNString(1, name);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            establishConnection().rollback();
        } finally {
            establishConnection().close();
        }
        return 1;
    }

    private void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            driverList.nextElement();
        }
    }

    public void addData(List<EmployeePayrollData> asList) {
        asList.forEach(contactData -> {
            System.out.println("Employee being added : " + contactData.name);
            try {
                this.dataInsertionInDatabase(contactData.name, contactData.gender, contactData.salary, contactData.start);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            System.out.println("Employee added : " + contactData.name);
        });
    }

    public void addEmployeeToPayrollWithThreads(List<EmployeePayrollData> employeePayrollDataList) {
        Map<Integer, Boolean> employeeAdditionStatus = new HashMap<>();
        employeePayrollDataList.forEach(contactData -> {
            Runnable task = () -> {
                employeeAdditionStatus.put(contactData.hashCode(), false);
                System.out.println("Employee being added : " + Thread.currentThread().getName());
                try {
                    this.dataInsertionInDatabase(contactData.name, contactData.gender, contactData.salary, contactData.start);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
                employeeAdditionStatus.put(contactData.hashCode(), true);
                System.out.println("Employee added : " + Thread.currentThread().getName());
            };
            Thread thread = new Thread(task, String.valueOf(contactData.name));
            thread.start();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        while (employeeAdditionStatus.containsValue(false)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}