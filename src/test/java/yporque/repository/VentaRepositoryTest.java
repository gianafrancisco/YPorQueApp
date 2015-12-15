package yporque.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import yporque.config.MemoryDBConfig;
import yporque.model.Articulo;
import yporque.model.Item;
import yporque.model.TipoDePago;
import yporque.model.Venta;

import javax.persistence.Transient;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

/**
 * Created by francisco on 04/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("yporque.model")
@ContextConfiguration(classes = {MemoryDBConfig.class})
public class VentaRepositoryTest {

    @Autowired
    private VentaRepository ventaRepository;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        ventaRepository.deleteAll();
    }

    @Test
    public void test_insert_new_venta() throws Exception {

        Venta venta = new Venta("venta 1", Instant.parse("2015-12-13T16:00:00Z"), TipoDePago.EFECTIVO, 8.0);
        ventaRepository.save(venta);
        Venta ventadb = ventaRepository.findOne(venta.getVentaId());
        Assert.assertThat(ventadb.getDescripcion(),is("venta 1"));
        Assert.assertThat(ventadb.getFecha(),is(Instant.parse("2015-12-13T16:00:00Z")));
        Assert.assertThat(ventadb.getTipoPago(),is(TipoDePago.EFECTIVO));
        Assert.assertThat(ventadb.getMontoTotal(),is(8.0));
    }
}
