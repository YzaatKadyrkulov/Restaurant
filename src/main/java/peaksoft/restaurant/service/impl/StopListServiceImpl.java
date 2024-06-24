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

@Service
@RequiredArgsConstructor
@Transactional
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    @Transactional // Обернем метод в транзакцию для атомарности операций
    public SimpleResponse addStopList(Long menuId, StopListRequestRd stopListRequestRd) {
        Menuitem menuItem = menuItemRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu item with id: " + menuId + " not found"));

        // Получаем все доступные ед
        List<Menuitem> allMenuItems = menuItemRepository.findAll();

        // Получаем все записи StopList на указанную дату
        List<StopList> existingStopListEntries = stopListRepository.findAllByDate(stopListRequestRd.date());

        // Создаем список отсутствующих ед
        List<Menuitem> missingMenuItems = new ArrayList<>();

        // Проверяем каждую доступную ед на отсутствие в StopList на указанную дату
        for (Menuitem item : allMenuItems) {
            boolean foundInStopList = false; // был ли найден текущий элемент в меню existingStopListEntries
            for (StopList stopList : existingStopListEntries) {   //по списку existingStopListEntries, который, предположительно, содержит записи о запретах на определенную дату.
                if (stopList.getMenuitem().getId().equals(item.getId())) {
                    foundInStopList = true;
                    break;
                }
            }
            if (!foundInStopList) {
                missingMenuItems.add(item);
            }
        }

        // Создаем записи в StopList для отсутствующих ед
        for (Menuitem missingItem : missingMenuItems) {
            // Проверяем, не существует ли уже запись StopList для данной еды на указанную дату
            StopList existingStopListEntry = stopListRepository.findByMenuitemAndDate(missingItem, stopListRequestRd.date());

            if (existingStopListEntry == null) {
                // Если запись не существует, создаем новую запись в StopList
                StopList stopListEntry = new StopList();
                stopListEntry.setMenuitem(missingItem);
                stopListEntry.setReason(stopListRequestRd.reason());
                stopListEntry.setDate(stopListRequestRd.date());
                menuItem.setStopList(stopListEntry);
                stopListEntry.setMenuitem(menuItem);
                stopListRepository.save(stopListEntry);
            } else {
                // Если запись уже существует, можно выбросить исключение или выполнить другую обработку
                throw new IllegalArgumentException("Stop list entry for menu item with id " + missingItem.getId() + " and date " + stopListRequestRd.date() + " already exists.");
            }
        }

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Stop list entries successfully added for missing menu items on date: " + stopListRequestRd.date())
                .build();
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
    public Optional<StopListResponseRd> findByMenuitemId(Long menuitemId) {
        return Optional.ofNullable(stopListRepository.findByMenuitemId(menuitemId)
                .orElseThrow(() -> new NotFoundException("Stop list entry with id: " + menuitemId + " not found")));

    }
}
