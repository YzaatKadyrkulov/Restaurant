package peaksoft.restaurant.dto.request.stopList;

import java.time.LocalDate;

public record StopListRequestRd(
        String reason,
        LocalDate date
){
}
