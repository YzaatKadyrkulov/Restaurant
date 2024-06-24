package peaksoft.restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.request.cheque.SaveChequeRequestRd;
import peaksoft.restaurant.dto.response.cheque.SaveChequeResponseRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.entities.Cheque;
import peaksoft.restaurant.entities.Menuitem;
import peaksoft.restaurant.entities.Restaurant;
import peaksoft.restaurant.entities.User;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.repository.ChequeRepository;
import peaksoft.restaurant.repository.RestaurantRepository;
import peaksoft.restaurant.repository.UserRepository;
import peaksoft.restaurant.service.ChequeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public SimpleResponse addCheque(Long userId, SaveChequeRequestRd saveChequeRequestRd) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with id %d not found ", userId)));
        Cheque cheque = new Cheque();
        cheque.setPriceAverage((int) saveChequeRequestRd.priceAverage());
        cheque.setLocalDate(saveChequeRequestRd.localDate());
        user.setCheque(cheque);
        cheque.getUsers().add(user);

        chequeRepository.save(cheque);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Cheque with id " + cheque.getId() + " saved successfully")
                .build();
    }

    @Override
    public SimpleResponse updateChequeById(Long id, SaveChequeRequestRd saveChequeRequestRd) {
        Cheque cheque = chequeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Cheque with id %d not found ", id)));

        cheque.setPriceAverage( (int) saveChequeRequestRd.priceAverage());
        cheque.setLocalDate(saveChequeRequestRd.localDate());
        chequeRepository.save(cheque);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Cheque with id " + cheque.getId() + " is updated")
                .build();
    }

    @Override
    public SimpleResponse deleteChequeById(Long id) {
        if (!chequeRepository.existsById(id)) {
            throw new NotFoundException("Cheque with id: " + id + " is not found");
        }

        chequeRepository.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Cheque with id " + id + " was deleted")
                .build();
    }

    @Override
    public Optional<SaveChequeResponseRd> getChequeById(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Cheque with id:%s is not present", id)));
        Restaurant restaurant = restaurantRepository.findById(1L).orElseThrow(() -> new NotFoundException(String.format("Restaurant with id:%s is not present", 1L)));
        List<Menuitem> items =  cheque.getMenuItems();
        List<String> collect = items.stream().map(Menuitem::getName).toList();
        return Optional.ofNullable(SaveChequeResponseRd.builder()
                .id(cheque.getId())
                .waiterFullName(cheque.getUsers().getFirst().getFirstName())
                .items(collect)
                .service(restaurant.getService())
                .priceAverage(cheque.getPriceAverage())
                .grandTotal((cheque.getPriceAverage() * restaurant.getService() / 100) + cheque.getPriceAverage())
                .build());
    }

    public double getTotalAmountForWaiterOnDate(Long waiterId, LocalDate date) {
        List<Cheque> cheques = chequeRepository.findByWaiterAndDate(waiterId, date);
        return cheques.stream()
                .flatMap(cheque -> cheque.getMenuItems().stream())
                .mapToDouble(Menuitem::getPrice)
                .sum();
    }

    public double getDailyAverageChequeAmount(LocalDate date) {
        List<Cheque> cheques = chequeRepository.findByDate(date);
        double totalAmount = cheques.stream()
                .flatMap(cheque -> cheque.getMenuItems().stream())
                .mapToDouble(Menuitem::getPrice)
                .sum();
        return cheques.isEmpty() ? 0 : totalAmount / cheques.size();
    }
}
