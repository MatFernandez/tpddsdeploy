import dominio.common.Distancia;
import dominio.common.Ubicacion;
import dominio.common.Transporte;
import dominio.mediotransporte.TipoTransporteContratado;
import dominio.mediotransporte.TransporteContratado;
import dominio.mediotransporte.enums.TipoCombustible;
import org.junit.jupiter.api.Test;
import restclient.classes.ObtenerDistanciaGeodds;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestApiGeodds {

  @Test
  //@Disabled
  public void test_api_geodds_only_service() {
    ObtenerDistanciaGeodds servicioGeodds = ObtenerDistanciaGeodds.getInstancia();
    Ubicacion inicio = new Ubicacion("1", "calle 1", "140");
    Ubicacion destino = new Ubicacion("1", "calle 6", "150");

    Distancia distancia = servicioGeodds.obtenerDistancia(inicio, destino);

    assertNotEquals(0, distancia.getValor());
    assertNotNull("KM", distancia.getUnidad());
  }

  @Test
  //@Disabled
  public void test_api_geodds_inject() {
    ObtenerDistanciaGeodds servicioGeodds = ObtenerDistanciaGeodds.getInstancia();
    Ubicacion inicio = new Ubicacion("1", "calle 1", "140");
    Ubicacion destino = new Ubicacion("1", "calle 6", "150");
    Transporte transporte = new TransporteContratado(new TipoTransporteContratado("UBER"),
        servicioGeodds, 1D, TipoCombustible.GASOIL);

    Long distancia = transporte.calcularDistanciaTransporte(inicio, destino);

    assertNotEquals(0, distancia);
    assertNotNull(distancia);
  }
}
