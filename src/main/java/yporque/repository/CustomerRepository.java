package yporque.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import yporque.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findByLastNameStartsWithIgnoreCase(String lastName);
}
