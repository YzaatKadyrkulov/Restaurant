package peaksoft.restaurant.dto.request.cheque;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SaveChequeRequestRd(
            int priceAverage,
            LocalDate localDate
) {
}
