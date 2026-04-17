package nicolagraziani.U5_W2_D5.Corporate.travel.management.payloads;

import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime timestamp) {
}
