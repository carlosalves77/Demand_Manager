package com.carlosdev.DemandManager.controller;

import com.carlosdev.DemandManager.dto.request.TaskRequestDTO;
import com.carlosdev.DemandManager.dto.response.TaskResponseDTO;
import com.carlosdev.DemandManager.dto.validation.OnCreate;
import com.carlosdev.DemandManager.dto.validation.OnUpdate;
import com.carlosdev.DemandManager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createNewTask(
         @Validated({OnCreate.class})
         @RequestBody TaskRequestDTO taskRequestDTO
            ) {

        TaskResponseDTO taskResponseDTO = taskService.createTask(taskRequestDTO);

        return new ResponseEntity<>(taskResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findTask(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) UUID projectId
    ) {

        List<TaskResponseDTO> task = taskService.findTaskByCriteria(status, priority, projectId);
        return ResponseEntity.ok().body(task);
    }

    @PutMapping("/{projectId}/status")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(
            @PathVariable UUID id,
            @Validated({OnUpdate.class})
            @RequestBody TaskRequestDTO taskRequestDTO
            ) {
        TaskResponseDTO updateTaskStatus = taskService.updateTaskStatus(id, taskRequestDTO);

        return ResponseEntity.ok().body(updateTaskStatus);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteTask(
            @PathVariable  UUID id) {
        taskService.deleteStatus(id);
        return ResponseEntity.ok().body("Tarefa deletada");
    }

}
