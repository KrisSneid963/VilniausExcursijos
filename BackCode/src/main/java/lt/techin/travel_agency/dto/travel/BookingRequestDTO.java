package lt.techin.travel_agency.dto.travel;

import lombok.Getter;

@Getter
public class BookingRequestDTO {
    private Long userId;
    private Long tourId;
    private String bookedDate;
}
