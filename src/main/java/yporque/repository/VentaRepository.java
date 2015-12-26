package yporque.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yporque.model.Venta;

import java.time.Instant;

/**
 * Created by francisco on 13/12/2015.
 */
public interface VentaRepository extends JpaRepository<Venta, Long> {
    Page<Venta> findByFechaBetween(Instant startTime, Instant stopTime, Pageable pageable);
    Page<Venta> findByUsernameIgnoreCaseContainingOrCodigoIgnoreCaseContainingOrDescripcionIgnoreCaseContaining(String username, String codigo, String descripcion, Pageable pageable);

    @Query("from Venta v where v.fecha between ?1 and ?2 " +
            "and ( UPPER(username) like CONCAT('%',UPPER(?3),'%') or UPPER(codigo) like CONCAT('%',UPPER(?3),'%') or UPPER(descripcion) like CONCAT('%',UPPER(?3),'%')" +
            " )")
    Page<Venta> filtrar(Instant startTime, Instant endTime, String search, Pageable pageable);

}
