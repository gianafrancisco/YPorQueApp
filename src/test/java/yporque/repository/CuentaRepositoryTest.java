package yporque.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import yporque.config.MemoryDBConfig;
import yporque.model.Cuenta;

import java.util.List;

import static org.hamcrest.core.Is.is;

/**
 * Created by francisco on 19/11/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("yporque.model")
@ContextConfiguration(classes = {MemoryDBConfig.class})
public class CuentaRepositoryTest {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Before
    public void setUp() throws Exception {
        cuentaRepository.saveAndFlush(new Cuenta("name", "surname", "+543513111111", "name@do.com", 32654223));
        cuentaRepository.saveAndFlush(new Cuenta("name 1", "surname 1", "+543513111111", "name@do.com", 32654224));
    }

    @Test
    public void test_findBy_DNI() throws Exception {

        Page<Cuenta> cuentaList = cuentaRepository.findByDni(32654223, new PageRequest(0, 10));

        Assert.assertThat(cuentaList.getTotalElements(), is(1L));
        Assert.assertThat(cuentaList.getContent().get(0).getNombre(), is("name"));
        Assert.assertThat(cuentaList.getContent().get(0).getApellido(), is("surname"));
        Assert.assertThat(cuentaList.getContent().get(0).getTelefono(), is("+543513111111"));
        Assert.assertThat(cuentaList.getContent().get(0).getEmail(), is("name@do.com"));

    }

    @Test
    public void test_search() throws Exception {

        Page<Cuenta> cuentaList = cuentaRepository.search("3265422", new PageRequest(0, 10));

        Assert.assertThat(cuentaList.getTotalElements(), is(2L));
        Assert.assertThat(cuentaList.getContent().get(0).getNombre(), is("name"));
        Assert.assertThat(cuentaList.getContent().get(0).getApellido(), is("surname"));
        Assert.assertThat(cuentaList.getContent().get(0).getTelefono(), is("+543513111111"));
        Assert.assertThat(cuentaList.getContent().get(0).getEmail(), is("name@do.com"));
        Assert.assertThat(cuentaList.getContent().get(0).getDni(), is(32654223L));

    }

    @After
    public void tearDown() throws Exception {
        cuentaRepository.deleteAll();
    }
}
