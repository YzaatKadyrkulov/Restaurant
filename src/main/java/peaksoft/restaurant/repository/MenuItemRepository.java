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


    @Query("select new peaksoft.restaurant.dto.response.menuitem.MenuitemResponseRd(m.id, m.name, m.image, m.description, m.price, m.isVegetarian) from Menuitem m ")
    Optional<MenuitemResponseRd>findMenuItemById(Long id);

    @Query("select new peaksoft.restaurant.dto.response.menuitem.MenuitemResponseRd(m.id, m.name, m.image, m.description, m.price, m.isVegetarian) from Menuitem m ")
    List<MenuitemResponseRd> findAllMenuItems();

    // Баасына жараша сортировка болсун(кымбаттан арзанга, арзандан кымбатка)
    @Query("select new peaksoft.restaurant.dto.response.menuitem.MenuitemResponseRd(" +
            "m.id, " +
            "m.name, " +
            "m.image, " +
            "m.description, " +
            "m.price, " +
            "m.isVegetarian" +
            ") from Menuitem m order by " +
            "case when :ascDesc = 'asc' then m.price end asc, " +
            "case when :ascDesc = 'desc' then m.price end desc")
    List<MenuitemResponseRd> sortByPrice(@Param("ascDesc") String ascDesc);

// IsVegeterian полеси аркылуу фильтрация болсун

    @Query("select new peaksoft.restaurant.dto.response.menuitem.MenuitemResponseRd(" +
            "m.id, " +
            "m.name, " +
            "m.image, " +
            "m.description, " +
            "m.price, " +
            "m.isVegetarian" +
            ")  from Menuitem m where m.isVegetarian = :isVegetarian")
    List<MenuitemResponseRd> searchMenuItems(boolean isVegetarian);


    // Global search (bir tamgany jazganda oshol tamgalar bar
// tamaktar chyksyn, catgeroiya jana sabkategoriyaga da tiyesheluu)
    @Query("select distinct new peaksoft.restaurant.dto.response.menuitem.MenuitemResponseRd(" +
            "m.id, " +
            "m.name, " +
            "m.image, " +
            "m.description, " +
            "m.price, " +
            "m.isVegetarian " +
            ") from Menuitem m " +
            "left join m.subcategories sb " +
            "left join sb.category c " +
            "where lower(sb.name) like lower(concat('%', :word, '%')) " +
            "or lower(m.name) like lower(concat('%', :word, '%')) " +
            "or lower(c.name) like lower(concat('%', :word, '%')) ")
    List<MenuitemResponseRd> globalSearch(String word);
}
