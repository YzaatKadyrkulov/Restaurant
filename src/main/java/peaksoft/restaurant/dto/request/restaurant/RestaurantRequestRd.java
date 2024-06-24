package peaksoft.restaurant.dto.request.restaurant;

import lombok.Builder;
import peaksoft.restaurant.enums.RestType;

@Builder
public record RestaurantRequestRd(
        String name,
        String location,
        RestType restType,
        Long numberOfEmployees,
        int service
        ) {
}
