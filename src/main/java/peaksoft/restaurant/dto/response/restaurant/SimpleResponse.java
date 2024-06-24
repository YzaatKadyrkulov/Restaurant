package peaksoft.restaurant.dto.response.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SimpleResponse {
    private HttpStatus httpStatus;
    private String message;
}
