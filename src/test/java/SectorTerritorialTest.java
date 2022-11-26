import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import dominio.common.DatoActividad;
import dominio.common.Ubicacion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import dominio.medicion.FactoresDeEmisionPorTipoConsumo;
import dominio.organizacion.Miembro;
import dominio.organizacion.Organizacion;
import dominio.organizacion.Sector;
import dominio.organizacion.enums.Clasificacion;
import dominio.organizacion.enums.TipoOrganizacion;
import servicios.ServicioCargaDatosActividad;
import servicios.ServicioCargarDatosActividadMedianteCSV;

public class SectorTerritorialTest {

  public Organizacion organizacionConNuevoSector() {

    FactoresDeEmisionPorTipoConsumo factoresDeEmisionPorTipoConsumo = mock(FactoresDeEmisionPorTipoConsumo.class);

    ServicioCargaDatosActividad servicioCargaDatosActividad =
        new ServicioCargarDatosActividadMedianteCSV();

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

//  @Test
//  public void dominio.sectorTerritorial(){
//    Organizacion dominio.organizacion = organizacionConNuevoSector();
//   List<Organizacion> organizaciones = new ArrayList<>();
//  organizaciones.add(dominio.organizacion);
//    SectorTerritorial dominio.sectorTerritorial = new SectorTerritorial(TipoSectorTerritorial.MUNICIPIO,
//      organizaciones);
//   Double huella = dominio.sectorTerritorial.HCTotalDelSector( LocalDate.of(2020,2,1));
//   huella  = huella +1 ;
//  }
}
