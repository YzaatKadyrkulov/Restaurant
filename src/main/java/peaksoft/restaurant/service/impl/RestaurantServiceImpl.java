package peaksoft.restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.request.restaurant.RestaurantRequestRd;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.dto.response.RestaurantResponseRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.entities.Restaurant;
import peaksoft.restaurant.repository.RestaurantRepository;
import peaksoft.restaurant.service.RestaurantService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantServiceImpl implements RestaurantService {
    private static final int MAX_EMPLOYEES = 15;
    private final RestaurantRepository restaurantRepository;

    @Override
    public SimpleResponse addRestaurant(RestaurantRequestRd restaurantRequestRd) {
        if (restaurantRepository.existsByName(restaurantRequestRd.name())) {
            throw new NotFoundException(
                    "Restaurant with name: " + restaurantRequestRd.name() + " already exists!");
        }

        Restaurant restaurant = Restaurant.builder()
                .name(restaurantRequestRd.name())
                .location(restaurantRequestRd.location())
                .restType(restaurantRequestRd.restType())
                .numberOfEmployees(restaurantRequestRd.numberOfEmployees())
                .service(restaurantRequestRd.service())
                .build();

        restaurantRepository.save(restaurant);


        return new SimpleResponse(
                HttpStatus.CREATED,
                "Restaurant successfully saved"
        );
    }

    @Override
    public SimpleResponse updateRestaurant(Long id, RestaurantRequestRd restaurantRequestRd) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Restaurant with id %d not found", id)));

        restaurant.setName(restaurantRequestRd.name());
        restaurant.setLocation(restaurantRequestRd.location());
        restaurant.setRestType(restaurantRequestRd.restType());
        restaurant.setNumberOfEmployees(restaurantRequestRd.numberOfEmployees());
        restaurant.setService(restaurantRequestRd.service());
        restaurantRepository.save(restaurant);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Restaurant with id: " + id + " is updated")
                .build();
    }

    @Override
    public SimpleResponse deleteRestaurantById(Long id) {
            if (!restaurantRepository.existsById(id)) {
                throw new NotFoundException("Restaurant with id: " + id + " is not found");
            }

            restaurantRepository.deleteById(id);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Restaurant with id " + id + " was deleted")
                    .build();
        }

    @Override
    public Optional<RestaurantResponseRd> getRestaurantById(Long id) {
        return Optional.ofNullable(restaurantRepository.getRestaurantById(id).orElseThrow(
                () -> new NotFoundException(String.format("Restaurant with id %d not found", id))));
    }

    @Override
    public List<RestaurantResponseRd> findAllRestaurants() {
        return restaurantRepository.findAllRestaurants();
    }

    @Override
    public String checkEmployeeRestrictions(int currentEmployees) {
        if (currentEmployees <= MAX_EMPLOYEES) {
            return "Vacancies available.";
        } else {
            return "No vacancies.";
        }
    }
}
