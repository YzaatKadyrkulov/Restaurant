package peaksoft.restaurant.service;

import peaksoft.restaurant.dto.request.cheque.SaveChequeRequestRd;
import peaksoft.restaurant.dto.response.cheque.SaveChequeResponseRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.entities.Cheque;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChequeService {
    SimpleResponse addCheque(Long userId, SaveChequeRequestRd saveChequeRequestRd);

    SimpleResponse updateChequeById(Long id, SaveChequeRequestRd saveChequeRequestRd);

    SimpleResponse deleteChequeById(Long id);

    Optional<SaveChequeResponseRd> getChequeById(Long id);

    double getTotalAmountForWaiterOnDate(Long waiterId, LocalDate date);

    double getDailyAverageChequeAmount(LocalDate date);
}
