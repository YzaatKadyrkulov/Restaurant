package peaksoft.restaurant.dto.request.user;

import lombok.Builder;

@Builder
public record SignInRequestRd(
        String email,
        String password
) {
}
