package nicolagraziani.U5_W2_D5.Corporate.travel.management.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ReservationDTO(
        @NotNull(message = "L'id del dipendente è obbligatorio")
        UUID employeeId,
        @NotNull(message = "L'id del viaggio è obbligatorio")
        UUID travelId,
        @Size(max = 300, message = "Le note non possono superare i 300 caratteri")
        String notes
) {
}
