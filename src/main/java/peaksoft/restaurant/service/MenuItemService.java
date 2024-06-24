package peaksoft.restaurant.service;


import peaksoft.restaurant.dto.request.menuitem.MenuitemRequestRd;
import peaksoft.restaurant.dto.response.menuitem.MenuitemResponseRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {
    SimpleResponse addMenuItem(Long restaurantId, MenuitemRequestRd menuItem);

    SimpleResponse updateMenuItem(Long id,MenuitemRequestRd menuItem);

    SimpleResponse deleteMenuItem(Long id);

    Optional<MenuitemResponseRd> findMenuItemById(Long id);

    List<MenuitemResponseRd> findAllMenuItems();

        List<MenuitemResponseRd> searchMenuItemsFilteredAndSorted(String keyword,boolean isVegetarian,String sortDirection);
}
