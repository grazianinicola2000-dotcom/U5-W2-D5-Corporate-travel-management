package nicolagraziani.U5_W2_D5.Corporate.travel.management.services;

import lombok.extern.slf4j.Slf4j;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.entities.Employee;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.entities.Reservation;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.entities.Travel;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.exceptions.NotFoundException;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.exceptions.ValidationException;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.payloads.ReservationDTO;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.repositories.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TravelService travelService;
    private final EmployeeService employeeService;

    public ReservationService(ReservationRepository reservationRepository, TravelService travelService, EmployeeService employeeService) {
        this.reservationRepository = reservationRepository;
        this.travelService = travelService;
        this.employeeService = employeeService;
    }

    //    SAVE
    public Reservation saveReservation(ReservationDTO body) {
        Travel travel = this.travelService.findTravelById(body.travelId());
        Employee employee = this.employeeService.findEmployeeById(body.employeeId());
//        CONTROLLO PER VERIFICARE CHE UN DIPENDENTE NON ABBIA VIAGGI IN PROGRAMMA LO STESSO GIORNO
        if (this.reservationRepository.existsByEmployeeAndTravel_TravelDate(employee, travel.getTravelDate())) {
            throw new ValidationException("Impossibile prenotare il viaggio, Il dipendente " + employee.getSurname() + " ha gia un viaggio prenotato per la data " + travel.getTravelDate());
        }
        Reservation newReservation = new Reservation(travel, employee, body.notes());
        this.reservationRepository.save(newReservation);
        log.info("La prenotazione è stata effetutata con successo");
        return newReservation;
    }

    //    FIND ALL
    public Page<Reservation> findAll(int page, int size, String sortBy) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.reservationRepository.findAll(pageable);
    }

    //    FIND BY ID
    public Reservation findReservationById(UUID reservationId) {
        return this.reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException(reservationId));
    }

    //    FIND BY ID AND UPDATE
    public Reservation findReservationByIdAndUpdate(UUID reservationId, ReservationDTO body) {
        Reservation found = this.findReservationById(reservationId);
        Travel travel = this.travelService.findTravelById(body.travelId());
        Employee employee = this.employeeService.findEmployeeById(body.employeeId());
        found.setEmployee(employee);
        found.setTravel(travel);
        found.setNotes(body.notes());

        Reservation saved = this.reservationRepository.save(found);
        log.info("La prenotazione è stata aggiornata con successo");
        return saved;
    }

    //    DELETE
    public void findReservationByIdAndDelete(UUID reservationId) {
        Reservation found = this.findReservationById(reservationId);
        this.reservationRepository.delete(found);
        log.info("La prenotazioen con id {} è stata eliminata con successo", found.getReservationId());
    }
}
