package com.udea.projectassign.resolver;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.udea.projectassign.entity.Employee;
import com.udea.projectassign.entity.Project;
import com.udea.projectassign.service.EmployeeService;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @MutationMapping
    public Employee addEmployee(@Argument String name, @Argument String lastName,
                                @Argument String dni) {
        return employeeService.addEmployee(name, lastName, dni);
    }

    @QueryMapping
    public List<Employee> allEmployees() {
        return employeeService.getAllEmployees();
    }

    @QueryMapping
    public List<Project> allProjectsByDni(@Argument String dni) {
        return employeeService.getProjectsByDni(dni);
    }

    @QueryMapping
    public Employee employeeById(@Argument Long id) {
        return employeeService.getById(id);
    }
}
