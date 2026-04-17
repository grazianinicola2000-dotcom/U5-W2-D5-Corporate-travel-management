package nicolagraziani.U5_W2_D5.Corporate.travel.management.repositories;

import nicolagraziani.U5_W2_D5.Corporate.travel.management.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmplyeeRepository extends JpaRepository<Employee, UUID> {
    boolean existsByEmail(String email);
}
