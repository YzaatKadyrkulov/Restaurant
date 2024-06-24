package peaksoft.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.dto.response.menuitem.MenuitemResponseRd;
import peaksoft.restaurant.entities.Menuitem;


import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<Menuitem,Long> {


    @Query("select new peaksoft.restaurant.dto.response.menuitem.MenuitemResponseRd(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) from Menuitem m ")
    Optional<MenuitemResponseRd>findMenuItemById(Long id);

    @Query("select new peaksoft.restaurant.dto.response.menuitem.MenuitemResponseRd(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) from Menuitem m ")
    List<MenuitemResponseRd> findAllMenuItems();

    @Query("select new peaksoft.restaurant.dto.response.menuitem.MenuitemResponseRd(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) " +
            "from Menuitem m " +
            "left join m.subcategories sub " +
            "where (m.name like %:keyword% " +
            "or sub.name like %:keyword%) " +
            "and m.isVegetarian = :isVegetarian " +
            "order by " +
            "case when :sortDirection = 'DESC' then m.price end desc, " +
            "case when :sortDirection = 'ASC' then m.price end asc")
    List<MenuitemResponseRd> searchMenuItemsFilteredAndSorted(@Param("keyword") String keyword,
                                                              @Param("isVegetarian") boolean isVegetarian,
                                                              @Param("sortDirection") String sortDirection);

}
