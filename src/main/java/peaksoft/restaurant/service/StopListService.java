package peaksoft.restaurant.service;

import peaksoft.restaurant.dto.request.stopList.StopListRequestRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.dto.response.stopList.StopListResponseRd;

import java.util.List;
import java.util.Optional;

public interface StopListService {
    SimpleResponse addStopList(Long menuId, StopListRequestRd stopListRequestRd);

    SimpleResponse updateStopList(Long id, StopListRequestRd stopListRequestRd);

    SimpleResponse deleteStopList(Long id);

    List<StopListResponseRd> getStopList();

    Optional<StopListResponseRd> findByMenuitemId(Long menuitemId);
}
