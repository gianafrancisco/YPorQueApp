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
import yporque.config.MemoryDBConfig;
import yporque.model.Articulo;
import yporque.model.Item;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by francisco on 04/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("yporque.model")
@ContextConfiguration(classes = {MemoryDBConfig.class})
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ArticuloRepository articuloRepository;
    private Articulo articulo;

    @Before
    public void setUp() throws Exception {
        articulo = new Articulo(1.0,1.0,1.0,1.0,1.0,"articulo 1");
        articuloRepository.save(articulo);
    }

    @After
    public void tearDown() throws Exception {
        articuloRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    public void test_insert_item() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(new Item("1",1));
        items.add(new Item("2",1));
        items.add(new Item("3",1));

        itemRepository.save(items);

        List<Item> items1 = itemRepository.findAll();
        Assert.assertThat(items1,hasSize(3));
        Assert.assertThat(items1.get(0).getCantidad(),is(items.get(0).getCantidad()));
        Assert.assertThat(items1.get(0).getCodigo(),is(items.get(0).getCodigo()));

    }

    @Test
    public void test_insert_item_and_check_articulo() throws Exception {

        Item item = new Item("22",1);
        item.setArticuloId(articulo.getArticuloId());
        itemRepository.save(item);
        Item item1 = itemRepository.findOne(item.getItemId());
        Assert.assertThat(item1.getArticulo().getDescripcion(),notNullValue());
        Assert.assertThat(item1.getArticulo().getDescripcion(),is("articulo 1"));

    }

}
