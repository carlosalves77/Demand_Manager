package com.carlosdev.DemandManager.service;

import com.carlosdev.DemandManager.dto.request.ProjectRequestDTO;
import com.carlosdev.DemandManager.dto.response.ProjectResponseDTO;
import com.carlosdev.DemandManager.mapper.ProjectMapper;
import com.carlosdev.DemandManager.model.Project;
import com.carlosdev.DemandManager.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getAllProjects() {

        List<Project> projects = projectRepository.findAll();


        return projects.stream().map(ProjectMapper::toResponse).collect(Collectors.toList());
    }

    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO) {

        Project newProject = ProjectMapper.toEntity(projectRequestDTO);


        Project saveProject = projectRepository.save(newProject);

        return ProjectMapper.toResponse(saveProject);
    }
}
