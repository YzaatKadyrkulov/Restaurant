package peaksoft.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.dto.response.cheque.SaveChequeResponseRd;
import peaksoft.restaurant.entities.Cheque;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {

    @Query("SELECT c FROM Cheque c JOIN c.users u WHERE u.id = :waiterId AND u.role = 'WAITER' AND c.localDate = :localDate")
    List<Cheque> findByWaiterAndDate(@Param("waiterId") Long waiterId, @Param("localDate") LocalDate localDate);

    @Query("SELECT c FROM Cheque c WHERE c.localDate = :localDate")
    List<Cheque> findByDate(@Param("localDate") LocalDate localDate);
}