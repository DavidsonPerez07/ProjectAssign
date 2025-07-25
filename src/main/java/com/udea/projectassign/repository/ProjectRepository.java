package com.udea.projectassign.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udea.projectassign.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository <Project, Long> {
    List<Project> findByEmployeesDni(String employeeDni);
}
