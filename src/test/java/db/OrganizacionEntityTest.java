package db;

import dominio.common.DatoActividad;
import dominio.common.ObtenerDistancia;
import dominio.common.Ubicacion;
import dominio.medicion.FactoresDeEmisionPorTipoConsumo;
import dominio.mediotransporte.Estacion;
import dominio.mediotransporte.Linea;
import dominio.mediotransporte.enums.TipoTransportePublico;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import dominio.organizacion.Miembro;
import dominio.organizacion.Organizacion;
import dominio.organizacion.Sector;
import dominio.organizacion.enums.Clasificacion;
import dominio.organizacion.enums.TipoDocumento;
import dominio.organizacion.enums.TipoOrganizacion;
import servicios.ServicioCargaDatosActividad;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;


public class OrganizacionEntityTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

  @BeforeEach
  public void abrirTr(){
    entityManager().getTransaction().begin();
  }

  @AfterEach
  public void rollback(){
    entityManager().getTransaction().rollback();
  }

  FactoresDeEmisionPorTipoConsumo factoresDeEmisionPorTipoConsumo = mock(FactoresDeEmisionPorTipoConsumo.class);

  ServicioCargaDatosActividad servicioCargaDatosActividad = mock(ServicioCargaDatosActividad.class);
  private final ObtenerDistancia obtenerDistancia = mock(ObtenerDistancia.class);


  private List<Miembro> listaMiembros = new ArrayList<>();


  @Test
  public void seDaDeAltaUnaOrganizacionConSusSectores() {
    assertEquals(organizacionConNuevoSector().cantidadDeSectores(), 3);
  }

  public Organizacion organizacionConNuevoSector() {
    List<Sector> sectoresOrganizacion = new ArrayList<>();
    List<Miembro> miembros1 = new ArrayList<>();
    List<Miembro> miembros2 = new ArrayList<>();
    List<Miembro> miembros3 = new ArrayList<>();
    List<DatoActividad> datos = new ArrayList<>();
    Sector sector1 = new Sector("Recursos humanos", miembros1);
    Sector sector2 = new Sector("Administracion", miembros2);
    Sector sector3 = new Sector("Capacitacion", miembros3);
    sectoresOrganizacion.add(sector1);
    sectoresOrganizacion.add(sector2);
    Ubicacion buenosAires = new Ubicacion("1", "54", "12");
    Predicate<Miembro> criterioAceptacion = miembro -> miembro.getNumeroDocumento().charAt(0) < 5;
    Organizacion organizacionAmpliada =
        new Organizacion("Caridad", buenosAires, TipoOrganizacion.ONG, sectoresOrganizacion,
            Clasificacion.MINISTERIO, datos, servicioCargaDatosActividad, factoresDeEmisionPorTipoConsumo);
    organizacionAmpliada.darAltaSector(sector3);
    return organizacionAmpliada;
  }

  public Sector miembroSeVinculaAUnaOrganizacionYSeAgregaAUnSector() {
    List<Sector> sectoresOrganizacion1 = new ArrayList<>();
    List<Miembro> miembros1 = new ArrayList<>();
    List<Miembro> miembros2 = new ArrayList<>();
    List<DatoActividad> datos = new ArrayList<>();
    Sector sector1 = new Sector("Recursos humanos", miembros1);
    Sector sector2 = new Sector("Administracion", miembros2);
    sectoresOrganizacion1.add(sector1);
    List<Sector> sectoresOrganizacion2 = new ArrayList<>();
    sectoresOrganizacion2.add(sector2);
    Ubicacion buenosAires = new Ubicacion("1", "54", "12");
    Ubicacion cordoba = new Ubicacion("1", "25", "44");
    Predicate<Miembro> criterioAceptacion = miembro -> miembro.getNumeroDocumento().charAt(0) < 5;
    Organizacion primeraOrganizacion =
        new Organizacion("Caridad", buenosAires, TipoOrganizacion.ONG, sectoresOrganizacion1,
            Clasificacion.MINISTERIO, datos, servicioCargaDatosActividad, factoresDeEmisionPorTipoConsumo);
    Organizacion segundaOrganizacion =
        new Organizacion("Donacion", cordoba, TipoOrganizacion.ONG, sectoresOrganizacion2,
            Clasificacion.MINISTERIO, datos, servicioCargaDatosActividad, factoresDeEmisionPorTipoConsumo);
    List<Organizacion> listaDeOrganizaciones = new ArrayList<>();
    listaDeOrganizaciones.add(primeraOrganizacion);
    Miembro miembro =
        new Miembro("Miembro", "Apellido", "49545122", TipoDocumento.DNI);

    return sector2;
  }


  public Organizacion organizacionCaritas() {
    Organizacion organizacion = new Organizacion("Caritas",
        ubicacionCaritas(),
        TipoOrganizacion.ONG,
        sectoresCaritas(),
        Clasificacion.MINISTERIO,
        null,
        null,
        null
    );
    return organizacion;
  }

  public Organizacion organizacionCruzRoja() {
    Organizacion organizacion = new Organizacion("Cruz Roja",
        ubicacionCruzRoja(),
        TipoOrganizacion.GUBERNAMENTAL,
        sectoresCruzRoja(),
        Clasificacion.MINISTERIO,
        null,
        null,
        null
    );
    return organizacion;
  }


  public Ubicacion ubicacionCaritas() {
    return new Ubicacion("Lanus", "9 de julio", "200");
  }

  public Ubicacion ubicacionCruzRoja() {
    return new Ubicacion("Lomas de Zamora", "Italia", "2200");
  }

  public List<Sector> sectoresCaritas() {
    List<Sector> sectoresCaritas = new ArrayList<>();
    List<Miembro> miembrosRRHH = new ArrayList<>();
    List<Miembro> miembrosAdministracion = new ArrayList<>();
    Miembro micaela = new Miembro("Micaela", "Sanchez", "4125132", TipoDocumento.DNI);
    Miembro javier = new Miembro("Javier", "Lopez", "5454534", TipoDocumento.DNI);
    Miembro matias = new Miembro("Matias", "Fernandez", "4454534", TipoDocumento.DNI);
    miembrosRRHH.add(micaela);
    miembrosAdministracion.add(javier);
    miembrosRRHH.add(matias);
    Sector sectorRRHH = new Sector("Recursos humanos", miembrosRRHH);
    Sector sectorAdministracion = new Sector("Administracion", miembrosAdministracion);
    sectoresCaritas.add(sectorAdministracion);
    sectoresCaritas.add(sectorRRHH);
    return sectoresCaritas;
  }

  public List<Sector> sectoresCruzRoja() {
    List<Sector> sectoresCruzRoja = new ArrayList<>();
    List<Miembro> miembrosAdministracion = new ArrayList<>();
    Miembro tomas = new Miembro("Tomas", "Sanchez", "6925132", TipoDocumento.DNI);
    Miembro agustin = new Miembro("Agustin", "Lopez", "14545984", TipoDocumento.DNI);
    miembrosAdministracion.add(tomas);
    miembrosAdministracion.add(agustin);
    //listaMiembros.add(tomas);
    //agustin.nuevoTrayecto(crearUnTrayectoConLaLinea55YElSubteA(), listaMiembros);
    Sector sectorAdministracion = new Sector("Administracion", miembrosAdministracion);
    sectoresCruzRoja.add(sectorAdministracion);
    return sectoresCruzRoja;
  }


  @Test
  public void guardarOrganizacion() {
    Organizacion caritas = this.organizacionCaritas();
    Organizacion cruzRoja = this.organizacionCruzRoja();
    entityManager().persist(cruzRoja);
    entityManager().persist(caritas);
    //Organizacion dominio.organizacion = entityManager().find(Organizacion.class, 1L);
    //assertNotNull(dominio.organizacion);
  }




  private Estacion estacionMoron() {
    return new Estacion("Moron", new Ubicacion("1", "calle 1", "150"), 10L, 20L, 1,
        null);
  }

  private Estacion estacionFloresta() {
    return new Estacion("Floresta", new Ubicacion("1", "calle 2"
        , "150"), 10L, 20L, 1, null);
  }

  private Estacion estacionFlores() {
    return new Estacion("Flores", new Ubicacion("1", "calle 3",
        "150"), 10L, 20L, 1, null);
  }

  private Estacion estacionSanPedrito() {
    return new Estacion("San Pedrito", new Ubicacion(
        "1", "calle 4", "150"), 10L, 20L, 1, null);
  }

  private Estacion estacionOnce() {
    return new Estacion("San Pedrito", new Ubicacion("1", "calle 5",
        "150"), 10L, 20L, 1, null);
  }

  private Estacion estacionCongreso() {
    return new Estacion("San Pedrito", new Ubicacion("1", "calle" +
        " 6", "150"), 10L, 20L, 1, null);
  }

  private Estacion estacionPlazaDeMayo() {
    return new Estacion("San Pedrito", new Ubicacion(
        "1", "calle 7", "150"), 10L, 20L, 1, null);
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
