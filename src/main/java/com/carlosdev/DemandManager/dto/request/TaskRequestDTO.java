package com.carlosdev.DemandManager.dto.request;

import com.carlosdev.DemandManager.dto.validation.OnCreate;
import com.carlosdev.DemandManager.dto.validation.OnUpdate;
import com.carlosdev.DemandManager.util.PriorityType;
import com.carlosdev.DemandManager.util.StatusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record TaskRequestDTO(
        @NotBlank(message = "É necessário o titulo", groups = {OnCreate.class}) @Size(min = 5, max = 150)
        String title,
        @NotBlank(message = "informe o título da descrição", groups = {OnCreate.class})
        String description,
        @NotNull(message = "Informe o status da tarefa", groups = {OnUpdate.class, OnCreate.class})
        StatusType status,
        @NotNull(message = "informe a prioridade", groups = {OnCreate.class})
        PriorityType priority,
        @NotNull(message = "Data de vencimento da tarefa", groups = {OnCreate.class})
        LocalDate dueDate,
        @NotNull(message = "informe o projectId do projeto", groups = {OnCreate.class})
        UUID id
) {

}
