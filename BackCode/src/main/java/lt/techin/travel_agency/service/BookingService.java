package lt.techin.travel_agency.service;

import lt.techin.travel_agency.model.Booking;
import lt.techin.travel_agency.model.Tour;
import lt.techin.travel_agency.model.User;
import lt.techin.travel_agency.repository.BookingRepository;
import lt.techin.travel_agency.repository.TourRepository;
import lt.techin.travel_agency.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TourRepository tourRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, TourRepository tourRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
    }


    @Transactional
    public String createBooking(Long userId, Long tourId, String bookedDate) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Tour> tour = tourRepository.findById(tourId);

        if (user.isEmpty() || tour.isEmpty()) {
            throw new IllegalArgumentException("Invalid user or tour ID");
        }

        Booking booking = new Booking(user.get(), tour.get(), LocalDate.parse(bookedDate), false);
        bookingRepository.save(booking);
        return "Booking confirmed";
    }


    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional
    public String confirmBooking(Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

        if (bookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Booking not found");
        }

        Booking booking = bookingOpt.get();
        booking.setConfirmed(true);
        bookingRepository.save(booking);
        return "Booking confirmed successfully!";
    }
}
