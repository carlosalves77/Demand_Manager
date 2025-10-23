package com.carlosdev.DemandManager.controller;

import com.carlosdev.DemandManager.dto.request.ProjectRequestDTO;
import com.carlosdev.DemandManager.dto.response.ProjectResponseDTO;
import com.carlosdev.DemandManager.service.ProjectService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@Transactional
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<ProjectResponseDTO> projects = projectService.getAllProjects();

        return ResponseEntity.ok().body(projects);
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(
            @Valid @RequestBody ProjectRequestDTO projectRequestDTO
    ) {
        ProjectResponseDTO projectResponse = projectService.createProject(projectRequestDTO);

        return new ResponseEntity<>(projectResponse, HttpStatus.CREATED);
    }


}
