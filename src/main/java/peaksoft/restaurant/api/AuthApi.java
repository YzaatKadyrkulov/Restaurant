package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.restaurant.dto.request.user.SignInRequestRd;
import peaksoft.restaurant.dto.response.restaurant.AuthenticationResponse;
import peaksoft.restaurant.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {
    private final UserService userService;

    @PostMapping("/signIn")
    public AuthenticationResponse signIn(@RequestBody SignInRequestRd signInRequest) {
        return userService.signIn(signInRequest);
    }
}
