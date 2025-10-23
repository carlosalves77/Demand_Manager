package com.carlosdev.DemandManager.dto.response;

import com.carlosdev.DemandManager.util.PriorityType;
import com.carlosdev.DemandManager.util.StatusType;

import java.time.LocalDate;


public record TaskResponseDTO(

        String id,
        String title,
        String description,
        StatusType status,
        PriorityType priority,
        LocalDate dueDate
        ) {
}
