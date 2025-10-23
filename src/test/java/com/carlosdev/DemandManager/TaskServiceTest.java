package com.carlosdev.DemandManager;

import com.carlosdev.DemandManager.dto.request.TaskRequestDTO;
import com.carlosdev.DemandManager.dto.response.TaskResponseDTO;
import com.carlosdev.DemandManager.exception.UserIdNotExistException;
import com.carlosdev.DemandManager.model.Project;
import com.carlosdev.DemandManager.model.Task;
import com.carlosdev.DemandManager.repository.ProjectRepository;
import com.carlosdev.DemandManager.repository.TaskRepository;
import com.carlosdev.DemandManager.service.TaskService;
import com.carlosdev.DemandManager.util.PriorityType;
import com.carlosdev.DemandManager.util.StatusType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    private final UUID PROJECT_ID = UUID.fromString("6746a571-9998-40b7-9c42-0988ec4db5c9");
    private final UUID TASK_ID = UUID.fromString("c5c5ef32-195b-45d5-9b7c-f3e98212117d");
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;

    @Captor
    private ArgumentCaptor<Task> taskArgumentCaptor;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_ShouldSaveTask_WhenProjectExists() {


        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(
                "New Task",
                "description1",
                StatusType.DOING,
                PriorityType.HIGH,
                LocalDate.now(),
                PROJECT_ID
        );

        Project foundProject = new Project();
        foundProject.setId(PROJECT_ID);
        foundProject.setName("Projeto Pai");

        Task saveTask = new Task();
        saveTask.setId(TASK_ID);
        saveTask.setTitle("Nova Tarefa de teste");
        saveTask.setProject(foundProject);

        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(foundProject));

        when(taskRepository.save(any(Task.class))).thenReturn(saveTask);

        TaskResponseDTO responseDTO = taskService.createTask(taskRequestDTO);

        verify(projectRepository).findById(PROJECT_ID);

        verify(taskRepository).save(taskArgumentCaptor.capture());
        Task taskSendToSave = taskArgumentCaptor.getValue();

        assertNull(taskSendToSave.getId());
        assertEquals("New Task", taskSendToSave.getTitle());
        assertNotNull(taskSendToSave.getProject());
        assertEquals(PROJECT_ID, taskSendToSave.getProject().getId());

        assertNotNull(responseDTO);
        assertEquals(TASK_ID.toString(), responseDTO.id());
        assertEquals("Nova Tarefa de teste", responseDTO.title());
    }

    @Test
    void createTask_shouldThrowException_WhenProjectNotFound() {

        TaskRequestDTO requestDTO = new TaskRequestDTO(
                "New Task",
                "description1",
                StatusType.DOING,
                PriorityType.HIGH,
                LocalDate.now(),
                PROJECT_ID
        );

        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.empty());

        UserIdNotExistException exception = assertThrows(UserIdNotExistException.class,
                () -> {
                    taskService.createTask(requestDTO);
                }
        );

        assertEquals("Projeto não encontrado", exception.getMessage());

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void findTaskByCriteria_ShouldReturnDtoList_WhenTaskFound() {

        UUID projectId = UUID.randomUUID();

        Task task1 = new Task();
        task1.setId(projectId);
        task1.setTitle("Title1");


        Task task2 = new Task();
        task2.setId(projectId);
        task2.setTitle("Title2");

        List<Task> taskFromRepo = List.of(task1, task2);

        when(taskRepository.findAll(any(Specification.class))).thenReturn(taskFromRepo);

        List<TaskResponseDTO> responseList = taskService.
                findTaskByCriteria(null, null, null);

        verify( taskRepository, times(1)).findAll(any(Specification.class));

        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals("Title1", responseList.get(0).title());

    }

    @Test
    void findTaskByCriteria_ShouldReturnEmptyList_WhenNoTaskFound() {

        when(taskRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        List<TaskResponseDTO> responseList = taskService.
                findTaskByCriteria(null, null, null);

        verify(taskRepository, times(1)).findAll(any(Specification.class));

        assertNotNull(responseList);
        assertTrue(responseList.isEmpty());
    }

    @Test
    void updateTaskStatus_ShouldUpdateStatus_WhenTaskExist() {

        UUID taskId = UUID.randomUUID();

        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(
                "empty",
                "empty",
                StatusType.DONE,
                PriorityType.HIGH,
                LocalDate.now(),
                taskId
        );

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setStatus(StatusType.DONE);

        Task updateTask = new Task();
        updateTask.setId(taskId);
        updateTask.setStatus(StatusType.TODO);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updateTask);

        TaskResponseDTO responseDTO = taskService.updateTaskStatus(taskId, taskRequestDTO);

        verify(taskRepository).findById(taskId);

        verify(taskRepository).save(taskArgumentCaptor.capture());
        Task taskSendToSave = taskArgumentCaptor.getValue();

        assertNotNull(taskSendToSave);
        assertEquals(taskId, taskSendToSave.getId());
        assertEquals(StatusType.DONE, taskSendToSave.getStatus());

        assertNotNull(responseDTO);
        assertEquals(StatusType.TODO, responseDTO.status());
        assertEquals(taskId.toString(), responseDTO.id());
    }

    @Test
    void updateTaskStatus_ShouldThrowException_WhenTaskNotFound() {

        UUID randomUUID = UUID.randomUUID();
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(
                "empty",
                "empty",
                StatusType.DONE,
                PriorityType.HIGH,
                LocalDate.now(),
                randomUUID
        );

        when(taskRepository.findById(randomUUID)).thenReturn(Optional.empty());

        UserIdNotExistException exception = assertThrows(UserIdNotExistException.class, () ->
                taskService.updateTaskStatus(randomUUID, taskRequestDTO)
                );

        assertEquals("Tarefa não existente", exception.getMessage());

        verify(taskRepository, never()).save((any(Task.class)));
    }

    @Test
    void deleteStatus_ShouldDeleteTask_WhenTaskExists() {
        UUID randomUUID = UUID.randomUUID();

        when(taskRepository.existsById(randomUUID)).thenReturn(true);

        doNothing().when(taskRepository).deleteById(randomUUID);

        assertDoesNotThrow(() -> {
            taskService.deleteStatus(randomUUID);
        });

        verify(taskRepository,times(1)).existsById(randomUUID);

        verify(taskRepository, times(1)).deleteById(randomUUID);
    }

    @Test
    void deleteStatus_ShouldThrowException_WhenTaskDoesNotExists() {

        UUID nonExistentId = UUID.randomUUID();

        when(taskRepository.existsById(nonExistentId)).thenReturn(false);

        UserIdNotExistException exception = assertThrows(UserIdNotExistException.class,
        () -> {taskService.deleteStatus(nonExistentId);}
        );

        assertEquals("Tarefa não existente", exception.getMessage());

        verify(taskRepository, times(1)).existsById(nonExistentId);
        verify(taskRepository, never()).deleteById(nonExistentId);

    }


}
