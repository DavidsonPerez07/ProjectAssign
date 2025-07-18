package com.udea.projectassign.resolver;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.udea.projectassign.entity.Project;
import com.udea.projectassign.service.ProjectService;

@Controller
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @MutationMapping
    public Project addProject(@Argument String name, @Argument String description,
                                @Argument String startDate, @Argument String endDate,
                                @Argument List<String> employeeDnis) {

        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
    
        return projectService.addProject(name, description, start, end, employeeDnis);
    }

    @QueryMapping
    public List<Project> allProjects() {
        return projectService.getAllProjects();
    }

    @QueryMapping
    public Project projectById(@Argument Long id) {
        return projectById(id);
    }
}
