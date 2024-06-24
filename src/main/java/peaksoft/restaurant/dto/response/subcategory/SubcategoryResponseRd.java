package peaksoft.restaurant.dto.response.subcategory;

import lombok.Builder;

@Builder
public record SubcategoryResponseRd(
        Long id,
        String name
) {
}
