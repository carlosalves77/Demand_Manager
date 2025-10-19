package com.carlosdev.DemandManager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProjectRequestDTO(

        @NotBlank(message = "informe o nome do projeto") @Size(min = 3, max = 100)
        String name,

        String description,

        LocalDate endDate

) {
}
