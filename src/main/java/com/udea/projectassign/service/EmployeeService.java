package com.udea.projectassign.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.udea.projectassign.entity.Employee;
import com.udea.projectassign.entity.Project;
import com.udea.projectassign.repository.EmployeeRepository;
import com.udea.projectassign.repository.ProjectRepository;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;

    public EmployeeService(EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    public Employee addEmployee(String name, String lastName, String dni) {
        return Optional.of(new Employee())
                .map(employee ->{
                    setIfNotNull(employee::setName, name);
                    setIfNotNull(employee::setLastName, lastName);
                    setIfNotNull(employee::setDni, dni);
                    return employeeRepository.save(employee);
                })
                .orElseThrow(()-> new RuntimeException("Error creating employee"));
    }

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        Optional.ofNullable(value).ifPresent(setter);
    }

    public List<Project> getProjectsByDni(String dni) {
        return projectRepository.findByEmployeesDni(dni);
    }

    public Employee getByDni(String dni) {
        return employeeRepository.getByDni(dni);
    }

    public Employee getById(Long empId) {
        return employeeRepository.findById(empId)
                .orElseThrow(()->new RuntimeException("Employee not found for id: " + empId));
    }

    public List<Employee> getAllEmployees() {
        return Optional.ofNullable(employeeRepository.findAll())
                .orElseThrow(()-> new RuntimeException("No employee available"));
    }
}
