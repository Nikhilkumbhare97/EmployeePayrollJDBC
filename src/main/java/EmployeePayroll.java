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
                String gender = resultSet.getString("gender");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollData.add(new EmployeePayrollData(id, name, gender, salary, startDate));
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

    public double functionsByGender(String sql, String fn) {
        establishConnection();
        ResultSet resultSet;
        double result = 0;
        try {
            resultSet = establishConnection().createStatement().executeQuery(sql);
            resultSet.next();
            result = resultSet.getDouble(fn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

//    public int dataInsertionInDatabase(String name,String gender, double salary,String start) throws SQLException {
//        try{
//            Connection connection=this.establishConnection();
//            connection.setAutoCommit(false);
//            PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO employee_payroll(name,gender,salary,start) values(?,?,?,?); ");
//            preparedStatement.setNString(1,name);
//            preparedStatement.setNString(2,gender);
//            preparedStatement.setDouble(3,salary);
//            preparedStatement.setDate(4, Date.valueOf(start));
//            preparedStatement.executeUpdate();
//            connection.commit();
//        }catch (SQLException throwables) {
//            throwables.printStackTrace();
//            establishConnection().rollback();
//        }finally {
//            establishConnection().close();
//        }
//        return 1;
//    }
//
//    public int dataInsertionInPayTableDatabase(Integer employee_id,double salary) throws SQLException {
//        try{
//            Connection connection=this.establishConnection();
//            connection.setAutoCommit(false);
//            PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO employee_details(employee_id,salary) values(?,?); ");
//            preparedStatement.setInt(1,employee_id);
//            preparedStatement.setDouble(2,salary);
//            preparedStatement.executeUpdate();
//            connection.commit();
//        }catch (SQLException throwables) {
//            throwables.printStackTrace();
//            establishConnection().rollback();
//        }finally {
//            establishConnection().close();
//        }
//        return 1;
//    }

    public int dataDeletionInDatabase(String name) throws SQLException {
        try{
            Connection connection=this.establishConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement=connection.prepareStatement("DELETE FROM employee_payroll where name=?; ");
            preparedStatement.setNString(1,name);
            preparedStatement.executeUpdate();
            connection.commit();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            establishConnection().rollback();
        }finally {
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
}
