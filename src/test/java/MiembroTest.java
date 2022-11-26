import dominio.common.ObtenerDistancia;
import dominio.common.Transporte;
import dominio.common.Ubicacion;
import dominio.medicion.FactoresDeEmisionPorTipoConsumo;
import dominio.mediotransporte.Estacion;
import dominio.mediotransporte.Linea;
import dominio.mediotransporte.TransportePublico;
import dominio.mediotransporte.enums.TipoCombustible;
import dominio.mediotransporte.enums.TipoTransportePublico;
import org.junit.jupiter.api.Test;
import dominio.organizacion.Miembro;
import dominio.organizacion.enums.TipoDocumento;
import dominio.organizacion.exceptions.ErrorAgregarTrayectoMiembroException;
import dominio.trayecto.Tramo;
import dominio.trayecto.Trayecto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class MiembroTest {
  private final ObtenerDistancia obtenerDistancia = mock(ObtenerDistancia.class);

  private final FactoresDeEmisionPorTipoConsumo factoresDeEmisionPorTipoConsumo = mock(FactoresDeEmisionPorTipoConsumo.class);
  private List<Miembro> sinMiembros = new ArrayList<>();

  @Test
  public void agregarTrayectoAUnMiembro() {
    Trayecto trayectoDeRoberto = trayectoDeTransportePublico();
    Miembro miembro = new Miembro("Roberto", "Carlos", "20482345", TipoDocumento.DNI);

    assertDoesNotThrow(() -> miembro.nuevoTrayecto(trayectoDeRoberto, sinMiembros));
    assertEquals(miembro.getTrayectos().size(), 1);
  }

  @Test
  public void intentarAgregarUnTrayectoVacioOUnaCantidadIncorrectaDeAcompaniantes() {
    Trayecto trayectoDeRoberto = trayectoDeTransportePublico();
    Miembro miembro = new Miembro("Roberto", "Carlos", "20482345", TipoDocumento.DNI);

    assertThrows(ErrorAgregarTrayectoMiembroException.class,
        () -> miembro.nuevoTrayecto(null, sinMiembros));
    assertThrows(ErrorAgregarTrayectoMiembroException.class,
        () -> miembro.nuevoTrayecto(trayectoDeRoberto, null));
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

  private Trayecto trayectoDeTransportePublico() {
    Linea linea55DeColectivo = linea55DeColectivo(estacionesColectivoDesdeMoronAFlores());
    Linea lineaADeSubte = lineaADeSubte(estacionesSubteDesdeSanPedritoAPlazaDeMayo());

    Transporte colectivo55 =
        new TransportePublico(TipoTransportePublico.COLECTIVO, linea55DeColectivo, 1D,
            TipoCombustible.GASOIL);
    Transporte subteA = new TransportePublico(TipoTransportePublico.SUBTE, lineaADeSubte, 1D, TipoCombustible.GASOIL);

    Tramo tramoMoronAFlores = new Tramo(estacionMoron().getUbicacionEstacion(),
        estacionFlores().getUbicacionEstacion(),
        colectivo55);
    Tramo tramoFloresACasaRosada = new Tramo(estacionSanPedrito().getUbicacionEstacion(),
        estacionPlazaDeMayo().getUbicacionEstacion(), subteA);

    List<Tramo> tramosDelTrayecto = new ArrayList<Tramo>();
    tramosDelTrayecto.add(tramoMoronAFlores);
    tramosDelTrayecto.add(tramoFloresACasaRosada);

    return new Trayecto();
  }
}
