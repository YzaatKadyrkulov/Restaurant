package peaksoft.restaurant.dto.response.category;

import lombok.Builder;

@Builder
public record CategoryRsRd(
        Long id,
        String name
){
}
