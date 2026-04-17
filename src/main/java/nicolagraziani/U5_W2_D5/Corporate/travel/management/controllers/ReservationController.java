package nicolagraziani.U5_W2_D5.Corporate.travel.management.controllers;

import nicolagraziani.U5_W2_D5.Corporate.travel.management.entities.Reservation;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.exceptions.ValidationException;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.payloads.ReservationDTO;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.services.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //    GET ALL
    @GetMapping
    public Page<Reservation> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size,
                                     @RequestParam(defaultValue = "requestDate") String sortBy) {
        return this.reservationService.findAll(page, size, sortBy);
    }

    //    POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Reservation saveReservation(@RequestBody @Validated ReservationDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        return this.reservationService.saveReservation(body);
    }

    //    GET BY ID
    @GetMapping("/{reservationId}")
    public Reservation findById(@PathVariable UUID reservationId) {
        return this.reservationService.findReservationById(reservationId);
    }

    //    PUT
    @PutMapping("/{reservationId}")
    public Reservation findReservationByIdAndUpdate(@PathVariable UUID reservationId, @RequestBody @Validated ReservationDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        return this.reservationService.findReservationByIdAndUpdate(reservationId, body);
    }

    //    DELETE
    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findReservationByIdAndDelete(@PathVariable UUID reservationId) {
        this.reservationService.findReservationByIdAndDelete(reservationId);
    }
}
