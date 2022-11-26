import dominio.common.Distancia;
import dominio.common.ObtenerDistancia;
import dominio.common.Transporte;
import dominio.common.Ubicacion;
import dominio.mediotransporte.Estacion;
import dominio.mediotransporte.Linea;
import dominio.mediotransporte.TipoTransporteContratado;
import dominio.mediotransporte.TransporteContratado;
import dominio.mediotransporte.TransporteParticular;
import dominio.mediotransporte.TransportePublico;
import dominio.mediotransporte.TransporteSustentable;
import dominio.mediotransporte.enums.TipoCombustible;
import dominio.mediotransporte.enums.TipoTransporteParticular;
import dominio.mediotransporte.enums.TipoTransportePublico;
import dominio.mediotransporte.enums.TipoTransporteSustentable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Disabled
public class TransporteTest {

  private final ObtenerDistancia obtenerDistancia = mock(ObtenerDistancia.class);

  @Test
  public void calcular_distancia_transportePublico_camino_ida() {
    Ubicacion inicio = new Ubicacion("1", "calle 1", "140");
    Ubicacion destino = new Ubicacion("1", "calle 6", "150");

    Estacion moron = new Estacion("Moron", new Ubicacion("1", "calle 1", "150"), 10L, 40L, 1,
        obtenerDistancia);
    Estacion estacionOnce = new Estacion("Once", new Ubicacion("1", "calle 5",
        "150"), 20L, 15L, 2, obtenerDistancia);
    Estacion estacionCongreso =
        new Estacion("Congreso", new Ubicacion("1", "calle" + " 6", "150"), 30L,
            25L, 3, obtenerDistancia);


    when(obtenerDistancia.obtenerDistancia(eq(inicio),
        eq(moron.getUbicacionEstacion()))).thenReturn(new Distancia(100L, "KM"));
    when(obtenerDistancia.obtenerDistancia(eq(inicio),
        eq(estacionOnce.getUbicacionEstacion()))).thenReturn(new Distancia(200L, "KM"));
    when(obtenerDistancia.obtenerDistancia(eq(inicio),
        eq(estacionCongreso.getUbicacionEstacion()))).thenReturn(new Distancia(300L, "KM"));

    when(obtenerDistancia.obtenerDistancia(eq(destino),
        eq(moron.getUbicacionEstacion()))).thenReturn(new Distancia(300L, "KM"));
    when(obtenerDistancia.obtenerDistancia(eq(destino),
        eq(estacionOnce.getUbicacionEstacion()))).thenReturn(new Distancia(200L, "KM"));
    when(obtenerDistancia.obtenerDistancia(eq(destino),
        eq(estacionCongreso.getUbicacionEstacion()))).thenReturn(new Distancia(100L, "KM"));

    List<Estacion> estaciones = new ArrayList<>();
    estaciones.add(estacionOnce);
    estaciones.add(moron);
    estaciones.add(estacionCongreso);
    Transporte transportePublico = new TransportePublico(TipoTransportePublico.SUBTE,
        lineaADeSubte(estaciones), 1D, TipoCombustible.GASOIL);
    Long distancia = transportePublico.calcularDistanciaTransporte(inicio, destino);
    assertEquals(60, distancia, "La distancia de ida");

  }

  @Test
  public void calcularDistancia_transporte_publico_camino_de_vuelta() {
    Ubicacion inicio = new Ubicacion("1", "calle 1", "140");
    Ubicacion destino = new Ubicacion("1", "calle 6", "150");

    Estacion moron = new Estacion("Moron", new Ubicacion("1", "calle 1", "150"), 10L, 40L, 3,
        obtenerDistancia);
    Estacion estacionOnce = new Estacion("Once", new Ubicacion("1", "calle 5",
        "150"), 20L, 15L, 2, obtenerDistancia);
    Estacion estacionCongreso =
        new Estacion("Congreso", new Ubicacion("1", "calle" + " 6", "150"), 30L,
            25L, 1, obtenerDistancia);


    when(obtenerDistancia.obtenerDistancia(eq(inicio),
        eq(moron.getUbicacionEstacion()))).thenReturn(new Distancia(100L, "KM"));
    when(obtenerDistancia.obtenerDistancia(eq(inicio),
        eq(estacionOnce.getUbicacionEstacion()))).thenReturn(new Distancia(200L, "KM"));
    when(obtenerDistancia.obtenerDistancia(eq(inicio),
        eq(estacionCongreso.getUbicacionEstacion()))).thenReturn(new Distancia(300L, "KM"));

    when(obtenerDistancia.obtenerDistancia(eq(destino),
        eq(moron.getUbicacionEstacion()))).thenReturn(new Distancia(300L, "KM"));
    when(obtenerDistancia.obtenerDistancia(eq(destino),
        eq(estacionOnce.getUbicacionEstacion()))).thenReturn(new Distancia(200L, "KM"));
    when(obtenerDistancia.obtenerDistancia(eq(destino),
        eq(estacionCongreso.getUbicacionEstacion()))).thenReturn(new Distancia(100L, "KM"));


    List<Estacion> estaciones = new ArrayList<>();
    estaciones.add(estacionOnce);
    estaciones.add(moron);
    estaciones.add(estacionCongreso);
    Transporte transportePublico = new TransportePublico(TipoTransportePublico.SUBTE,
        lineaADeSubte(estaciones), 1D, TipoCombustible.GASOIL);
    Long distanciaEnTransportePublico =
        transportePublico.calcularDistanciaTransporte(inicio, destino);

    assertEquals(80, distanciaEnTransportePublico, "L a distancia de ida");

  }

  @Test
  public void test_trayecto_auto_particular() {

    Ubicacion inicio = new Ubicacion("1", "calle 1", "140");
    Ubicacion destino = new Ubicacion("1", "calle 6", "150");

    when(obtenerDistancia.obtenerDistancia(inicio, destino)).thenReturn(new Distancia(100L, "KM"));

    Transporte transporte =
        new TransporteParticular(TipoCombustible.GASOIL, TipoTransporteParticular.AUTO,
            obtenerDistancia, 1D);

    Long distancia = transporte.calcularDistanciaTransporte(inicio, destino);
    assertEquals(100L, distancia);
  }

  @Test
  public void test_trayecto_transporte_sustentable() {

    Ubicacion inicio = new Ubicacion("1", "calle 1", "140");
    Ubicacion destino = new Ubicacion("1", "calle 6", "150");

    when(obtenerDistancia.obtenerDistancia(inicio, destino)).thenReturn(new Distancia(100L, "KM"));

    Transporte transporte = new TransporteSustentable(TipoTransporteSustentable.BICICLETA,
        obtenerDistancia);

    Long distancia = transporte.calcularDistanciaTransporte(inicio, destino);

    assertEquals(100L, distancia);
  }

  @Test
  public void test_trayecto_transporte_contratado() {

    Ubicacion inicio = new Ubicacion("1", "calle 1", "140");
    Ubicacion destino = new Ubicacion("1", "calle 6", "150");

    when(obtenerDistancia.obtenerDistancia(inicio, destino)).thenReturn(new Distancia(100L, "KM"));

    Transporte transporte = new TransporteContratado(new TipoTransporteContratado("UBER"),
        obtenerDistancia, 1D, TipoCombustible.GASOIL);

    Long distancia = transporte.calcularDistanciaTransporte(inicio, destino);

    assertEquals(100L, distancia);
  }

  private Linea lineaADeSubte(List<Estacion> estacionesDeLaLineaA) {
    Linea lineaA = new Linea(TipoTransportePublico.SUBTE, "A", estacionesDeLaLineaA);
    return lineaA;
  }
}
