package lt.techin.travel_agency.dto.travel;

import java.util.List;

public record LoginResponseDTO(String username, List<String> roles) {
}