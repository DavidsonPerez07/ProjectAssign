package com.udea.projectassign.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.udea.projectassign.entity.Employee;
import com.udea.projectassign.entity.Project;
import com.udea.projectassign.entity.Status;
import com.udea.projectassign.repository.EmployeeRepository;
import com.udea.projectassign.repository.ProjectRepository;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    public ProjectService(ProjectRepository projectRepository, EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Project addProject(String name, String description, 
                                LocalDateTime startDate, LocalDateTime endDate, List<String> employeeDnis) {
        Project project = new Project();

        project.setName(name);
        project.setDescription(description);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setStatus(Status.ACTIVE);
        project.setEmployees(employeesForAProject(employeeDnis));
        projectRepository.save(project);

        for (Employee employe : employeesForAProject(employeeDnis)) {
            employe.getProjects().add(project);
        }

        return project;
    }

    public List<Employee> employeesForAProject(List<String> employeeDnis) {
        List<Employee> employees = new ArrayList<>();
        for (String dni : employeeDnis) {
            employees.add(employeeService.getByDni(dni));
        }
        return employees;
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
