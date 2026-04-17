package nicolagraziani.U5_W2_D5.Corporate.travel.management.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TravelDTO(
        @NotBlank(message = "Il destinazione è un campo obbligatorio e non può essere una Stringa vuota")
        @Size(min = 2, message = "La destinazione non può avere meno di 2 caratteri")
        String destination,
        @NotNull(message = "La data è obbligatoria")
//        @Future(message = "Data non valida, non può essere nel passato")
//        Tolto per dare un senso allo stato "completed"
//        Anche se tecnicamente si potrebbero dover registrare solo viaggi futuri e poi cambiare
//        lo stato una volta passata la data del viaggio
        LocalDate travelDate,
        @NotBlank(message = "Lo stato è obbligatorio")
        @Pattern(regexp = "completed|programmed", message = "Lo stato può essere solo 'completed' o 'programmed'")
        String state
) {
}
