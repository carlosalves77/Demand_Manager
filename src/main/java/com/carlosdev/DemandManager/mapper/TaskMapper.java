package com.carlosdev.DemandManager.mapper;

import com.carlosdev.DemandManager.dto.request.TaskRequestDTO;
import com.carlosdev.DemandManager.dto.response.TaskResponseDTO;
import com.carlosdev.DemandManager.model.Task;
import org.springframework.stereotype.Component;


@Component
public class TaskMapper {


    public static TaskResponseDTO toResponse(Task task) {
        if (task == null) {
            return null;
        }

        return new TaskResponseDTO(
                task.getId().toString(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate()
        );

    }

    public static Task toEntity(TaskRequestDTO taskRequestDTO) {
        if (taskRequestDTO == null) {
            return null;
        }


        Task taskEntity = new Task();
        taskEntity.setTitle(taskRequestDTO.title());
        taskEntity.setDescription(taskRequestDTO.description());
        taskEntity.setStatus(taskRequestDTO.status());
        taskEntity.setPriority(taskRequestDTO.priority());
        taskEntity.setDueDate(taskRequestDTO.dueDate());

        return taskEntity;
    }


}
