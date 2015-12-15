package yporque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yporque.model.Venta;

/**
 * Created by francisco on 13/12/2015.
 */
public interface VentaRepository extends JpaRepository<Venta, Long> {

}
