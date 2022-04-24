package springboot.k8s.demo.service;

import org.springframework.stereotype.Service;
import springboot.k8s.demo.exception.ResourceNotFoundException;
import springboot.k8s.demo.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private List<Employee> employees = new ArrayList<Employee>() {{
        add(new Employee(1, "Daniel", "d@test.com", "9876543210", "Acct" ));
        add(new Employee(2, "Michael", "m@test.com", "9876543211", "IT" ));
        add(new Employee(3, "Will", "w@test.com", "9876543212", "Acct" ));
        add(new Employee(4, "Matthew", "m@test.com", "9876543213", "PMO" ));
        add(new Employee(5, "Smith", "s@test.com", "9876543214", "IT" ));
    }};

    public List<Employee> findAll() {
        return employees;
    }

    public Employee findById(Integer employeeId) {
        Employee employee = employees.stream()
                .filter(v -> v.getId() == employeeId)
                .findFirst().orElse(null);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found for this id :: " + employeeId);
        }
        return employee;
    }

    public Employee save(Employee employee) {
        int employeeId = employees.size();
        if (employeeId == 0) {
            employeeId = 1;
        } else {
            List<Integer> ids = employees.stream()
                    .map(v -> v.getId())
                    .collect(Collectors.toList());
            employeeId = ids.stream()
                    .mapToInt(v -> v)
                    .max().orElse(employeeId);
        }
        employee.setId(employeeId + 1);
        employees.add(employee);

        return employee;
    }

    public Employee update(Integer employeeId, Employee employeeDetails) {
        Employee employee = this.findById(employeeId);
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhone(employeeDetails.getPhone());
        employee.setDepartment(employeeDetails.getDepartment());

        return employee;
    }

    public void delete(Integer employeeId) {
        employees.removeIf(v -> v.getId() == employeeId);
    }
}
