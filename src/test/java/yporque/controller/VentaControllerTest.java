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
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import yporque.config.MemoryDBConfig;
import yporque.model.Articulo;
import yporque.model.Vendedor;
import yporque.model.VentaRequest;
import yporque.repository.ArticuloRepository;
import yporque.repository.VendedorRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

/**
 * Created by francisco on 18/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("yporque")
@ContextConfiguration(classes = {MemoryDBConfig.class,VentaController.class})
public class VentaControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private VentaController ventaController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(ventaController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @After
    public void tearDown() throws Exception {
        articuloRepository.deleteAll();
    }

    @Test
    public void test_confirmar_venta() throws Exception {

        Articulo articulo = new Articulo("123456", "articulo 1", 10.0, 1.0, 1.0, 10, 10);
        articulo = articuloRepository.save(articulo);
        Vendedor vendedor = new Vendedor("usernam1", "1234", "nombre1", "apellido1");

        VentaRequest ventaRequest = new VentaRequest(articulo,5, vendedor,"Efectivo");

        List<VentaRequest> list = new ArrayList<>();
        list.add(ventaRequest);

        Double monto = ventaController.confirmar(list);

        Articulo articulo1 = articuloRepository.findOne(articulo.getArticuloId());

        Assert.assertThat(monto,is(50.0));
        Assert.assertThat(articulo1.getCantidadStock(),is(5));



    }
}
