package yporque.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import yporque.model.Articulo;
import yporque.model.Item;

import java.util.List;

/**
 * Created by francisco on 04/12/2015.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCodigo(String codigo);
    Page<Item> findByArticuloId(Long articuloId, Pageable pageable);
    List<Item> findByArticuloId(Long articuloId);
}
