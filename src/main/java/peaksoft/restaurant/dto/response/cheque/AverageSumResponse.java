package peaksoft.restaurant.dto.response.cheque;

import lombok.Builder;

@Builder
public record AverageSumResponse(int sum) {
    public AverageSumResponse {
    }
}
