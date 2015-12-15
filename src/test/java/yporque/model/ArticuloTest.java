package yporque.model;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * Created by francisco on 13/12/2015.
 */
public class ArticuloTest {

    @Test
    public void test_new_articulo() throws Exception {
        Articulo articulo = new Articulo(0.0,1.0,2.0,2.0,1.0,"articulo 1");
        Assert.assertThat(articulo.getDescripcion(),is("articulo 1"));
        Assert.assertThat(articulo.getPrecio(),is(4.0));
    }
}
