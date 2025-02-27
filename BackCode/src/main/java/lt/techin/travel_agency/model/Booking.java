package lt.techin.travel_agency.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    @JsonBackReference
    private Tour tour;

    @Column(nullable = false)
    private LocalDate bookedDate;

    @Column(nullable = false)
    private boolean confirmed = false;

    public Booking() {
        this.confirmed = false;
    }

    public Booking(User user, Tour tour, LocalDate bookedDate, boolean confirmed) {
        this.user = user;
        this.tour = tour;
        this.bookedDate = bookedDate;
        this.confirmed = confirmed;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Tour getTour() {
        return tour;
    }

    public LocalDate getBookedDate() {
        return bookedDate;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public void setBookedDate(LocalDate bookedDate) {
        this.bookedDate = bookedDate;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
