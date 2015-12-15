package yporque.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import yporque.config.MemoryDBConfig;
import yporque.model.Articulo;
import yporque.model.Customer;
import yporque.model.Item;
import yporque.model.Product;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;

/**
 * Created by francisco on 04/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("yporque.model")
@ContextConfiguration(classes = {MemoryDBConfig.class})
public class ArticuloRepositoryTest {

    @Autowired
    private ArticuloRepository articuloRepository;

    @After
    public void tearDown() throws Exception {
        articuloRepository.deleteAll();
    }

    @Test
    public void test_insert_articulo() throws Exception {
        Articulo art = new Articulo(0.0,1.0,2.0,2.0,1.0,"articulo 1");
        articuloRepository.save(art);
        Articulo a = articuloRepository.findOne(art.getArticuloId());
        Assert.assertThat(a.getDescripcion(),is("articulo 1"));
        Assert.assertThat(a.getPrecio(),is(4.0));
    }

    @Test
    public void test_update_articulo() throws Exception {
        Articulo art = new Articulo(1.0,1.0,1.0,1.0,1.0,"articulo 1");
        articuloRepository.save(art);
        Articulo a = articuloRepository.findOne(art.getArticuloId());
        Assert.assertThat(a.getDescripcion(),is("articulo 1"));

        a.setDescripcion("articulo x");
        articuloRepository.save(a);

        a = articuloRepository.findOne(art.getArticuloId());
        Assert.assertThat(a.getDescripcion(),is("articulo x"));
    }

}
