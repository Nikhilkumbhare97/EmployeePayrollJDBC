import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeePayrollTest {

    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData();
        Assertions.assertEquals(3, employeePayrollDataList.size());
    }
}
