package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.NotFound;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.request.cheque.SaveChequeRequestRd;
import peaksoft.restaurant.dto.response.cheque.SaveChequeResponseRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.service.ChequeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cheque")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
public class ChequeApi {

    private final ChequeService chequeService;

    @PostMapping("/add/{userId}")
    public SimpleResponse addCheque(@PathVariable Long userId,
                                    @RequestBody SaveChequeRequestRd saveChequeRequestRd) {
        return chequeService.addCheque(userId, saveChequeRequestRd);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateChequeById(@PathVariable Long id,
                                           @RequestBody SaveChequeRequestRd saveChequeRequestRd) {
        return chequeService.updateChequeById(id, saveChequeRequestRd);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteChequeById(@PathVariable Long id) {
        return chequeService.deleteChequeById(id);
    }

    @GetMapping("/{id}")
    public Optional<SaveChequeResponseRd> getChequeById(@PathVariable Long id) {
        return chequeService.getChequeById(id);
    }

    @GetMapping("/getTotalAmountForWaiterOnDate/{waiterId}/{date}")
    public double getTotalAmountForWaiterOnDate(@PathVariable Long waiterId,
                                                @PathVariable LocalDate date) {
        return chequeService.getTotalAmountForWaiterOnDate(waiterId, date);
    }

    @GetMapping("/getDailyAverageChequeAmount/{date}")
    public double getTotalAmountForWaiterOnDate(@PathVariable LocalDate date) {
        return chequeService.getDailyAverageChequeAmount(date);
    }
}
