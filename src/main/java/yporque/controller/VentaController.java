package yporque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yporque.model.Articulo;
import yporque.model.VentaRequest;
import yporque.repository.ArticuloRepository;

import java.util.List;

/**
 * Created by francisco on 23/12/15.
 */
@RestController
@Component("ventaController")
public class VentaController {

    @Autowired
    private ArticuloRepository articuloRepository;

    @RequestMapping("/venta/confirmar")
    public Double confirmar(@RequestBody List<VentaRequest> ventas){

        ventas.stream().forEach(ventaRequest -> {
            Articulo art = articuloRepository.findOne(ventaRequest.getArticulo().getArticuloId());
            art.setCantidadStock(art.getCantidadStock()-ventaRequest.getCantidad());
            articuloRepository.saveAndFlush(art);
        });

        Double totalVenta = ventas.stream().mapToDouble(ventaRequest -> (Double)(ventaRequest.getCantidad()*ventaRequest.getArticulo().getPrecio())).sum();
        return  totalVenta;
    };

}
