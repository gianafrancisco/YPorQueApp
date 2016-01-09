package yporque.model;

import org.junit.Assert;
import org.junit.Test;
import yporque.request.RetiroRequest;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;


/**
 * Created by francisco on 12/12/2015.
 */
public class RetiroRequestTest {

    @Test
    public void test_new_RetiroRequest() throws Exception {
        RetiroRequest retiroRequest = new RetiroRequest(100.0,"desc1", "username1");

        Assert.assertThat(retiroRequest.getMonto(),is(100.0));
        Assert.assertThat(retiroRequest.getDescripcion(),is("desc1"));
        Assert.assertThat(retiroRequest.getUsername(),is("username1"));

    }

    @Test
    public void test_new_RetiroRequest_default_constructor() throws Exception {

        RetiroRequest retiroRequest = new RetiroRequest();

        Assert.assertThat(retiroRequest.getMonto(),is(0.0));
        Assert.assertThat(retiroRequest.getDescripcion(),is(""));
        Assert.assertThat(retiroRequest.getUsername(),is(""));


    }


}
