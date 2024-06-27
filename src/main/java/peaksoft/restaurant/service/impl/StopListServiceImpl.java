package peaksoft.restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.request.stopList.StopListRequestRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.dto.response.stopList.StopListResponseRd;
import peaksoft.restaurant.entities.Menuitem;
import peaksoft.restaurant.entities.StopList;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.repository.MenuItemRepository;
import peaksoft.restaurant.repository.StopListRepository;
import peaksoft.restaurant.service.StopListService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public SimpleResponse addStopList(Long menuId, StopListRequestRd stopListRequestRd) {
        Menuitem menuItem = menuItemRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu item with id: " + menuId + " not found"));

        // Проверяем, не существует ли уже запись StopList для данной еды на указанную дату
        StopList existingStopListEntry = stopListRepository.findByMenuItemAndDate(menuItem, stopListRequestRd.date());

        if (existingStopListEntry != null) {
            throw new IllegalArgumentException("Stop list entry for menu item with id " + menuItem.getId() + " and date " + stopListRequestRd.date() + " already exists.");
        }

        // Создаем новую запись в StopList
        StopList stopListEntry = new StopList();
        stopListEntry.setMenuItem(menuItem);
        stopListEntry.setReason(stopListRequestRd.reason());
        stopListEntry.setDate(stopListRequestRd.date());

        stopListRepository.save(stopListEntry);

        // Получаем все доступные блюда
        List<Menuitem> allMenuItems = menuItemRepository.findAll();

        // Получаем все записи StopList на указанную дату
        List<StopList> existingStopListEntries = stopListRepository.findAllByDate(stopListRequestRd.date());

        // Создаем список отсутствующих блюд
        List<Menuitem> missingMenuItems = new ArrayList<>();

        // Проверяем каждое доступное блюдо на отсутствие в StopList на указанную дату
        for (Menuitem item : allMenuItems) {
            boolean foundInStopList = false;
            for (StopList stopList : existingStopListEntries) {
                if (stopList.getMenuItem().getId().equals(item.getId())) {
                    foundInStopList = true;
                    break;
                }
            }
            if (!foundInStopList) {
                missingMenuItems.add(item);
            }
        }

        // Формируем сообщение с отсутствующими блюдами
        String missingMenuItemsMessage = missingMenuItems.stream()
                .map(Menuitem::getName)
                .collect(Collectors.joining(", "));

        String responseMessage = "Stop list entry successfully added for menu item with id " + menuItem.getId();
        if (!missingMenuItems.isEmpty()) {
            responseMessage += ". Missing menu items: " + missingMenuItemsMessage;
        }

        return new SimpleResponse(
                HttpStatus.CREATED,
                responseMessage
        );
    }







    @Override
    public SimpleResponse updateStopList(Long id, StopListRequestRd stopListRequestRd) {
        StopList stopListEntry = stopListRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Stop list entry with id: " + id + " not found"));

        stopListEntry.setReason(stopListRequestRd.reason());
        stopListEntry.setDate(stopListRequestRd.date());

        stopListRepository.save(stopListEntry);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Stop list entry with id: " + id + " is updated")
                .build();
    }

    @Override
    public SimpleResponse deleteStopList(Long id) {
        StopList stopListEntry = stopListRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Stop list entry with id: " + id + " not found"));

        stopListRepository.delete(stopListEntry);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Stop list entry with id: " + id + " is deleted")
                .build();
    }

    @Override
    public List<StopListResponseRd> getStopList() {
        return stopListRepository.getStopList();
    }


    @Override
    public Optional<StopListResponseRd> findByMenuItemId(Long menuitemId) {
        return Optional.ofNullable(stopListRepository.findByMenuItemId(menuitemId)
                .orElseThrow(() -> new NotFoundException("Stop list entry with id: " + menuitemId + " not found")));

    }
}
