package peaksoft.restaurant.dto.response.cheque;

import lombok.Builder;
import peaksoft.restaurant.entities.Menuitem;

import java.time.LocalDate;
import java.util.List;

@Builder
public record SaveChequeResponseRd(
        Long id,
        double priceAverage,
        LocalDate localDate
) {
}
