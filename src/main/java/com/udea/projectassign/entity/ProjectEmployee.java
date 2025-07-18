package com.udea.projectassign.entity;

import javax.persistence.*;

@Entity
@IdClass(ProjectEmployeeId.class)
@Table(name = "project_employee")
public class ProjectEmployee {

    @EmbeddedId
    private ProjectEmployeeId id = new ProjectEmployeeId();

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;


    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    private String role;
}
