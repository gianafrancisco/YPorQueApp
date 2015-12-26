package yporque.repository;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import yporque.config.MemoryDBConfig;
import yporque.model.TipoDePago;
import yporque.model.Venta;

import java.time.Instant;

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

    @After
    public void tearDown() throws Exception {
        ventaRepository.deleteAll();
    }

    @Test
    public void test_insert_new_venta() throws Exception {

        Venta venta = new Venta(Instant.parse("2015-12-13T16:00:00Z"),"123456","Articulo 1",10,1.0,1.0,20.0,200.0,TipoDePago.EFECTIVO,"username1");
        ventaRepository.save(venta);
        Venta ventadb = ventaRepository.findOne(venta.getVentaId());
        Assert.assertThat(ventadb.getCodigo(),is("123456"));
        Assert.assertThat(ventadb.getDescripcion(),is("Articulo 1"));
        Assert.assertThat(ventadb.getFecha(),is(Instant.parse("2015-12-13T16:00:00Z")));
        Assert.assertThat(ventadb.getTipoPago(),is(TipoDePago.EFECTIVO));
        Assert.assertThat(ventadb.getPrecio(),is(200.0));
        Assert.assertThat(ventadb.getPrecioLista(),is(20.0));
        Assert.assertThat(ventadb.getCantidad(),is(10));
        Assert.assertThat(ventadb.getFactor1(),is(1.0));
        Assert.assertThat(ventadb.getFactor2(),is(1.0));
        Assert.assertThat(ventadb.getUsername(),is("username1"));

    }

    @Test
    public void test_findByFecha_find_one() throws Exception {

        Venta venta = new Venta(Instant.parse("2015-12-13T16:00:00Z"),"123456","Articulo 1",10,1.0,1.0,20.0,200.0,TipoDePago.EFECTIVO,"username1");
        ventaRepository.save(venta);
        Page<Venta> page = ventaRepository.findByFechaBetween(Instant.parse("2015-12-10T00:00:00Z"), Instant.parse("2015-12-14T00:00:00Z"),new PageRequest(0,1));

        Assert.assertThat(page.getContent(),hasSize(1));
        Assert.assertThat(page.getContent().get(0).getCodigo(),is("123456"));
        Assert.assertThat(page.getContent().get(0).getDescripcion(),is("Articulo 1"));
        Assert.assertThat(page.getContent().get(0).getFecha(),is(Instant.parse("2015-12-13T16:00:00Z")));
    }

    @Test
    public void test_findByFecha_find_none() throws Exception {

        Venta venta = new Venta(Instant.parse("2015-12-13T16:00:00Z"),"123456","Articulo 1",10,1.0,1.0,20.0,200.0,TipoDePago.EFECTIVO,"username1");
        ventaRepository.save(venta);
        Page<Venta> page = ventaRepository.findByFechaBetween(Instant.parse("2015-12-10T00:00:00Z"), Instant.parse("2015-12-13T00:00:00Z"),new PageRequest(0,1));

        Assert.assertThat(page.getContent(),hasSize(0));
    }

    @Test
    public void test_findByUsernameCodigoDescripcion() throws Exception {

        Venta venta = new Venta(Instant.parse("2015-12-13T16:00:00Z"),"COD1","Articulo1",10,1.0,1.0,20.0,200.0,TipoDePago.EFECTIVO,"username1");
        Venta venta1 = new Venta(Instant.parse("2015-12-13T16:00:00Z"),"COD2","Articulo2",10,1.0,1.0,20.0,200.0,TipoDePago.EFECTIVO,"username2");
        Venta venta3 = new Venta(Instant.parse("2015-12-13T17:00:00Z"),"COD3","Articulo3",10,1.0,1.0,20.0,200.0,TipoDePago.EFECTIVO,"username3");
        ventaRepository.save(venta);
        ventaRepository.save(venta1);
        ventaRepository.save(venta3);

        Page<Venta> page = ventaRepository.findByUsernameIgnoreCaseContainingOrCodigoIgnoreCaseContainingOrDescripcionIgnoreCaseContaining("SERNAME1","OD2","RTICULO3" , new PageRequest(0,10));

        Assert.assertThat(page.getContent(),hasSize(3));

    }

    @Test
    public void test_filtrar() throws Exception {

        Venta venta = new Venta(Instant.parse("2015-12-13T16:00:00Z"),"COD1","Articulo1",10,1.0,1.0,20.0,200.0,TipoDePago.EFECTIVO,"username1");
        Venta venta1 = new Venta(Instant.parse("2015-12-13T16:00:00Z"),"COD2","Articulo2",10,1.0,1.0,20.0,200.0,TipoDePago.EFECTIVO,"username2");
        Venta venta3 = new Venta(Instant.parse("2015-12-13T17:00:00Z"),"COD3","Articulo3",10,1.0,1.0,20.0,200.0,TipoDePago.EFECTIVO,"username3");
        ventaRepository.save(venta);
        ventaRepository.save(venta1);
        ventaRepository.save(venta3);

        Page<Venta> page = ventaRepository.filtrar(Instant.parse("2015-12-13T16:30:00Z"),Instant.parse("2015-12-13T17:30:00Z"),"SERNAME", new PageRequest(0,10));

        Assert.assertThat(page.getContent(),hasSize(1));

    }



}
