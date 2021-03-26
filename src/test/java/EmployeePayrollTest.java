import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeePayrollTest {

    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData();
        System.out.println(employeePayrollDataList.size());
        Assertions.assertEquals(3, employeePayrollDataList.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldReturn1() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        employeePayroll.readData();
        int salaryUpdated= employeePayroll.updateEmployeeDataUsingStatement();
        Assertions.assertEquals(1, salaryUpdated);
    }
}
