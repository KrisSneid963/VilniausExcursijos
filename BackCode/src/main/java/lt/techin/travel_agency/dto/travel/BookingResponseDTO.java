package lt.techin.travel_agency.dto.travel;

import lt.techin.travel_agency.model.Booking;
import lombok.Getter;

@Getter
public class BookingResponseDTO {
    private Long id;
    private Long userId;
    private Long tourId;
    private String bookedDate;
    private boolean confirmed;

    public BookingResponseDTO(Booking booking) {
        this.id = booking.getId();
        this.userId = booking.getUser().getId();
        this.tourId = booking.getTour().getId();
        this.bookedDate = booking.getBookedDate().toString();
        this.confirmed = booking.isConfirmed();
    }
}
