package yporque.config;

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
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import yporque.controller.ArticuloController;
import yporque.model.Articulo;
import yporque.model.Vendedor;
import yporque.repository.ArticuloRepository;
import yporque.repository.ItemRepository;
import yporque.repository.VendedorRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by francisco on 18/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("yporque")
@ContextConfiguration(classes = {MemoryDBConfig.class,MyUserDetailsService.class})
public class MyUserDetailsServiceTest {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Before
    public void setUp() throws Exception {
        vendedorRepository.save(new Vendedor("Administrador","123456","Admin","Admin"));
    }

    @After
    public void tearDown() throws Exception {
        vendedorRepository.deleteAll();
    }


    @Test
    public void test_loadUserByUsername() throws Exception {

        UserDetails userDetails = myUserDetailsService.loadUserByUsername("Administrador");

        Assert.assertThat(userDetails.getUsername(),is("Administrador"));
        Assert.assertThat(userDetails.getPassword(),is("123456"));


    }
}
