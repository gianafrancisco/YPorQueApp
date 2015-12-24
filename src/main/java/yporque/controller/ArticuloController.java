package yporque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import yporque.model.Articulo;
import yporque.model.Item;
import yporque.repository.ArticuloRepository;
import yporque.repository.ItemRepository;

import java.util.List;

/**
 * Created by francisco on 18/12/15.
 */
@RestController
@Component("articuloController")
public class ArticuloController {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping("/articulos")
    public Page<Articulo> obtenerListaArticulos(Pageable pageRequest){
        return articuloRepository.findAll(pageRequest);
    }

    @RequestMapping("/articulo/search")
    public Page<Articulo> filtrarArticulos(@RequestParam(value = "") String search, Pageable pageRequest){
        return articuloRepository.findByDescripcionContainingIgnoreCaseOrCodigoContainingIgnoreCase(search, search, pageRequest);
    }


    @RequestMapping("/articulo/agregar")
    public Articulo agregar(@RequestBody Articulo articulo){
        return articuloRepository.saveAndFlush(articulo);
    }

    /*
    @RequestMapping("/articulo/{articuloId}/agregar")
    public Item agregarItem(@PathVariable Long articuloId, @RequestBody Item item){
        item.setArticuloId(articuloId);
        return itemRepository.saveAndFlush(item);
    }

    @RequestMapping("/articulo/{articuloId}/items")
    public Page<Item> obtenerListaItem(@PathVariable Long articuloId, Pageable pageRequest){
        return itemRepository.findByArticuloId(articuloId,pageRequest);
    }

    @RequestMapping("/articulo/{articuloId}/item/delete/{itemId}")
    public void borrarItem(@PathVariable Long itemId){
        itemRepository.delete(itemId);
    };
    */
    @RequestMapping("/articulo/delete/{articuloId}")
    public void borrarArticulo(@PathVariable Long articuloId){
        List<Item> items = itemRepository.findByArticuloId(articuloId);
        items.stream().forEach(item -> itemRepository.delete(item.getItemId()));
        articuloRepository.delete(articuloId);
    }

}
