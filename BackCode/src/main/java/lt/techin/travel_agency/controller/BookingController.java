package lt.techin.travel_agency.controller;

import lombok.Getter;
import lt.techin.travel_agency.model.Booking;
import lt.techin.travel_agency.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> bookTour(@RequestBody BookingRequest request) {
        try {
            String confirmationMessage = bookingService.createBooking(
                    request.getUserId(), request.getTourId(), request.getBookedDate()
            );
            return ResponseEntity.ok(confirmationMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @PutMapping("/confirm/{bookingId}")
    public ResponseEntity<?> confirmBooking(@PathVariable Long bookingId) {
        try {
            return ResponseEntity.ok(bookingService.confirmBooking(bookingId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Getter
    public static class BookingRequest {
        private Long userId;
        private Long tourId;
        private String bookedDate;

    }

}
