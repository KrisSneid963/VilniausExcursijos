package lt.techin.travel_agency.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Entity
@Table(name = "tour_available_dates")
public class TourAvailableDates implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @Setter
    @Column(nullable = false)
    private LocalDate availableDate;

    public TourAvailableDates() {}

    public TourAvailableDates(Tour tour, LocalDate availableDate) {
        this.tour = tour;
        this.availableDate = availableDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourAvailableDates that = (TourAvailableDates) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(tour, that.tour) &&
                Objects.equals(availableDate, that.availableDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tour, availableDate);
    }
}
