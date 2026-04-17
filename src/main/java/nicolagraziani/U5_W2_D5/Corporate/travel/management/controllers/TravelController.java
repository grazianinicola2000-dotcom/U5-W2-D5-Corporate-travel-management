package nicolagraziani.U5_W2_D5.Corporate.travel.management.controllers;

import nicolagraziani.U5_W2_D5.Corporate.travel.management.entities.Travel;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.exceptions.ValidationException;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.payloads.TravelDTO;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.services.TravelService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/travels")
public class TravelController {
    private final TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    //GET ALL
    @GetMapping
    public Page<Travel> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "travelDate") String sortBy) {
        return this.travelService.findAll(page, size, sortBy);
    }

    //    POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Travel saveBlogPost(@RequestBody @Validated TravelDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        return this.travelService.saveTravel(body);
    }

    //    GET BY ID
    @GetMapping("/{travelId}")
    public Travel findById(@PathVariable UUID travelId) {
        return this.travelService.findTravelById(travelId);
    }

    //    PUT
    @PutMapping("/{travelId}")
    public Travel findTravelByIdAndUpdate(@PathVariable UUID travelId, @RequestBody @Validated TravelDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        return this.travelService.findTravelByIdAndUpdate(travelId, body);
    }

    //    DELETE
    @DeleteMapping("/{travelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findTravelByIdAndDelete(@PathVariable UUID travelId) {
        this.travelService.findTravelByIdAndDelete(travelId);
    }
}
