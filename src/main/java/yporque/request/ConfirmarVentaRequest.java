package yporque.request;

import java.util.Collections;
import java.util.List;

/**
 * Created by francisco on 09/01/16.
 */
public class ConfirmarVentaRequest {
    private List<VentaRequest> articulos;
    private List<DevolucionRequest> devoluciones;

    public ConfirmarVentaRequest(List<VentaRequest> articulos, List<DevolucionRequest> devoluciones) {
        this.articulos = articulos;
        this.devoluciones = devoluciones;
    }

    public List<VentaRequest> getArticulos() {
        return articulos;
    }

    public List<DevolucionRequest> getDevoluciones() {
        return devoluciones;
    }

    public ConfirmarVentaRequest() {
        this.articulos = Collections.EMPTY_LIST;
        this.devoluciones = Collections.EMPTY_LIST;
    }
}
