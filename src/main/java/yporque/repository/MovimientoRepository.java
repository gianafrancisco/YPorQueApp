package yporque.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import yporque.model.Movimiento;

/**
 * Created by francisco on 11/22/16.
 */
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    Page<Movimiento> findByCuentaId(Long cuentaId, Pageable pageRequest);
    @Query(value = "SELECT SUM(importe) FROM Movimiento WHERE cuentaId = ?1")
    Double saldo(Long cuentaId);
}
