package peaksoft.restaurant.service;

import peaksoft.restaurant.dto.request.user.SignInRequestRd;
import peaksoft.restaurant.dto.request.user.SignUpRequestRd;
import peaksoft.restaurant.dto.response.restaurant.AuthenticationResponse;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.dto.response.user.SignUpResponseRd;

import java.util.List;
import java.util.Optional;

public interface UserService {
    SimpleResponse save(Long restaurantId, SignUpRequestRd signUpRequestRd);

    AuthenticationResponse signIn(SignInRequestRd sign);

    SimpleResponse updateUserById(Long id, SignUpRequestRd signUpRequestRd);

    SimpleResponse deleteUserById(Long id);

    Optional<SignUpResponseRd> getUserById(Long id);

    List<SignUpResponseRd> findAllUsers();
}
