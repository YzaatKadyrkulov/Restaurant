package peaksoft.restaurant.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.restaurant.api.UserApi;
import peaksoft.restaurant.exception.BadCredentialException;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.dto.request.user.SignInRequestRd;
import peaksoft.restaurant.dto.request.user.SignUpRequestRd;
import peaksoft.restaurant.dto.response.restaurant.AuthenticationResponse;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.dto.response.user.SignUpResponseRd;
import peaksoft.restaurant.entities.Restaurant;
import peaksoft.restaurant.entities.User;
import peaksoft.restaurant.enums.Role;
import peaksoft.restaurant.repository.RestaurantRepository;
import peaksoft.restaurant.repository.UserRepository;
import peaksoft.restaurant.security.jwt.JwtService;
import peaksoft.restaurant.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

    public SimpleResponse save(Long restaurantId, SignUpRequestRd signUpRequestRd) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant with id " + restaurantId + " not found"));

        if (restaurant.getUsers().size() >= 15) {
            throw new IllegalStateException("Restaurant with id " + restaurantId + " already has the maximum number of employees (15)");
        }

        if (userRepository.existsByEmail(signUpRequestRd.email())) {
            throw new EntityExistsException(
                    "User with email: " + signUpRequestRd.email() + " already exists!");
        }

        try {
            // Validate age and experience based on role
            if (signUpRequestRd.role() == Role.CHEF) {
                if (!isValidAge(signUpRequestRd.dateOfBirth(), 25, 45) || signUpRequestRd.experience() < 2) {
                    throw new IllegalArgumentException("Invalid age or experience for Chef.");
                }
            } else if (signUpRequestRd.role() == Role.WAITER) {
                if (!isValidAge(signUpRequestRd.dateOfBirth(), 18, 30) || signUpRequestRd.experience() < 1) {
                    throw new IllegalArgumentException("Invalid age or experience for Waiter.");
                }
            } else {
                throw new IllegalArgumentException("Invalid role specified.");
            }

            User user = User.builder()
                    .firstName(signUpRequestRd.firstName())
                    .lastName(signUpRequestRd.lastName())
                    .dateOfBirth(signUpRequestRd.dateOfBirth())
                    .email(signUpRequestRd.email())
                    .password(signUpRequestRd.password())
                    .phoneNumber(signUpRequestRd.phoneNumber())
                    .role(signUpRequestRd.role())
                    .experience(signUpRequestRd.experience())
                    .build();

            restaurant.getUsers().add(user);
            user.setRestaurant(restaurant);
            userRepository.save(user);

            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("User with id " + user.getId() + " saved successfully")
                    .build();
        } catch (IllegalArgumentException | IllegalStateException | NotFoundException | EntityExistsException ex) {
            // Log the exception
            logger.error("Exception occurred while saving user: {}", ex.getMessage(), ex);

            // Return an appropriate error response or throw it further if needed
            throw ex;
        } catch (Exception ex) {
            // Log any other unexpected exceptions
            logger.error("Unexpected exception occurred while saving user: {}", ex.getMessage(), ex);

            // Throw or handle the exception accordingly
            throw new NotFoundException("An unexpected error occurred");
        }
    }

    private boolean isValidAge(LocalDate birthDate, int minAge, int maxAge) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        return age >= minAge && age <= maxAge;
    }


    @Override
    public AuthenticationResponse signIn(SignInRequestRd sign) {
        User user = userRepository.getUserByEmail(sign.email()).orElseThrow(
                () -> new NotFoundException(String.format(
                        "User with email: " + sign.email() + " doesn't exist!")));

        if (sign.email().isBlank()) {
            throw new BadCredentialException("Email is blank");
        }
        if (!passwordEncoder.matches(sign.password(), user.getPassword())) {
            throw new BadCredentialException("Wrong password");
        }

        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public SimpleResponse updateUserById(Long id, SignUpRequestRd signUpRequestRd) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with id %d not found ", id)));

        user.setFirstName(signUpRequestRd.firstName());
        user.setLastName(signUpRequestRd.lastName());
        user.setDateOfBirth(signUpRequestRd.dateOfBirth());
        user.setEmail(signUpRequestRd.email());
        user.setPassword(signUpRequestRd.password());
        user.setPhoneNumber(signUpRequestRd.phoneNumber());
        user.setRole(signUpRequestRd.role());
        user.setExperience(signUpRequestRd.experience());

        userRepository.save(user);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User with id: " + id + " is updated")
                .build();
    }

    @Override
    public SimpleResponse deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id: " + id + " is not found");
        }

        userRepository.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User with id " + id + " was deleted")
                .build();
    }

    @Override
    public Optional<SignUpResponseRd> getUserById(Long id) {
        return Optional.ofNullable(userRepository.getUserById(id).orElseThrow(
                () -> new NotFoundException(String.format("User with id %d not found", id))));
    }

    @Override
    public List<SignUpResponseRd> findAllUsers() {
        return userRepository.findAllUsers();
    }
}
