package peaksoft.restaurant.service;

import peaksoft.restaurant.dto.request.restaurant.RestaurantRequestRd;
import peaksoft.restaurant.dto.response.RestaurantResponseRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.entities.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    SimpleResponse addRestaurant(RestaurantRequestRd restaurantRequestRd);

    SimpleResponse updateRestaurant(Long id, RestaurantRequestRd restaurantRequestRd);

    SimpleResponse deleteRestaurantById(Long id);

    Optional<RestaurantResponseRd> getRestaurantById(Long id);

    List<RestaurantResponseRd> findAllRestaurants();

    String checkEmployeeRestrictions(int currentEmployees);
}
