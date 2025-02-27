package lt.techin.travel_agency.repository;

import lt.techin.travel_agency.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Byte> {
  Role findByName(String name);

}
