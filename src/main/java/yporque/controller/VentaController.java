package yporque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yporque.model.Articulo;
import yporque.model.Venta;
import yporque.model.VentaRequest;
import yporque.repository.ArticuloRepository;
import yporque.repository.VentaRepository;
import yporque.utils.VentaFunction;

import java.time.Instant;
import java.util.List;

/**
 * Created by francisco on 23/12/15.
 */
@RestController
@Component("ventaController")
public class VentaController {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentaFunction ventaFunction;

    @RequestMapping("/venta/confirmar")
    public Double confirmar(@RequestBody List<VentaRequest> ventas){

        Instant fecha = Instant.now();

        ventas.stream().forEach(ventaRequest -> {
            Articulo art = articuloRepository.findOne(ventaRequest.getArticulo().getArticuloId());
            art.setCantidadStock(art.getCantidadStock()-ventaRequest.getCantidad());
            articuloRepository.saveAndFlush(art);
            ventaRepository.save(ventaFunction.apply(fecha,ventaRequest));
        });

        return ventas.stream().mapToDouble(ventaRequest -> ventaRequest.getCantidad()*ventaRequest.getArticulo().getPrecio()).sum();

    }


    @RequestMapping("/ventas")
    public Page<Venta> obtenerListado(Pageable pageRequest){
        return  ventaRepository.findAll(pageRequest);
    }

}
