package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.request.stopList.StopListRequestRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.dto.response.stopList.StopListResponseRd;
import peaksoft.restaurant.service.StopListService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stopList")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
public class StopListApi {
    private final StopListService stopListService;

    @PostMapping("/add/{menuId}")
    public SimpleResponse addStopList(@PathVariable Long menuId,
                                      @RequestBody StopListRequestRd stopListRequestRd) {
        return stopListService.addStopList(menuId, stopListRequestRd);
    }

    @PutMapping("/update/{id}")
    public SimpleResponse updateStopList(@PathVariable Long id,
                                         @RequestBody StopListRequestRd stopListRequestRd) {
        return stopListService.updateStopList(id, stopListRequestRd);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteStopList(@PathVariable Long id) {
        return stopListService.deleteStopList(id);
    }

    @GetMapping("/{id}")
    public Optional<StopListResponseRd> findByMenuitemId(@PathVariable Long id) {
        return stopListService.findByMenuitemId(id);
    }

    @GetMapping("/getAll")
    public List<StopListResponseRd> getStopList() {
        return stopListService.getStopList();
    }
}
