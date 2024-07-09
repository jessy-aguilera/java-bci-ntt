package cl.aguilera.projects.javabcintt.repository;

import cl.aguilera.projects.javabcintt.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, String> {
}
