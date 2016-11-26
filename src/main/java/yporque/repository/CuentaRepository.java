package yporque.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yporque.model.Cuenta;

/**
 * Created by francisco on 19/11/2016.
 */
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Cuenta findByDni(long dni);
    Page<Cuenta> findByDni(long dni, Pageable pageable);
    @Query(value = "SELECT i FROM Cuenta i WHERE ( i.nombre LIKE %?1% OR i.apellido LIKE %?1% OR i.email LIKE %?1% OR i.telefono LIKE %?1% OR i.dni LIKE %?1% )",
            countQuery = "SELECT count(i) FROM Cuenta i WHERE ( i.nombre LIKE %?1% OR i.apellido LIKE %?1% OR i.email LIKE %?1% OR i.telefono LIKE %?1% OR i.dni LIKE %?1% ) ")
    Page<Cuenta> search(String search, Pageable pageable);
}
