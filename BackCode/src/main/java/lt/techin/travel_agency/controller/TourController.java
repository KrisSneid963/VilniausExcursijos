package lt.techin.travel_agency.controller;

import lt.techin.travel_agency.dto.travel.TourResponseDTO;
import lt.techin.travel_agency.model.Tour;
import lt.techin.travel_agency.model.TourCategory;
import lt.techin.travel_agency.service.TourService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {
    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public List<TourResponseDTO> getAllTours() {
        return tourService.getAllTours();
    }

    @GetMapping("/category/{category}")
    public List<TourResponseDTO> getToursByCategory(@PathVariable TourCategory category) {
        return tourService.getToursByCategory(category);
    }

    @GetMapping("/search")
    public List<TourResponseDTO> searchToursByTitle(@RequestParam String title) {
        return tourService.searchToursByTitle(title);
    }
    @PostMapping
    public ResponseEntity<TourResponseDTO> createTour(@RequestBody Tour tour) {
        TourResponseDTO createdTour = tourService.createTour(tour);
        return new ResponseEntity<>(createdTour, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{tourId}/dates")
    public ResponseEntity<?> addTourDates(@PathVariable Long tourId, @RequestBody List<String> dates) {
        return tourService.addTourDates(tourId, dates);
    }
    @DeleteMapping("/{tourId}/dates")
    public ResponseEntity<?> deleteTourDates(@PathVariable Long tourId, @RequestBody List<String> dates) {
        return tourService.deleteTourDates(tourId, dates);
    }


}
