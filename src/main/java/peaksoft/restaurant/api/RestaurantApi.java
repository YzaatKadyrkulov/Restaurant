package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.request.restaurant.RestaurantRequestRd;
import peaksoft.restaurant.dto.response.RestaurantResponseRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.entities.Restaurant;
import peaksoft.restaurant.service.RestaurantService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantApi {
    private static final int MAX_EMPLOYEES = 15;
    private final RestaurantService restaurantService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<SimpleResponse> addRestaurant(@RequestBody RestaurantRequestRd restaurantRequestRd) {
        SimpleResponse response = restaurantService.addRestaurant(restaurantRequestRd);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<SimpleResponse> updateRestaurant(@PathVariable Long id,
                                                           @RequestBody RestaurantRequestRd restaurantRequestRd) {
        SimpleResponse response = restaurantService.updateRestaurant(id, restaurantRequestRd);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<SimpleResponse> deleteRestaurantById(@PathVariable Long id) {
        SimpleResponse response = restaurantService.deleteRestaurantById(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Optional<RestaurantResponseRd> getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<RestaurantResponseRd> findAllRestaurants() {
        return restaurantService.findAllRestaurants();
    }

    @GetMapping("/check-restrictions/{currentEmployees}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public String checkEmployeeRestrictions(@PathVariable int currentEmployees) {
        if (currentEmployees <= MAX_EMPLOYEES) {
            return "Vacancies available.";
        } else {
            return "No vacancies.";
        }
    }
}
