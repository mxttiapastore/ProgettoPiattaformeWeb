package mattia.progettopiattaformeweb.service;

import mattia.progettopiattaformeweb.model.Customer;
import mattia.progettopiattaformeweb.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) { this.repo = repo; }

    @Transactional
    public Customer create(Customer c) { return repo.save(c); }

    @Transactional(readOnly = true)
    public List<Customer> all() { return repo.findAll(); }
}
