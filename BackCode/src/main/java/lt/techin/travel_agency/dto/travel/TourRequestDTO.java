package lt.techin.travel_agency.dto.travel;

import jakarta.validation.constraints.*;
import lt.techin.travel_agency.model.TourCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public record TourRequestDTO(
        @NotBlank(message = "Title cannot be empty")
        String title,

        @NotNull(message = "ImageUrl cannot be null")
        String imageUrl,

        @NotNull(message = "Duration cannot be null")
        Integer duration,

        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Category cannot be null")
        TourCategory category,

        @Size(min = 1, max = 20, message = "Must add at least 1 date")
        List<LocalDate> availableDates
) {}
