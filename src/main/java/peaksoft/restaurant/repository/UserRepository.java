package peaksoft.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.dto.response.user.SignUpResponseRd;
import peaksoft.restaurant.entities.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> getUserByEmail(String email);
    boolean existsByEmail(String email);

    Optional<SignUpResponseRd> getUserById(Long id);

    @Query("select new peaksoft.restaurant.dto.response.user.SignUpResponseRd(" +
            "u.id," +
            "u.firstName," +
            "u.lastName," +
            "u.dateOfBirth," +
            "u.email," +
            "u.password," +
            "u.phoneNumber," +
            "u.role," +
            "u.experience) from User u")
    List<SignUpResponseRd> findAllUsers();
}
