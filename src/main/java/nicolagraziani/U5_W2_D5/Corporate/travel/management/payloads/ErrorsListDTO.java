package nicolagraziani.U5_W2_D5.Corporate.travel.management.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsListDTO(String message, LocalDateTime timestamp, List<String> errors) {
}
