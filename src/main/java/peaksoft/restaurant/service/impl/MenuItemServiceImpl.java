package peaksoft.restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.request.menuitem.MenuitemRequestRd;
import peaksoft.restaurant.dto.response.menuitem.MenuitemResponseRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.entities.Menuitem;
import peaksoft.restaurant.entities.Restaurant;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.repository.MenuItemRepository;
import peaksoft.restaurant.repository.RestaurantRepository;
import peaksoft.restaurant.service.MenuItemService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public SimpleResponse addMenuItem(Long restaurantId, MenuitemRequestRd menuItemRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant with id: " + restaurantId + " not found"));

        Menuitem menuItem = Menuitem.builder()
                .name(menuItemRequest.name())
                .image(menuItemRequest.image())
                .price(menuItemRequest.price())
                .description(menuItemRequest.description())
                .isVegetarian(menuItemRequest.isVegetarian())
                .build();

        restaurant.getMenuItems().add(menuItem);
        menuItem.setRestaurant(restaurant);

        menuItemRepository.save(menuItem);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Menu item successfully added to restaurant with id: " + restaurantId)
                .build();
    }

    @Override
    public SimpleResponse updateMenuItem(Long id, MenuitemRequestRd menuitemRequestRd) {
        Menuitem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Menu item with id: " + id + " not found"));

        menuItem.setName(menuitemRequestRd.name());
        menuItem.setImage(menuitemRequestRd.image());
        menuItem.setPrice(menuitemRequestRd.price());
        menuItem.setDescription(menuitemRequestRd.description());
        menuItem.setVegetarian(menuItem.isVegetarian());

        menuItemRepository.save(menuItem);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Menu item with id: " + id + " is updated")
                .build();

    }

    @Override
    public SimpleResponse deleteMenuItem(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new NotFoundException("Menu item with id: " + id + " is not found");
        }

        menuItemRepository.deleteById(id);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Menu item with id " + id + " was deleted")
                .build();
    }

    @Override
    public Optional<MenuitemResponseRd> findMenuItemById(Long id) {
        return Optional.ofNullable(menuItemRepository.findMenuItemById(id)
                .orElseThrow(() -> new NotFoundException("Menu item with id: " + id + " not found")));


    }

    @Override
    public List<MenuitemResponseRd> findAllMenuItems() {
        return menuItemRepository.findAllMenuItems();
    }

    @Override
    public List<MenuitemResponseRd> sortByPrice(String ascDesc) {
        return menuItemRepository.sortByPrice(ascDesc);
    }

    @Override
    public List<MenuitemResponseRd> searchMenuItems(boolean isVegetarian) {
        return menuItemRepository.searchMenuItems(isVegetarian);
    }

    @Override
    public List<MenuitemResponseRd> globalSearch(String word) {
        return menuItemRepository.globalSearch(word);
    }
}
