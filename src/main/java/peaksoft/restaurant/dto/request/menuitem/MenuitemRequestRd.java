package peaksoft.restaurant.dto.request.menuitem;

import lombok.Builder;

@Builder
public record MenuitemRequestRd(
        String name,
        String image,
        double price,
        String description,
        boolean isVegetarian
) {
}
