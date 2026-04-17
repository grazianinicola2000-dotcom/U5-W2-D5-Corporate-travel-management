package nicolagraziani.U5_W2_D5.Corporate.travel.management.repositories;

import nicolagraziani.U5_W2_D5.Corporate.travel.management.entities.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TravelRepository extends JpaRepository<Travel, UUID> {
}
