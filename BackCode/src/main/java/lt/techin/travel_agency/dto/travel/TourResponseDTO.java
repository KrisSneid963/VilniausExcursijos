package lt.techin.travel_agency.dto.travel;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class TourResponseDTO {
    private Long id;
    private String title;
    private String imageUrl;
    private Integer duration;
    private BigDecimal price;
    private String category;
    private List<LocalDate> availableDates;
}
