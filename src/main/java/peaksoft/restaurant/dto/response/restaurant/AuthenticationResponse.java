package peaksoft.restaurant.dto.response.restaurant;

import lombok.Builder;
import peaksoft.restaurant.enums.Role;

@Builder
public record AuthenticationResponse(
        String token,
        String email,
        Role role
){
}
