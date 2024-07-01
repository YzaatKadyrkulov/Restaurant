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
    @Query("select new peaksoft.restaurant.dto.response.stopList.StopListResponseRd(s.id,s.reason,s.date) from StopList s")
    List<StopListResponseRd> getStopList();

    StopList findByMenuItemAndDate(Menuitem menuItem, LocalDate date);

    Optional<StopListResponseRd> findByMenuItemId(Long menuitemId);

    List<StopList> findAllByDate(LocalDate date);
}
