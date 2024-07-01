package peaksoft.restaurant.dto.request.user;

import lombok.Builder;
import peaksoft.restaurant.enums.Role;

import java.time.LocalDate;

public record SignUpRequestRd(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String password,
        String phoneNumber,
        Role role,
        int experience
        ) {
}
