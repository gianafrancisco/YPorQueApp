package yporque;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by fgiana on 04/11/2015.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCodeStartsWithIgnoreCase(String code);
}
