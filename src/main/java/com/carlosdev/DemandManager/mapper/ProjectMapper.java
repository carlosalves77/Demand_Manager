package com.carlosdev.DemandManager.mapper;

import com.carlosdev.DemandManager.dto.request.ProjectRequestDTO;
import com.carlosdev.DemandManager.dto.response.ProjectResponseDTO;
import com.carlosdev.DemandManager.dto.response.TaskResponseDTO;
import com.carlosdev.DemandManager.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
public class ProjectMapper {

    private static final Logger log = LoggerFactory.getLogger(ProjectMapper.class);

    public static ProjectResponseDTO toResponse(Project project) {
        if (project == null) {
            return null;
        }


        List<TaskResponseDTO> taskRequestDTO = null;
        if (project.getTasks() != null) {
            taskRequestDTO = project.getTasks().stream().map(TaskMapper::toResponse).toList();
        }

        return new ProjectResponseDTO(
                String.valueOf(project.getId()),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                taskRequestDTO
        );
    }

    public static Project toEntity(ProjectRequestDTO projectRequestDTO) {
        if (projectRequestDTO == null) {
            return null;
        }

        Project projectEntity = new Project();

        projectEntity.setName(projectRequestDTO.name());
        projectEntity.setDescription(projectRequestDTO.description());
        projectEntity.setEndDate(projectRequestDTO.endDate());
        projectEntity.setStartDate(LocalDate.now());


        return projectEntity;
    }


}
