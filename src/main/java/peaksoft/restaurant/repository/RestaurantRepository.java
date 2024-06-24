package peaksoft.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.dto.response.RestaurantResponseRd;
import peaksoft.restaurant.entities.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    boolean existsByName(String name);

    Optional<Restaurant> findByName(String name);

    Optional<RestaurantResponseRd> getRestaurantById(Long id);

    @Query("select new peaksoft.restaurant.dto.response.RestaurantResponseRd(r.id,r.name,r.location,r.restType,r.numberOfEmployees,r.service) from Restaurant r")
    List<RestaurantResponseRd> findAllRestaurants();
}
