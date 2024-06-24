package peaksoft.restaurant.dto.response;

import lombok.Builder;
import peaksoft.restaurant.enums.RestType;

@Builder
public record RestaurantResponseRd(
        Long id,
        String name,
        String location,
        RestType restType,
        Long numberOfEmployees,
        int service
){
}
