package com.carlosdev.DemandManager.dto.response;


import java.time.LocalDate;
import java.util.List;

public record ProjectResponseDTO(
        String id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        List<TaskResponseDTO> tasks
) {
}
