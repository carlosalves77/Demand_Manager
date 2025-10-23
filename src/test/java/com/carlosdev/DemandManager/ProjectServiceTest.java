package com.carlosdev.DemandManager;

import com.carlosdev.DemandManager.dto.request.ProjectRequestDTO;
import com.carlosdev.DemandManager.dto.response.ProjectResponseDTO;
import com.carlosdev.DemandManager.model.Project;
import com.carlosdev.DemandManager.repository.ProjectRepository;
import com.carlosdev.DemandManager.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Captor
    private ArgumentCaptor<Project> projectArgumentCaptor;

    @InjectMocks
    private ProjectService projectService;

    @Test
    @DisplayName("Deve retorna a resposta de um projeto criado")
    void createProject_ShouldReturnProjectResponseDto() {

        LocalDate endDate = LocalDate.of(2025, 12, 31);
        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO(
                "Projeto test",
                "Description",
                endDate
        );

        Project saveProject = new Project();
        saveProject.setId(UUID.fromString("27039ac2-3c62-4a05-912e-4ccee785d43f"));
        saveProject.setName("Projeto test");
        saveProject.setDescription("Description");
        saveProject.setEndDate(endDate);
        saveProject.setStartDate(LocalDate.now());
        saveProject.setTasks(new ArrayList<>());

        when(projectRepository.save(any(Project.class))).thenReturn(saveProject);

        ProjectResponseDTO projectResponseDTO = projectService.createProject(projectRequestDTO);

        verify(projectRepository, times(1)).save(projectArgumentCaptor.capture());

        Project projectSendToSave = projectArgumentCaptor.getValue();

        assertNull(projectSendToSave.getId());
        assertEquals("Projeto test", projectSendToSave.getName());
        assertEquals("Description", projectSendToSave.getDescription());
        assertEquals(endDate, projectSendToSave.getEndDate());
        assertNotNull(projectSendToSave.getStartDate());
        assertEquals(LocalDate.now(), projectSendToSave.getStartDate());



        assertNotNull(projectResponseDTO);
        assertEquals("27039ac2-3c62-4a05-912e-4ccee785d43f", projectResponseDTO.id());
        assertEquals("Projeto test", projectResponseDTO.name());
        assertEquals("Description", projectResponseDTO.description());
        assertEquals(saveProject.getStartDate(), projectResponseDTO.startDate());
        assertEquals(endDate, projectResponseDTO.endDate());
        assertNotNull(projectResponseDTO.tasks());
        assertTrue(projectResponseDTO.tasks().isEmpty());
    }

    @Test
    @DisplayName("Deve retorna uma lista de projetos assim que o metodo é chamado")
    void getAllProjects_ShouldReturnDtoList_WhenProjectExists() {

        Project project1 = new Project();
        project1.setId(UUID.fromString("27039ac2-3c62-4a05-912e-4ccee785d43f"));
        project1.setName("Project1");
        project1.setDescription("Description1");
        project1.setStartDate(LocalDate.now());
        project1.setTasks(new ArrayList<>());

        Project project2 = new Project();
        project2.setId(UUID.fromString("6746a571-9998-40b7-9c42-0988ec4db5c9"));
        project2.setName("Project2");
        project2.setDescription("Description2");
        project2.setStartDate(LocalDate.now());
        project2.setTasks(new ArrayList<>());

        List<Project> projectListRepo = List.of(project1, project2);

        when(projectRepository.findAll()).thenReturn(projectListRepo);

        List<ProjectResponseDTO> responseList = projectService.getAllProjects();

        verify(projectRepository, times(1)).findAll();

        assertNotNull(responseList);
        assertEquals(2, responseList.size());

        assertEquals("Project1", responseList.get(0).name());
        assertEquals("27039ac2-3c62-4a05-912e-4ccee785d43f", responseList.get(0).id());

        assertEquals("Project2", responseList.get(1).name());
        assertEquals("6746a571-9998-40b7-9c42-0988ec4db5c9", responseList.get(1).id());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia de projetos")
    void getAllProjects_ShouldReturnEmptyList_WhenNoProjectExist() {

        when(projectRepository.findAll()).thenReturn(Collections.emptyList());

        List<ProjectResponseDTO> responseList = projectService.getAllProjects();

        verify(projectRepository, times(1)).findAll();

        assertNotNull(responseList);
        assertTrue(responseList.isEmpty());
    }
}
