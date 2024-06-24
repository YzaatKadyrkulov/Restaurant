package peaksoft.restaurant.dto.request.cheque;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SaveChequeRequestRd(
            double priceAverage,
            LocalDate localDate
) {
}
