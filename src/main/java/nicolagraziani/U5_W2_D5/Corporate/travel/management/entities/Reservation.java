package nicolagraziani.U5_W2_D5.Corporate.travel.management.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Reservation {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID reservationId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "travel_id")
    private Travel travel;

    @ManyToOne
    @JoinColumn(nullable = false, name = "emplyee_id")
    private Employee employee;

    @Column(nullable = false, name = "request_date")
    private LocalDate requestDate;

    @Column(nullable = false)
    private String notes;

    public Reservation(Travel travel, Employee employee, String notes) {
        this.travel = travel;
        this.employee = employee;
        this.requestDate = LocalDate.now();
        this.notes = notes;
    }
}
