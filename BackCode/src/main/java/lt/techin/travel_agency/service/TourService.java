package lt.techin.travel_agency.service;

import lt.techin.travel_agency.dto.travel.TourResponseDTO;
import lt.techin.travel_agency.model.Tour;
import lt.techin.travel_agency.model.TourAvailableDates;
import lt.techin.travel_agency.model.TourCategory;
import lt.techin.travel_agency.repository.TourAvailableDatesRepository;
import lt.techin.travel_agency.repository.TourRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourService {
    private final TourRepository tourRepository;
    private final TourAvailableDatesRepository tourAvailableDatesRepository;

    public TourService(TourRepository tourRepository, TourAvailableDatesRepository tourAvailableDatesRepository) {
        this.tourRepository = tourRepository;
        this.tourAvailableDatesRepository = tourAvailableDatesRepository;
    }

    @Transactional(readOnly = true)
    public List<TourResponseDTO> getAllTours() {
        return tourRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TourResponseDTO> getToursByCategory(TourCategory category) {
        return tourRepository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TourResponseDTO> getToursByMaxPrice(BigDecimal maxPrice) {
        return tourRepository.findByPriceLessThanEqual(maxPrice).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TourResponseDTO> searchToursByTitle(String title) {
        return tourRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional
    public TourResponseDTO createTour(Tour tour) {
        Tour savedTour = tourRepository.save(tour);
        return convertToDTO(savedTour);
    }

    public List<TourAvailableDates> getAvailableDatesForTour(Long tourId) {
        return tourAvailableDatesRepository.findByTourId(tourId);
    }

    @Transactional
    public ResponseEntity<?> addTourDates(Long tourId, List<String> dateStrings) {
        Optional<Tour> optionalTour = tourRepository.findById(tourId);

        if (optionalTour.isEmpty()) {
            return ResponseEntity.badRequest().body("Tour not found");
        }

        Tour tour = optionalTour.get();

        try {
            List<TourAvailableDates> availableDates = dateStrings.stream()
                    .map(dateStr -> new TourAvailableDates(tour, LocalDate.parse(dateStr))) // Convert String to LocalDate
                    .collect(Collectors.toList());

            tourAvailableDatesRepository.saveAll(availableDates);

            return ResponseEntity.ok("Dates added successfully");
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format. Use YYYY-MM-DD.");
        }
    }

    @Transactional
    public ResponseEntity<?> deleteTourDates(Long tourId, List<String> dateStrings) {
        Optional<Tour> optionalTour = tourRepository.findById(tourId);

        if (optionalTour.isEmpty()) {
            return ResponseEntity.badRequest().body("Tour not found");
        }

        Tour tour = optionalTour.get();

        try {
            List<LocalDate> datesToDelete = dateStrings.stream()
                    .map(LocalDate::parse) // Convert String to LocalDate
                    .toList();

            tourAvailableDatesRepository.deleteByTourIdAndAvailableDateIn(tourId, datesToDelete);

            return ResponseEntity.ok("Dates deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting dates: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteTour(Long id) {
        if (!tourRepository.existsById(id)) {
            throw new RuntimeException("Tour with ID " + id + " does not exist!");
        }
        tourRepository.deleteById(id);
    }

    private TourResponseDTO convertToDTO(Tour tour) {
        return new TourResponseDTO(
                tour.getId(),
                tour.getTitle(),
                tour.getImageUrl(),
                tour.getDuration(),
                tour.getPrice(),
                tour.getCategory() != null ? tour.getCategory().name() : "UNKNOWN",
                tour.getAvailableDates() != null && !tour.getAvailableDates().isEmpty()
                        ? tour.getAvailableDates().stream()
                        .map(TourAvailableDates::getAvailableDate)
                        .collect(Collectors.toList())
                        : Collections.emptyList()
        );
    }
}
