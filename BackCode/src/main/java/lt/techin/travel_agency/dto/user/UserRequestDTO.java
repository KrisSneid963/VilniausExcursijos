package lt.techin.travel_agency.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotNull(message = "Name cannot be null")
        @Size(min = 2, max = 100, message = "Name must be from 2 to 100 characters")
        String name,

        @NotNull(message = "Email cannot be null")
        @Size(max = 254, message = "Email cannot exceed 254 characters")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Must be a valid email address"
        )
        String email,

        @NotNull(message = "Password cannot be null")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[\\S]{10,128}$",
                message = "Password must contain at least one lowercase, one uppercase, one number, one special character"
        )
        String password,

        String role
) {

}
