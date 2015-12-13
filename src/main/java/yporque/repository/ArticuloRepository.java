package yporque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import yporque.model.Articulo;

import java.util.Collection;
import java.util.List;

/**
 * Created by francisco on 04/12/2015.
 */
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
    List<Articulo> findByDescripcionStartsWithIgnoreCase(String text);
}
