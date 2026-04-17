package nicolagraziani.U5_W2_D5.Corporate.travel.management.services;

import lombok.extern.slf4j.Slf4j;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.entities.Travel;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.enums.TravelState;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.exceptions.NotFoundException;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.exceptions.ValidationException;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.payloads.TravelDTO;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.repositories.TravelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TravelService {
    private final TravelRepository travelRepository;

    public TravelService(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    //  FIND ALL
    public Page<Travel> findAll(int page, int size, String sortBy) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.travelRepository.findAll(pageable);
    }

    //    SAVE NEW
    public Travel saveTravel(TravelDTO body) {
        String state = body.state();
        TravelState stateEnum = null;
        switch (state.toLowerCase()) {
            case "programmed" -> stateEnum = TravelState.PROGRAMMED;
            case "completed" -> stateEnum = TravelState.COMPLETED;
            default -> throw new ValidationException("Valore stato non accettabile");
        }
        Travel travel = new Travel(body.destination(), body.travelDate(), stateEnum);
        this.travelRepository.save(travel);
        log.info("Il viaggio a {} nella data {} è stato salvato correttamente", body.destination(), body.travelDate());
        return travel;
    }

    //  FIND BY ID
    public Travel findTravelById(UUID travelid) {
        return this.travelRepository.findById(travelid).orElseThrow(() -> new NotFoundException(travelid));
    }

    //    FIND BY ID AND UPDATE
    public Travel findTravelByIdAndUpdate(UUID travelId, TravelDTO body) {
        String state = body.state();
        TravelState stateEnum = null;
        switch (state.toLowerCase()) {
            case "programmed" -> stateEnum = TravelState.PROGRAMMED;
            case "completed" -> stateEnum = TravelState.COMPLETED;
            default -> throw new ValidationException("Valore stato non accettabile");
        }
        Travel found = this.findTravelById(travelId);
        found.setTravelDate(body.travelDate());
        found.setDestination(body.destination());
        found.setState(stateEnum);
        Travel saved = this.travelRepository.save(found);
        log.info("Il viaggio a {} nella data {} è stato aggiornato correttamente", saved.getDestination(), saved.getTravelDate());
        return saved;
    }

    //  DELETE
    public void findTravelByIdAndDelete(UUID travelId) {
        Travel found = this.findTravelById(travelId);
        this.travelRepository.delete(found);
        log.info("Il viaggio a {} nella data {} è stato eliminato correttamente", found.getDestination(), found.getTravelDate());
    }
}
