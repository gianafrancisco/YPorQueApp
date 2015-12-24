package yporque.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import yporque.config.MemoryDBConfig;
import yporque.model.*;
import yporque.repository.ArticuloRepository;
import yporque.repository.VendedorRepository;
import yporque.repository.VentaRepository;
import yporque.utils.VentaFunction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

/**
 * Created by francisco on 18/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("yporque")
@ContextConfiguration(classes = {MemoryDBConfig.class,VentaController.class, VentaFunction.class})
public class VentaControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentaController ventaController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(ventaController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @After
    public void tearDown() throws Exception {
        articuloRepository.deleteAll();
        ventaRepository.deleteAll();
    }

    @Test
    public void test_confirmar_venta() throws Exception {

        Articulo articulo = new Articulo("123456", "articulo 1", 10.0, 1.0, 1.0, 10, 10);
        articulo = articuloRepository.save(articulo);
        Vendedor vendedor = new Vendedor("username1", "1234", "nombre1", "apellido1");

        VentaRequest ventaRequest = new VentaRequest(articulo,5, vendedor,"Efectivo");

        List<VentaRequest> list = new ArrayList<>();
        list.add(ventaRequest);

        Double monto = ventaController.confirmar(list);

        Articulo articulo1 = articuloRepository.findOne(articulo.getArticuloId());

        List<Venta> ventas = ventaRepository.findAll();

        Assert.assertThat(monto,is(50.0));
        Assert.assertThat(articulo1.getCantidadStock(),is(5));
        Assert.assertThat(ventas,hasSize(1));
        Assert.assertThat(ventas.get(0).getCantidad(),is(5));
        Assert.assertThat(ventas.get(0).getTipoPago(),is(TipoDePago.EFECTIVO));
        Assert.assertThat(ventas.get(0).getCodigo(),is("123456"));
        Assert.assertThat(ventas.get(0).getDescripcion(),is("articulo 1"));
        Assert.assertThat(ventas.get(0).getUsername(),is("username1"));
        Assert.assertThat(ventas.get(0).getPrecioLista(),is(10.0));
        Assert.assertThat(ventas.get(0).getFactor1(),is(1.0));
        Assert.assertThat(ventas.get(0).getFactor2(),is(1.0));
        Assert.assertThat(ventas.get(0).getPrecio(),is(50.0));

    }

    @Test
    public void test_ventas() throws Exception {

        Articulo articulo = new Articulo("123456", "articulo 1", 10.0, 1.0, 1.0, 10, 10);
        articulo = articuloRepository.save(articulo);
        Articulo articulo1 = new Articulo("123457", "articulo 2", 10.0, 1.0, 1.0, 10, 10);
        articulo1 = articuloRepository.save(articulo1);

        Vendedor vendedor = new Vendedor("username1", "1234", "nombre1", "apellido1");

        VentaRequest ventaRequest = new VentaRequest(articulo,5, vendedor,"Efectivo");
        VentaRequest ventaRequest1 = new VentaRequest(articulo1,5, vendedor,"Efectivo");

        BiFunction<Instant,VentaRequest,Venta> function = new VentaFunction();

        ventaRepository.save(function.apply(Instant.parse("2015-12-24T16:00:00Z"),ventaRequest));
        ventaRepository.save(function.apply(Instant.parse("2015-12-24T13:00:00Z"),ventaRequest1));

        Sort order = new Sort(Sort.Direction.DESC, "fecha");
        Page<Venta> page1 = ventaController.obtenerListado(new PageRequest(0,1, order));

        Assert.assertThat(page1.getContent(),hasSize(1));
        Assert.assertThat(page1.getContent().get(0).getCantidad(),is(5));
        Assert.assertThat(page1.getContent().get(0).getTipoPago(),is(TipoDePago.EFECTIVO));
        Assert.assertThat(page1.getContent().get(0).getCodigo(),is("123456"));
        Assert.assertThat(page1.getContent().get(0).getDescripcion(),is("articulo 1"));
        Assert.assertThat(page1.getContent().get(0).getUsername(),is("username1"));
        Assert.assertThat(page1.getContent().get(0).getPrecioLista(),is(10.0));
        Assert.assertThat(page1.getContent().get(0).getFactor1(),is(1.0));
        Assert.assertThat(page1.getContent().get(0).getFactor2(),is(1.0));
        Assert.assertThat(page1.getContent().get(0).getPrecio(),is(50.0));
        Assert.assertThat(page1.isFirst(),is(true));
        Assert.assertThat(page1.isLast(),is(false));

        Page<Venta> page2 = ventaController.obtenerListado(new PageRequest(1,1,order));

        Assert.assertThat(page2.getContent(),hasSize(1));
        Assert.assertThat(page2.getContent().get(0).getCantidad(),is(5));
        Assert.assertThat(page2.getContent().get(0).getTipoPago(),is(TipoDePago.EFECTIVO));
        Assert.assertThat(page2.getContent().get(0).getCodigo(),is("123457"));
        Assert.assertThat(page2.getContent().get(0).getDescripcion(),is("articulo 2"));
        Assert.assertThat(page2.getContent().get(0).getUsername(),is("username1"));
        Assert.assertThat(page2.getContent().get(0).getPrecioLista(),is(10.0));
        Assert.assertThat(page2.getContent().get(0).getFactor1(),is(1.0));
        Assert.assertThat(page2.getContent().get(0).getFactor2(),is(1.0));
        Assert.assertThat(page2.getContent().get(0).getPrecio(),is(50.0));
        Assert.assertThat(page2.isFirst(),is(false));
        Assert.assertThat(page2.isLast(),is(true));

    }

}
