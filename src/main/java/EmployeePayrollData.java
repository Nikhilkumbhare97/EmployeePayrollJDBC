import java.time.LocalDate;

public class EmployeePayrollData {
    public String name;
    public String gender;
    public double salary;
    public LocalDate start;

    public EmployeePayrollData(String name, String gender, double salary, LocalDate start) {
        this.name = name;
        this.gender = gender;
        this.salary = salary;
        this.start = start;
    }
}
