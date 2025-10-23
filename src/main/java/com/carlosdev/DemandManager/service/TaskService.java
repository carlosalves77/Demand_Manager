package com.carlosdev.DemandManager.service;

import com.carlosdev.DemandManager.dto.request.TaskRequestDTO;
import com.carlosdev.DemandManager.dto.response.TaskResponseDTO;
import com.carlosdev.DemandManager.exception.UserIdNotExistException;
import com.carlosdev.DemandManager.mapper.TaskMapper;
import com.carlosdev.DemandManager.model.Project;
import com.carlosdev.DemandManager.model.Task;
import com.carlosdev.DemandManager.repository.ProjectRepository;
import com.carlosdev.DemandManager.repository.TaskRepository;
import com.carlosdev.DemandManager.util.TaskSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {

        Project projectId = projectRepository.findById(taskRequestDTO.id()).orElseThrow(()
                -> new UserIdNotExistException("Projeto não encontrado"));

        Task newTask = TaskMapper.toEntity(taskRequestDTO);

        newTask.setProject(projectId);

        Task saveTask = taskRepository.save(newTask);

        return TaskMapper.toResponse(saveTask);
    }

    public List<TaskResponseDTO> findTaskByCriteria(String status, String priority, UUID projectId) {

        Specification<Task> spec = ((root, query, criteriaBuilder)
                -> criteriaBuilder.conjunction());

        if (status != null) {
            spec = spec.and(TaskSpecifications.hasStatus(status));
        }
        if (priority != null) {
            spec = spec.and(TaskSpecifications.hasPriority(priority));
        }
        if (projectId != null) {
            spec = spec.and(TaskSpecifications.hasProjectId(projectId));
        }

        List<Task> task = taskRepository.findAll(spec);

        return task.stream().map(TaskMapper::toResponse).collect(Collectors.toList());
    }

    public TaskResponseDTO updateTaskStatus(UUID id, TaskRequestDTO taskRequestDTO) {
        Task task =
                taskRepository.findById(id)
                        .orElseThrow(() -> new UserIdNotExistException("Tarefa não existente"));

        task.setStatus(taskRequestDTO.status());

        Task updateTask = taskRepository.save(task);

        return TaskMapper.toResponse(updateTask);

    }

    public void deleteStatus(UUID id) {

        boolean thisTaskExists = taskRepository.existsById(id);

        if (!thisTaskExists) {
            throw new UserIdNotExistException("Tarefa não existente");
        }

        taskRepository.deleteById(id);
    }
}
