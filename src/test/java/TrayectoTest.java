import dominio.common.Distancia;
import dominio.common.ObtenerDistancia;
import dominio.common.Transporte;
import dominio.common.Ubicacion;
import dominio.medicion.FactoresDeEmisionPorTipoConsumo;
import dominio.mediotransporte.Estacion;
import dominio.mediotransporte.Linea;
import dominio.mediotransporte.TipoTransporteContratado;
import dominio.mediotransporte.TransporteContratado;
import dominio.mediotransporte.TransporteParticular;
import dominio.mediotransporte.TransportePublico;
import dominio.mediotransporte.TransporteSustentable;
import dominio.mediotransporte.enums.*;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

@Disabled
public class TrayectoTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

  @BeforeEach
  public void abrirTr(){
    entityManager().getTransaction().begin();
  }

  @AfterEach
  public void rollback(){
    entityManager().getTransaction().rollback();
  }

  private final ObtenerDistancia obtenerDistancia = mock(ObtenerDistancia.class);
  private final FactoresDeEmisionPorTipoConsumo factoresDeEmisionPorTipoConsumo = mock(FactoresDeEmisionPorTipoConsumo.class);

  @Test
  public void crearTrayectoCon2Tramos() {
    assertEquals(trayectoConTramos().cantidadDeTramos(), 2);
  }

  @Test
  public void agregarTramoAUnTrayecto() {
    Trayecto trayecto = trayectoConTramos();
    assertEquals(trayecto.cantidadDeTramos(), 2);
    trayecto.agregarTramo(new Tramo(new Ubicacion("1", "2", "4"), new Ubicacion("1", "7", "10"),
        new TransporteSustentable(TipoTransporteSustentable.PIE,
            mock(ObtenerDistancia.class))));
    assertEquals(trayecto.cantidadDeTramos(), 3);
  }

  @Test
  public void crearNuevaLineaDeColectivoConNuevasEstaciones() {

    List<Estacion> nuevasEstaciones = estacionesColectivoDesdeMoronAFlores();
    Linea nuevaLinea55 = linea55DeColectivo(nuevasEstaciones);

    assertEquals(nuevaLinea55.getNombreLinea(), "55");
    assertArrayEquals(nuevaLinea55.getEstaciones().toArray(new Estacion[0]),
        nuevasEstaciones.toArray(new Estacion[0]));
  }

  @Test
  public void crearDidiComoNuevoServicioDeTransporteContratado() {
    TipoTransporteContratado nuevoTransporteContratado = new TipoTransporteContratado("DiDi");

    assertEquals(nuevoTransporteContratado.getNombreDelTransporte(), "DiDi");
  }

  @Test
  public void crearUnTrayectoConLaLinea55YElSubteA() {
    Linea linea55DeColectivo = linea55DeColectivo(estacionesColectivoDesdeMoronAFlores());
    Linea lineaADeSubte = lineaADeSubte(estacionesSubteDesdeSanPedritoAPlazaDeMayo());

    Transporte colectivo55 =
        new TransportePublico(TipoTransportePublico.COLECTIVO, linea55DeColectivo, 1D, TipoCombustible.GASOIL);
    Transporte subteA = new TransportePublico(TipoTransportePublico.SUBTE, lineaADeSubte, 1D, TipoCombustible.GASOIL);

    Tramo tramoMoronAFlores = new Tramo(estacionMoron().getUbicacionEstacion(),
        estacionFlores().getUbicacionEstacion(),
        colectivo55);
    Tramo tramoFloresACasaRosada = new Tramo(estacionSanPedrito().getUbicacionEstacion(),
        estacionPlazaDeMayo().getUbicacionEstacion(), subteA);

    List<Tramo> tramosDelTrayecto = new ArrayList<Tramo>();
    tramosDelTrayecto.add(tramoMoronAFlores);
    tramosDelTrayecto.add(tramoFloresACasaRosada);

    Trayecto trayecto = new Trayecto(tramosDelTrayecto, LocalDate.of(01,10,2022));

    assertEquals(trayecto.cantidadDeTramos(), tramosDelTrayecto.size());
  }

  @Test
  public void crearUnTransporte_con_su_respecetivo_tipo() {
    TransporteParticular transporteParticular =
        new TransporteParticular(TipoCombustible.GASOIL, TipoTransporteParticular.AUTO,
            mock(ObtenerDistancia.class), 1D);
    TransporteSustentable transporteSustentable =
        new TransporteSustentable(TipoTransporteSustentable.BICICLETA,
            mock(ObtenerDistancia.class));

    String expectedTipoAuto = "AUTO";
    String expectedTipoBicicleta = "BICICLETA";

    assertEquals(expectedTipoAuto, transporteParticular.getSubtipo());
    assertEquals(expectedTipoBicicleta, transporteSustentable.getSubtipo());
  }


  private Trayecto trayectoConTramos() {
    Tramo tramo1 = new Tramo(new Ubicacion("1", "1", "1"), new Ubicacion("1", "1", "2"),
        new TransporteContratado(new TipoTransporteContratado("Uber"
        ), mock(ObtenerDistancia.class), 1D, TipoCombustible.GASOIL));
    Tramo tramo2 = new Tramo(new Ubicacion("1", "1", "2"), new Ubicacion("1", "2", "4"),
        new TransporteParticular(TipoCombustible.GASOIL, TipoTransporteParticular.AUTO,
            mock(ObtenerDistancia.class), 1D));

    List<Tramo> tramos = new ArrayList<Tramo>();
    tramos.add(tramo1);
    tramos.add(tramo2);

    return new Trayecto(tramos);
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
