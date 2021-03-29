import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeePayrollTest {

    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_payroll;";
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData(sql);
        System.out.println(employeePayrollDataList.size());
        Assertions.assertEquals(3, employeePayrollDataList.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdatedUsingStatement_ShouldReturn1() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_payroll;";
        employeePayroll.readData(sql);
        int salaryUpdated= employeePayroll.updateEmployeeDataUsingStatement();
        Assertions.assertEquals(1, salaryUpdated);
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdatedUsingPreparedStatement_ShouldReturn1() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_payroll;";
        employeePayroll.readData(sql);
        int salaryUpdated= employeePayroll.updateEmployeeDataUsingPreparedStatement("Charlie", 3000000);
        Assertions.assertEquals(1, salaryUpdated);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_payroll WHERE start BETWEEN CAST('2019-01-01' AS DATE) AND DATE(NOW());";
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData(sql);
        Assertions.assertEquals(2, employeePayrollDataList.size());
    }
}
