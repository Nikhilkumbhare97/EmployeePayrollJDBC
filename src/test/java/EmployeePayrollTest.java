import org.junit.*;
import org.junit.Test;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class EmployeePayrollTest {

    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_payroll;";
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData(sql);
        System.out.println(employeePayrollDataList.size());
        Assert.assertEquals(10, employeePayrollDataList.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdatedUsingStatement_ShouldReturn1() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_payroll;";
        employeePayroll.readData(sql);
        int salaryUpdated = employeePayroll.updateEmployeeDataUsingStatement();
        Assert.assertEquals(1, salaryUpdated);
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdatedUsingPreparedStatement_ShouldReturn1() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_payroll;";
        employeePayroll.readData(sql);
        int salaryUpdated = employeePayroll.updateEmployeeDataUsingPreparedStatement("Charlie", 3000000);
        Assert.assertEquals(1, salaryUpdated);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_payroll WHERE start BETWEEN CAST('2019-01-01' AS DATE) AND DATE(NOW());";
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData(sql);
        Assert.assertEquals(9, employeePayrollDataList.size());
    }

    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchSumByGender() throws SQLException {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT SUM(salary) FROM employee_payroll WHERE gender='M' GROUP BY gender;";
        String fn = "SUM(salary)";
        double result = employeePayroll.functionsByGender(sql, fn);
        Assert.assertEquals(3500000, result,0.1);

    }


    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchAvgByGender() throws SQLException {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT AVG(salary) FROM employee_payroll WHERE gender='M' GROUP BY gender;";
        String fn = "AVG(salary)";
        double result = employeePayroll.functionsByGender(sql, fn);
        Assert.assertEquals(1750000, result,0.5);

    }

    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchMaxByGender() throws SQLException {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT MAX(salary) FROM employee_payroll WHERE gender='M' GROUP BY gender;";
        String fn = "MAX(salary)";
        double result = employeePayroll.functionsByGender(sql, fn);
        Assert.assertEquals(2500000, result,1);

    }

    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchMinByGender() throws SQLException {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT MIN(salary) FROM employee_payroll WHERE gender='M' GROUP BY gender;";
        String fn = "MIN(salary)";
        double result = employeePayroll.functionsByGender(sql, fn);
        Assert.assertEquals(1000000, result,0.5);

    }

    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchCountByGender() throws SQLException {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT COUNT(*) FROM employee_payroll WHERE gender='M' GROUP BY gender;";
        String fn = "COUNT(*)";
        double result = employeePayroll.functionsByGender(sql, fn);
        Assert.assertEquals(2, result,(0.5));
    }

    @Test
    public void givenNewDataOfEmployee_WhenRetrieved_ShouldReturn1() throws SQLException {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String name = "Saloni";
        String gender = "F";
        double salary = 2000000;
        LocalDate start = LocalDate.parse("2021-03-21");
        int result = employeePayroll.dataInsertionInDatabase(name, gender, salary, start);
        Assert.assertEquals(1, result);
    }

    @Test
    public void givenNewDataOfEmployeeDetails_WhenRetrieved_ShouldReturn1() throws SQLException {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        Integer employee_id = 6;
        double salary = 2000000;
        int result = employeePayroll.dataInsertionInPayTableDatabase(employee_id, salary);
        Assert.assertEquals(1, result);
    }

    @Test
    public void givenNewDataOfEmployeeInPayroll_WhenRetrieved_ShouldReturn1() throws SQLException {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String name = "Reema";
        String gender = "F";
        double salary = 2100000;
        LocalDate start = LocalDate.parse("2021-03-27");
        Integer employee_id = 7;
        int result1 = employeePayroll.dataInsertionInDatabase(name, gender, salary, start);
        int result2 = employeePayroll.dataInsertionInPayTableDatabase(employee_id, salary);
        Assert.assertEquals(2, result1 + result2);
    }

    @Test
    public void deleteDataOfEmployee_WhenRetrieved_ShouldReturn1() throws SQLException {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String name = "Reema";
        int result = employeePayroll.dataDeletionInDatabase(name);
        Assert.assertEquals(1, result);
    }

    @Test
    public void givenMultipleEmployeeDetails_WhenAddedToDB_ShouldMatchEmployeeEntries() {
        EmployeePayrollData[] arrayOfEmployee = {
                new EmployeePayrollData("Gini", "F", 2500000, LocalDate.parse("2021-03-25")),
                new EmployeePayrollData("Zil", "F", 2100000, LocalDate.parse("2021-03-26"))};
        EmployeePayroll employeePayroll = new EmployeePayroll();
        Instant start = Instant.now();
        employeePayroll.addData(Arrays.asList(arrayOfEmployee));
        Instant end = Instant.now();
        System.out.println("Duration Without Thread :" + Duration.between(start, end));
        String sql = "SELECT * FROM employee_payroll;";
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData(sql);
        Assert.assertEquals(8,employeePayrollDataList.size());
    }

    @Test
    public void givenMultipleEmployeeDetails_WhenAddedToDBUsingThreads_ShouldMatchEmployeeEntries() {
        EmployeePayrollData[] arrayOfEmployee = {
                new EmployeePayrollData("Gini", "F", 2500000, LocalDate.parse("2021-03-25")),
                new EmployeePayrollData("Zil", "F", 2100000, LocalDate.parse("2021-03-26"))};
        EmployeePayroll employeePayroll = new EmployeePayroll();
        Instant start = Instant.now();
        employeePayroll.addEmployeeToPayrollWithThreads(Arrays.asList(arrayOfEmployee));
        Instant end = Instant.now();
        System.out.println("Duration With Thread :" + Duration.between(start, end));
        String sql = "SELECT * FROM employee_payroll;";
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData(sql);
        Assert.assertEquals(10,employeePayrollDataList.size());
    }
}
