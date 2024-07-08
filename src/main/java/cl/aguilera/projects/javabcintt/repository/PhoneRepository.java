package cl.aguilera.projects.javabcintt.repository;

import cl.aguilera.projects.javabcintt.model.Phone;
import cl.aguilera.projects.javabcintt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, String> {
    List<Phone> findByUser(User user);
}
