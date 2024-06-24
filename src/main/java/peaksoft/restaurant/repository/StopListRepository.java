package peaksoft.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.dto.response.stopList.StopListResponseRd;
import peaksoft.restaurant.entities.Menuitem;
import peaksoft.restaurant.entities.StopList;
import peaksoft.restaurant.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StopListRepository extends JpaRepository<StopList,Long> {
    @Query("SELECT new peaksoft.restaurant.dto.response.stopList.StopListResponseRd(s.id, s.reason, s.date) " +
            "FROM StopList s " +
            "LEFT JOIN s.menuitem m " +
            "WHERE m.id = ?1")
    List<StopListResponseRd> findStopListEntriesByMenuItemId(Long menuItemId);


    @Query("select new peaksoft.restaurant.dto.response.stopList.StopListResponseRd(s.id,s.reason,s.date) from StopList s")
    List<StopListResponseRd> getStopList();

    StopList findByMenuitemAndDate(Menuitem menuItem, LocalDate date);

    Optional<StopListResponseRd> findByMenuitemId(Long menuitemId);

    List<StopList> findAllByDate(LocalDate date);
}
