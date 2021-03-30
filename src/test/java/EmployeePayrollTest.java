import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class EmployeePayrollTest {

    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_payroll;";
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData(sql);
        System.out.println(employeePayrollDataList.size());
        Assertions.assertEquals(6, employeePayrollDataList.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdatedUsingStatement_ShouldReturn1() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_payroll;";
        employeePayroll.readData(sql);
        int salaryUpdated = employeePayroll.updateEmployeeDataUsingStatement();
        Assertions.assertEquals(1, salaryUpdated);
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdatedUsingPreparedStatement_ShouldReturn1() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_payroll;";
        employeePayroll.readData(sql);
        int salaryUpdated = employeePayroll.updateEmployeeDataUsingPreparedStatement("Charlie", 3000000);
        Assertions.assertEquals(1, salaryUpdated);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_payroll WHERE start BETWEEN CAST('2019-01-01' AS DATE) AND DATE(NOW());";
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData(sql);
        Assertions.assertEquals(3, employeePayrollDataList.size());
    }

    @Test
    public void givenEmployeePayrollDB_WhenRetrived_ShouldMatchSumByGender() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT SUM(salary) FROM employee_payroll WHERE gender='M' GROUP BY gender;";
        String fn = "SUM(salary)";
        double result = employeePayroll.functionsByGender(sql, fn);
        Assertions.assertEquals(3500000, result);

    }


    @Test
    public void givenEmployeePayrollDB_WhenRetrived_ShouldMatchAvgByGender() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT AVG(salary) FROM employee_payroll WHERE gender='M' GROUP BY gender;";
        String fn = "AVG(salary)";
        double result = employeePayroll.functionsByGender(sql, fn);
        Assertions.assertEquals(1750000, result);

    }

    @Test
    public void givenEmployeePayrollDB_WhenRetrived_ShouldMatchMaxByGender() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT MAX(salary) FROM employee_payroll WHERE gender='M' GROUP BY gender;";
        String fn = "MAX(salary)";
        double result = employeePayroll.functionsByGender(sql, fn);
        Assertions.assertEquals(2500000, result);

    }

    @Test
    public void givenEmployeePayrollDB_WhenRetrived_ShouldMatchMinByGender() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT MIN(salary) FROM employee_payroll WHERE gender='M' GROUP BY gender;";
        String fn = "MIN(salary)";
        double result = employeePayroll.functionsByGender(sql, fn);
        Assertions.assertEquals(1000000, result);

    }

    @Test
    public void givenEmployeePayrollDB_WhenRetrived_ShouldMatchCountByGender() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT COUNT(*) FROM employee_payroll WHERE gender='M' GROUP BY gender;";
        String fn = "COUNT(*)";
        double result = employeePayroll.functionsByGender(sql, fn);
        Assertions.assertEquals(2, result);

    }

//    @Test
//    public void givenNewDataOfEmployee_WhenRetrived_ShouldReturn1() throws SQLException {
//        EmployeePayroll employeePayroll = new EmployeePayroll();
//        String name="Saloni"; String gender="F"; double salary=2000000; String start="2021-03-21";
//        int result = employeePayroll.dataInsertionInDatabase(name, gender, salary, start);
//        Assertions.assertEquals(1, result);
//    }
//
//    @Test
//    public void givenNewDataOfEmployeeDetails_WhenRetrived_ShouldReturn1() throws SQLException {
//        EmployeePayroll employeePayroll = new EmployeePayroll();
//        Integer employee_id=6; double salary=2000000;
//        int result = employeePayroll.dataInsertionInPayTableDatabase(employee_id, salary);
//        Assertions.assertEquals(1, result);
//    }
//
//    @Test
//    public void givenNewDataOfEmployeeInPayroll_WhenRetrived_ShouldReturn1() throws SQLException {
//        EmployeePayroll employeePayroll = new EmployeePayroll();
//        String name="Reshma"; String gender="F"; double salary=2100000; String start="2021-03-27";
//        Integer employee_id=7;
//        int result1 = employeePayroll.dataInsertionInDatabase(name, gender, salary, start);
//        int result2 = employeePayroll.dataInsertionInPayTableDatabase(employee_id, salary);
//        Assertions.assertEquals(2, result1+result2);
//    }

    @Test
    public void deleteDataOfEmployee_WhenRetrived_ShouldReturn1() throws SQLException {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String name="Reshma";
        int result = employeePayroll.dataDeletionInDatabase(name);
        Assertions.assertEquals(1, result);
    }
}
