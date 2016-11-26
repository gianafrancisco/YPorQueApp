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
import yporque.model.Movimiento;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;

/**
 * Created by francisco on 19/11/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("yporque.model")
@ContextConfiguration(classes = {MemoryDBConfig.class, CuentaRepository.class, MovimientoRepository.class})
public class MovimientoRepositoryTest {

    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private MovimientoRepository movimientoRepository;

    private Cuenta cuenta1;

    @Before
    public void setUp() throws Exception {
        cuentaRepository.saveAndFlush(new Cuenta("name", "surname", "+543513111111", "name@do.com", 32654223));
        cuenta1 = new Cuenta("name 1", "surname 1", "+543513111111", "name@do.com", 32654224);
        cuentaRepository.saveAndFlush(cuenta1);
    }

    @Test
    public void test_saldo() throws Exception {
        Movimiento movimiento = new Movimiento(Instant.now(), cuenta1.getId(), "cod1", "desc1", -900);
        movimientoRepository.saveAndFlush(movimiento);
        movimiento = Movimiento.generarEntrega(Instant.now(), "Entrega Parcial", cuenta1.getId(), 400);
        movimientoRepository.saveAndFlush(movimiento);

        double saldo = movimientoRepository.saldo(cuenta1.getId());
        Assert.assertThat(saldo, is(-500.0));

    }

    @Test
    public void test_saldo_no_movimientos() throws Exception {
        Double saldo = movimientoRepository.saldo(cuenta1.getId());
        Assert.assertThat(saldo, is(nullValue()));
    }

    @After
    public void tearDown() throws Exception {
        movimientoRepository.deleteAll();
        cuentaRepository.deleteAll();
    }
}
