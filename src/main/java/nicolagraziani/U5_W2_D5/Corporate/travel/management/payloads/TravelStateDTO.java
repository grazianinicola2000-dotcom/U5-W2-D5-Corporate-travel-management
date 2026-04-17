package nicolagraziani.U5_W2_D5.Corporate.travel.management.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TravelStateDTO(
        @NotBlank(message = "Lo stato è obbligatorio")
        @Pattern(regexp = "completed|programmed", message = "Lo stato può essere solo 'completed' o 'programmed'")
        String state
) {
}
