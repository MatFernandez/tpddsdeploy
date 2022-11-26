package db;

import dominio.common.Distancia;
import dominio.common.ObtenerDistancia;
import dominio.common.Transporte;
import dominio.common.Ubicacion;
import dominio.medicion.FactoresDeEmisionPorTipoConsumo;
import dominio.mediotransporte.Estacion;
import dominio.mediotransporte.Linea;
import dominio.mediotransporte.TransportePublico;
import dominio.mediotransporte.enums.TipoCombustible;
import dominio.mediotransporte.enums.TipoTransportePublico;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import dominio.trayecto.Tramo;
import dominio.trayecto.Trayecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TrayectoEntityTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

  private final ObtenerDistancia obtenerDistancia = mock(ObtenerDistancia.class);
  private final FactoresDeEmisionPorTipoConsumo factoresDeEmisionPorTipoConsumo = mock(FactoresDeEmisionPorTipoConsumo.class);


  @BeforeEach
  public void abrirTr(){
    entityManager().getTransaction().begin();
  }

  @AfterEach
  public void rollback(){
    entityManager().getTransaction().rollback();
  }

  @Test
  @DisplayName("crea un dominio.trayecto y calcula la distancia total")
  public void crearUnTrayectoConLaLinea55YElSubteACalculoTrayecto() {

    Ubicacion inicioTrayecto = new Ubicacion("1", "inicio 1", "150");
    Ubicacion destinoTrayecto = new Ubicacion("1", "destino 1", "150");

    Ubicacion inicioFlores = new Ubicacion("1", "flores 1", "150");
    Ubicacion destinoFlores = new Ubicacion("1", "flores 2", "150");

    Ubicacion inicioMoron = new Ubicacion("1", "moron 1", "150");
    Ubicacion destinoMoron = new Ubicacion("1", "moron 2", "150");

    Estacion moron = new Estacion("Moron", inicioMoron, 60L, 20L, 1,
        obtenerDistancia);

    Estacion estacionFlores = new Estacion("Flores", inicioFlores, 30L, 20L, 1, obtenerDistancia);
    List<Estacion> estaciones = Arrays.asList(estacionFlores, moron);

    Linea linea55DeColectivo = linea55DeColectivo(estaciones);

    // Para calcular la distancia entre cada estación para luego sumarizar
    when(obtenerDistancia.obtenerDistancia(inicioFlores, destinoFlores)).thenReturn(new Distancia(30L, "KM"));
    when(obtenerDistancia.obtenerDistancia(inicioMoron, destinoMoron)).thenReturn(new Distancia(60L, "KM"));

    // Para calcular que estacion esta más cerca del inicioTrayecto
    when(obtenerDistancia.obtenerDistancia(inicioTrayecto, inicioMoron)).thenReturn(new Distancia(50L,
        "KM"));
    when(obtenerDistancia.obtenerDistancia(inicioTrayecto, inicioFlores)).thenReturn(new Distancia(70L,
        "KM"));

    // Para calcular que estación esta más cerca del destinoTrayecto
    when(obtenerDistancia.obtenerDistancia(destinoTrayecto, inicioMoron)).thenReturn(new Distancia(70L,
        "KM"));
    when(obtenerDistancia.obtenerDistancia(destinoTrayecto, inicioFlores)).thenReturn(new Distancia(70L,
        "KM"));

    Transporte colectivo55 =
        new TransportePublico(TipoTransportePublico.COLECTIVO, linea55DeColectivo, 1D, TipoCombustible.GASOIL);

    Tramo tramoMoronAFlores = new Tramo(inicioTrayecto, destinoTrayecto,
        colectivo55);

    List<Tramo> tramosDelTrayecto = new ArrayList<Tramo>();
    tramosDelTrayecto.add(tramoMoronAFlores);

    Trayecto trayecto = new Trayecto(tramosDelTrayecto);

    int distanciaTotalTrayecto = 90;

    entityManager().persist(trayecto);
    List<Trayecto> t1 = entityManager().createQuery("from Trayecto").getResultList();
    assertNotNull(t1);

    assertEquals(distanciaTotalTrayecto, trayecto.getDistanciaTotal());
    assertEquals(trayecto.cantidadDeTramos(), tramosDelTrayecto.size());
  }

  private Estacion estacionMoron() {
    return new Estacion("Moron", new Ubicacion("1", "calle 1", "150"), 10L, 20L, 1,
        obtenerDistancia);
  }

  private Estacion estacionFloresta() {
    return new Estacion("Floresta", new Ubicacion("1", "calle 2"
        , "150"), 10L, 20L, 1, obtenerDistancia);
  }

  private Estacion estacionFlores() {
    return new Estacion("Flores", new Ubicacion("1", "calle 3",
        "150"), 10L, 20L, 1, obtenerDistancia);
  }

  private Estacion estacionSanPedrito() {
    return new Estacion("San Pedrito", new Ubicacion(
        "1", "calle 4", "150"), 10L, 20L, 1, obtenerDistancia);
  }

  private Estacion estacionOnce() {
    return new Estacion("San Pedrito", new Ubicacion("1", "calle 5",
        "150"), 10L, 20L, 1, obtenerDistancia);
  }

  private Estacion estacionCongreso() {
    return new Estacion("San Pedrito", new Ubicacion("1", "calle" +
        " 6", "150"), 10L, 20L, 1, obtenerDistancia);
  }

  private Estacion estacionPlazaDeMayo() {
    return new Estacion("San Pedrito", new Ubicacion(
        "1", "calle 7", "150"), 10L, 20L, 1, obtenerDistancia);
  }

  private List<Estacion> estacionesColectivoDesdeMoronAFlores() {
    List<Estacion> estaciones = new ArrayList<Estacion>();

    estaciones.add(estacionMoron());
    estaciones.add(estacionFloresta());
    estaciones.add(estacionFlores());

    return estaciones;
  }

  private List<Estacion> estacionesSubteDesdeSanPedritoAPlazaDeMayo() {
    List<Estacion> estaciones = new ArrayList<Estacion>();

    estaciones.add(estacionSanPedrito());
    estaciones.add(estacionOnce());
    estaciones.add(estacionCongreso());
    estaciones.add(estacionPlazaDeMayo());

    return estaciones;
  }

  private Linea linea55DeColectivo(List<Estacion> estacionesDel55) {
    Linea linea55 = new Linea(TipoTransportePublico.COLECTIVO, "55", estacionesDel55);
    return linea55;
  }

  private Linea lineaADeSubte(List<Estacion> estacionesDeLaLineaA) {
    Linea lineaA = new Linea(TipoTransportePublico.SUBTE, "A", estacionesDeLaLineaA);
    return lineaA;
  }

}
