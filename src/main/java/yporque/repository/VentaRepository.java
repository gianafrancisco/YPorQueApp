package yporque.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yporque.model.Venta;

import java.time.Instant;

/**
 * Created by francisco on 13/12/2015.
 */
public interface VentaRepository extends JpaRepository<Venta, Long> {
    Page<Venta> findByFechaBetween(Instant startTime, Instant stopTime, Pageable pageable);
    Page<Venta> findByUsernameIgnoreCaseContaining(String username, Pageable pageable);
}
