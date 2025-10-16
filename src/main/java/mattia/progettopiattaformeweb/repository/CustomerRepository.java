package mattia.progettopiattaformeweb.repository;

import mattia.progettopiattaformeweb.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByLastNameIgnoreCase(String lastName);
}
