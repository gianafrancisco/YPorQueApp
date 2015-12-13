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
        List<Item> items = new ArrayList<>();
        items.add(new Item("1",1));
        items.add(new Item("2",1));
        items.add(new Item("3",1));
        Articulo art = new Articulo(1.0,1.0,1.0,1.0,1.0,"articulo 1");
        art.setItems(items);
        articuloRepository.save(art);
        Articulo a = articuloRepository.findOne(art.getArticuloId());
        Assert.assertThat(a.getDescripcion(),is("articulo 1"));
        Assert.assertThat(a.getItems().size(),is(3));
    }

    @Test
    public void test_update_articulo() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(new Item("1",1));
        items.add(new Item("2",1));
        items.add(new Item("3",1));
        Articulo art = new Articulo(1.0,1.0,1.0,1.0,1.0,"articulo 1");
        art.setItems(items);
        articuloRepository.save(art);
        Articulo a = articuloRepository.findOne(art.getArticuloId());
        Assert.assertThat(a.getDescripcion(),is("articulo 1"));
        Assert.assertThat(a.getItems().size(),is(3));

        a.getItems().remove(0);
        a.setDescripcion("articulo x");
        articuloRepository.save(a);

        a = articuloRepository.findOne(art.getArticuloId());
        Assert.assertThat(a.getDescripcion(),is("articulo x"));
        Assert.assertThat(a.getItems().size(),is(2));
    }

}
