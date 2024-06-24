package peaksoft.restaurant.dto.response.cheque;

import lombok.Builder;
import java.util.List;

@Builder
public record SaveChequeResponseRd(
        Long id,
        String waiterFullName,
        List<String> items,
        int priceAverage,
        int service,
        int grandTotal
) {
}
