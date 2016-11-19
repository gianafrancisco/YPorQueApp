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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import yporque.config.MemoryDBConfig;
import yporque.model.Cuenta;
import yporque.repository.CuentaRepository;

import java.util.List;

import static org.hamcrest.core.Is.is;


/**
 * Created by francisco on 18/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("yporque")
@ContextConfiguration(classes = {MemoryDBConfig.class, CuentaController.class})
public class CuentaControllerTest {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private CuentaController cuentaController;

    @Before
    public void setUp() throws Exception {
        cuentaRepository.saveAndFlush(new Cuenta("name", "surname", "+543513111111", "name@do.com", 32654223));
        cuentaRepository.saveAndFlush(new Cuenta("name 1", "surname 1", "+543513111111", "name@do.com", 32654224));
    }

    @Test
    public void get_test() throws Exception {
        ResponseEntity<Page<Cuenta>> response = cuentaController.get(new PageRequest(0, 10));

        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(response.getBody().getTotalElements(), is(2L));
        Cuenta cuenta = response.getBody().getContent().get(0);
        Assert.assertThat(cuenta.getNombre(), is("name"));
        Assert.assertThat(cuenta.getApellido(), is("surname"));
        Assert.assertThat(cuenta.getTelefono(), is("+543513111111"));
        Assert.assertThat(cuenta.getEmail(), is("name@do.com"));
        Assert.assertThat(cuenta.getDni(), is(32654223L));

    }

    @Test
    public void get_search_test() throws Exception {
        ResponseEntity<Page<Cuenta>> response = cuentaController.get("32654223", new PageRequest(0, 10));

        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(response.getBody().getTotalElements(), is(1L));
        Cuenta cuenta = response.getBody().getContent().get(0);
        Assert.assertThat(cuenta.getNombre(), is("name"));
        Assert.assertThat(cuenta.getApellido(), is("surname"));
        Assert.assertThat(cuenta.getTelefono(), is("+543513111111"));
        Assert.assertThat(cuenta.getEmail(), is("name@do.com"));
        Assert.assertThat(cuenta.getDni(), is(32654223L));

    }

    @Test
    public void post_test() throws Exception {

        cuentaRepository.deleteAll();
        Cuenta c = new Cuenta("name", "surname", "+543513111111", "name@do.com", 1);

        ResponseEntity<Cuenta> response = cuentaController.post(c);

        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ResponseEntity<Page<Cuenta>> response1 = cuentaController.get(new PageRequest(0, 10));

        Assert.assertThat(response1.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(response1.getBody().getTotalElements(), is(1L));
        Cuenta cuenta = response1.getBody().getContent().get(0);
        Assert.assertThat(cuenta.getNombre(), is("name"));
        Assert.assertThat(cuenta.getApellido(), is("surname"));
        Assert.assertThat(cuenta.getTelefono(), is("+543513111111"));
        Assert.assertThat(cuenta.getEmail(), is("name@do.com"));
        Assert.assertThat(cuenta.getDni(), is(1L));

    }

    @Test
    public void delete_test() throws Exception {

        List<Cuenta> list = cuentaRepository.findAll();
        Assert.assertThat(list.size(), is(2));
        ResponseEntity<Void> response = cuentaController.delete(list.get(0).getId());

        Assert.assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));

        list = cuentaRepository.findAll();
        Assert.assertThat(list.size(), is(1));

    }

    @Test
    public void put_test() throws Exception {

        List<Cuenta> list = cuentaRepository.findAll();
        Cuenta c = list.get(0);
        c.setApellido("surname 2");
        c.setNombre("name 2");
        c.setEmail("pp@pp.com");
        c.setTelefono("999");
        c.setDni(9);

        ResponseEntity<Cuenta> response = cuentaController.put(c);

        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));

        Cuenta cuenta = cuentaRepository.findOne(response.getBody().getId());
        Assert.assertThat(cuenta.getNombre(), is("name 2"));
        Assert.assertThat(cuenta.getApellido(), is("surname 2"));
        Assert.assertThat(cuenta.getTelefono(), is("999"));
        Assert.assertThat(cuenta.getEmail(), is("pp@pp.com"));
        Assert.assertThat(cuenta.getDni(), is(9L));

    }

    @After
    public void tearDown() throws Exception {
        cuentaRepository.deleteAll();
    }

}
