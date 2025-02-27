package lt.techin.travel_agency.repository;

import lt.techin.travel_agency.model.Tour;
import lt.techin.travel_agency.model.TourCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByCategory(TourCategory category);
    List<Tour> findByPriceLessThanEqual(BigDecimal maxPrice);
    List<Tour> findByTitleContainingIgnoreCase(String title);
}
