package nicolagraziani.U5_W2_D5.Corporate.travel.management.repositories;

import nicolagraziani.U5_W2_D5.Corporate.travel.management.entities.Employee;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    boolean existsByEmployeeAndTravel_TravelDate(Employee employee, LocalDate travelDate);
}
