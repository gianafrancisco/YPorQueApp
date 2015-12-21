package yporque.controller;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import yporque.config.MemoryDBConfig;
import yporque.model.Articulo;
import yporque.repository.ArticuloRepository;
import yporque.repository.ItemRepository;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by francisco on 18/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("yporque")
@ContextConfiguration(classes = {MemoryDBConfig.class})
public class ArticuloControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    //@Autowired
    private ArticuloController articuloController;

    @Before
    public void setUp() throws Exception {
        articuloController = new ArticuloController();
        articuloController.setArticuloRepository(articuloRepository);
        articuloController.setItemRepository(itemRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(articuloController).build();

    }

    @After
    public void tearDown() throws Exception {
        itemRepository.deleteAll();
        articuloRepository.deleteAll();
    }

    /*
    @Test
    public void test_add_new_articulo() throws Exception {

        Articulo articulo = new Articulo(0.0,1.0,1.0,1.0,0.0,"Articulo 1");

        mockMvc.perform("/articulo/agregar")


        Articulo articulo1 = articuloRepository.findAll().get(0);
        Assert.assertThat(articulo.getDescripcion(),is(articulo1.getDescripcion()));

    }
    */

    @Test
    @Ignore
    public void test_get_articulos() throws Exception {

        Articulo articulo = new Articulo("1234","articulo 1",1.0,2.0,2.0,1,1);
        articuloRepository.saveAndFlush(articulo);

        mockMvc.perform(get("/articulos").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.content[0].descripcion").value("Articulo 1"));

    }
}
