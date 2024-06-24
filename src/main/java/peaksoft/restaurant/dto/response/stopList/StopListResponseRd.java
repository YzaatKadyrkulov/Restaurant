package peaksoft.restaurant.dto.response.stopList;

import java.time.LocalDate;

public record StopListResponseRd(
        Long id,
        String reason,
        LocalDate date
){
}
