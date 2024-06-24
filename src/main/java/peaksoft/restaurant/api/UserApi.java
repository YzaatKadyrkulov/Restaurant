package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.request.user.SignUpRequestRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.dto.response.user.SignUpResponseRd;
import peaksoft.restaurant.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;

    @PostMapping("/save/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse save(@PathVariable Long id,
                               @Valid @RequestBody SignUpRequestRd signUpRequest) {
        return userService.save(id, signUpRequest);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse updateUserById(@PathVariable Long id,
                                         @Valid @RequestBody SignUpRequestRd signUpRequestRd) {
        return userService.updateUserById(id, signUpRequestRd);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Optional<SignUpResponseRd> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public List<SignUpResponseRd> findAllUsers() {
        return userService.findAllUsers();
    }

}
