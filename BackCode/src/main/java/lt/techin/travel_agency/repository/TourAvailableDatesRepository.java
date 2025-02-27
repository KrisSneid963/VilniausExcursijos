package lt.techin.travel_agency.repository;

import lt.techin.travel_agency.model.TourAvailableDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TourAvailableDatesRepository extends JpaRepository<TourAvailableDates, Long> {

    List<TourAvailableDates> findByTourId(Long tourId);

    @Transactional
    void deleteByTourIdAndAvailableDateIn(Long tourId, List<LocalDate> availableDates);
}
