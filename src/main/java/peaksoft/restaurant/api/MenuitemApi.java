package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.request.menuitem.MenuitemRequestRd;
import peaksoft.restaurant.dto.response.menuitem.MenuitemResponseRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
public class MenuitemApi {
    private final MenuItemService menuitemService;

    @PostMapping("/add/{restaurantId}")
    public SimpleResponse addMenuItem(@PathVariable Long restaurantId,
                                      @RequestBody MenuitemRequestRd menuitemRequestRd) {
        return menuitemService.addMenuItem(restaurantId, menuitemRequestRd);
    }

    @PutMapping("/update/{id}")
    public SimpleResponse updateMenuItem(@PathVariable Long id,
                                         @RequestBody MenuitemRequestRd menuItemRequest) {
        return menuitemService.updateMenuItem(id, menuItemRequest);
    }

    @DeleteMapping("/delete/{id}")
    public SimpleResponse deleteMenuItem(@PathVariable Long id) {
        return menuitemService.deleteMenuItem(id);
    }

    @GetMapping("/{id}")
    public MenuitemResponseRd findMenuItemById(@PathVariable Long id) {
        return menuitemService.findMenuItemById(id)
                .orElseThrow(() -> new NotFoundException("Menu item with id: " + id + " not found"));
    }

    @GetMapping("/all")
    public List<MenuitemResponseRd> findAllMenuItems() {
        return menuitemService.findAllMenuItems();
    }

    @GetMapping("/search/{keyword}/{isVegetarian}/{sortDirection}")
    public List<MenuitemResponseRd> searchMenuItemsFilteredAndSorted(@PathVariable String keyword,
                                                                     @PathVariable boolean isVegetarian,
                                                                     @PathVariable String sortDirection) {
        return menuitemService.searchMenuItemsFilteredAndSorted(keyword, isVegetarian, sortDirection);
    }

}
