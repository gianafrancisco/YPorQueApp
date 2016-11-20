package yporque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import yporque.model.Articulo;
import yporque.repository.ArticuloRepository;
import yporque.repository.VentaRepository;

/**
 * Created by francisco on 18/12/15.
 */
@RestController
@Component("articuloController")
public class ArticuloController {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @RequestMapping("/articulos")
    public Page<Articulo> obtenerListaArticulos(Pageable pageRequest){
        Page<Articulo> articulos = articuloRepository.findAll(pageRequest);
        articulos.getContent().stream().forEach(
                articulo ->
                        articulo.setUnidadesVendidas(ventaRepository.unidadesVendidas(articulo.getCodigo()))
        );
        return articulos;
    }

    @RequestMapping("/articulo/search")
    public Page<Articulo> filtrarArticulos(@RequestParam(value = "") String search, Pageable pageRequest){
        Page<Articulo> articulos = articuloRepository.findByDescripcionContainingIgnoreCaseOrCodigoContainingIgnoreCase(search, search, pageRequest);
        articulos.getContent().stream().forEach(
                articulo ->
                        articulo.setUnidadesVendidas(ventaRepository.unidadesVendidas(articulo.getCodigo()))
        );
        return articulos;
    }


    @RequestMapping("/articulo/agregar")
    public Articulo agregar(@RequestBody Articulo articulo){
        articulo.setCantidadStock(articulo.getCantidadStock()+articulo.getCantidad());
        return articuloRepository.saveAndFlush(articulo);
    }

    @RequestMapping("/articulo/delete/{articuloId}")
    public void borrarArticulo(@PathVariable Long articuloId){
        articuloRepository.delete(articuloId);
    }

}
