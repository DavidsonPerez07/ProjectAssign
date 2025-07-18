package com.udea.projectassign.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.udea.projectassign.entity.Employee;
import com.udea.projectassign.entity.Project;
import com.udea.projectassign.entity.ProjectEmployee;
import com.udea.projectassign.entity.Status;
import com.udea.projectassign.repository.ProjectRepository;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    private EmployeeService employeeService;

    public ProjectService(ProjectRepository projectRepository, EmployeeService employeeService) {
        this.projectRepository = projectRepository;
        this.employeeService = employeeService;
    }

    public Project addProject(String name, String description, 
                                LocalDateTime startDate, LocalDateTime endDate, Status status, List<String> employeeDnis) {
        return Optional.of(new Project())
                .map(project ->{
                    setIfNotNull(project::setName, name);
                    setIfNotNull(project::setDescription, description);
                    setIfNotNull(project::setStartDate, startDate);
                    setIfNotNull(project::setEndDate, endDate);
                    setIfNotNull(project::setStatus, status);
                    setIfNotNull(project::setEmployees, employeesForAProject(employeeDnis));
                    return projectRepository.save(project);
                })
                .orElseThrow(()-> new RuntimeException("Error creating project"));
    }

    public List<ProjectEmployee> employeesForAProject(List<String> employeeDnis) {
        List<Employee> employees = new ArrayList<>();
        for (String dni : employeeDnis) {
            employees.add(employeeService.getByDni(dni));
        }
        return employees;
    }

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        Optional.ofNullable(value).ifPresent(setter);
    }

    public List<Project> getAllProjects() {
        return Optional.ofNullable(projectRepository.findAll())
                .orElseThrow(()-> new RuntimeException("No project available"));
    }

    public Project getProjectbyId(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(()->new RuntimeException("Project not found for id: " + projectId));
    }
}
