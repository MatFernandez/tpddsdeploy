import dominio.common.DatoActividad;
import dominio.common.TipoConsumo;
import dominio.common.Ubicacion;
import dominio.common.enums.Periodicidad;
import dominio.medicion.FactorDeEmision;
import dominio.medicion.FactoresDeEmisionPorTipoConsumo;
import dominio.medicion.enums.Magnitud;
import dominio.medicion.enums.Unidad;
import dominio.mediotransporte.Linea;
import dominio.mediotransporte.TransportePublico;
import dominio.mediotransporte.enums.TipoCombustible;
import dominio.mediotransporte.enums.TipoTransportePublico;
import dominio.organizacion.Miembro;
import dominio.organizacion.Organizacion;
import dominio.organizacion.Sector;
import dominio.organizacion.enums.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import servicios.ServicioCargaDatosActividad;
import servicios.ServicioCargarDatosActividadMedianteCSV;
import dominio.trayecto.Tramo;
import dominio.trayecto.Trayecto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ConsumoTest {
//
//  private FactoresDeEmisionPorTipoConsumo factoresDeEmisionPorTipoConsumo;
//
//  private final ServicioCargaDatosActividad servicioCargaDatosActividad =
//      new ServicioCargarDatosActividadMedianteCSV();
//
//  private final ServicioCargaDatosActividad servicioCargaDatosActividadMock =
//      mock(ServicioCargaDatosActividad.class);
//
//  private Organizacion unaOrganizacion(ServicioCargaDatosActividad servicioCargaDatosActividad) {
//
//    this.factoresDeEmisionPorTipoConsumo = new FactoresDeEmisionPorTipoConsumo(new HashMap<>());
//    this.factoresDeEmisionPorTipoConsumo.agregarFactorEmision(
//        TipoConsumo.GAS_NATURAL, new FactorDeEmision("", 2d, Unidad.m3, Magnitud.kg));
//    this.factoresDeEmisionPorTipoConsumo.agregarFactorEmision(TipoConsumo.NAFTA, new FactorDeEmision("", 3d, Unidad.lt, Magnitud.kg));
//    this.factoresDeEmisionPorTipoConsumo.agregarFactorEmision(TipoConsumo.DIESEL, new FactorDeEmision("", 5d, Unidad.lt, Magnitud.kg));
//    this.factoresDeEmisionPorTipoConsumo.agregarFactorEmision(TipoConsumo.ELECTRICIDAD, new FactorDeEmision("", 2d, Unidad.Kwh, Magnitud.kg));
//
//    List<Sector> sectores = new ArrayList<>();
//    List<Miembro> miembros = new ArrayList<>();
//    List<DatoActividad> datos = new ArrayList<>();
//    Sector sector = new Sector("Recursos humanos", miembros);
//    sectores.add(sector);
//    Ubicacion unaUbicacion = new Ubicacion("La Granja", "54", "12");
//
//    return new Organizacion("Caridad", unaUbicacion, TipoOrganizacion.ONG, sectores,
//        Clasificacion.MINISTERIO, datos,
//        servicioCargaDatosActividad, factoresDeEmisionPorTipoConsumo);
//  }
//
//  @Test()
//  @DisplayName("Prueba de carga de mediciones desde un file csv")
//  public void organizacionLeeCargaDeMedicionesDeArchivoCsvYSeAgregaASusMedicionesCSV() {
//    Organizacion organizacion = unaOrganizacion(servicioCargaDatosActividad);
//    organizacion.setDatosActividad();
//    int cantidadDeDatosDeActividad = organizacion.cantidadDeDatosDeActividad();
//    Assertions.assertEquals(cantidadDeDatosDeActividad, 1);
//
//    Assertions.assertEquals(TipoConsumo.GAS_NATURAL,
//        organizacion.getDatosActividad().get(0).getTipoConsumo());
//    Assertions.assertEquals(Periodicidad.MENSUAL,
//        organizacion.getDatosActividad().get(0).getPeriodicidad());
//    Assertions.assertEquals(LocalDate.of(2020, 2, 1),
//        organizacion.getDatosActividad().get(0).getPeriodoImputacion());
//    Assertions.assertEquals(15412d,
//        organizacion.getDatosActividad().get(0).getValor());
//  }
//
//  @Test
//  @DisplayName("Prueba de carga de con un servicioCargaDatosMock")
//  public void organizacionLeeCargaDeMedicionesDeArchivoCsvYSeAgregaASusMedicionesMock() {
//    DatoActividad datoActividad1 = new DatoActividad("Gas natural", "123", "mensual", "10/2022");
//    DatoActividad datoActividad2 = new DatoActividad("Gas natural", "123", "anual", "2022");
//    DatoActividad datoActividad3 = new DatoActividad("Gas natural", "123", "anual", "2022");
//    DatoActividad datoActividad4 = new DatoActividad("Gas natural", "123", "anual", "2022");
//    DatoActividad datoActividad5 = new DatoActividad("Gas natural", "123", "anual", "2022");
//    DatoActividad datoActividad6_otraFecha = new DatoActividad("Gas natural", "2000", "anual", "1999");
//
//    List<DatoActividad> datos = Arrays.asList(datoActividad1, datoActividad2, datoActividad3, datoActividad4, datoActividad5, datoActividad6_otraFecha);
//    when(servicioCargaDatosActividadMock.obtenerDatosActividad()).thenReturn(datos);
//
//    Organizacion organizacion = unaOrganizacion(servicioCargaDatosActividadMock);
//    organizacion.setDatosActividad();
//
//    int cantidadDeDatosDeActividad = organizacion.cantidadDeDatosDeActividad();
//
//    Assertions.assertEquals(cantidadDeDatosDeActividad, 6);
//    Assertions.assertEquals(datoActividad1.getTipoConsumo(),
//        organizacion.getDatosActividad().get(0).getTipoConsumo());
//    Assertions.assertEquals(datoActividad2.getValor(),
//        organizacion.getDatosActividad().get(0).getValor());
//    Assertions.assertEquals(datoActividad1.getPeriodicidad(),
//        organizacion.getDatosActividad().get(0).getPeriodicidad());
//    Assertions.assertEquals(datoActividad1.getPeriodoImputacion(),
//        organizacion.getDatosActividad().get(0).getPeriodoImputacion());
//    Assertions.assertEquals(datoActividad5.getTipoConsumo(),
//        organizacion.getDatosActividad().get(4).getTipoConsumo());
//  }
//
//  @Test
//  @DisplayName("Prueba calculo huella carbono con datos de actividad")
//  public void organizacionCalculaHuellaCarbonoDeSusActividadesConVariosConsumosDelMismoTipo() {
//    DatoActividad datoActividad1 = new DatoActividad("Gas natural", "123", "mensual", "10/2022");
//    DatoActividad datoActividad2 = new DatoActividad("Gas natural", "123", "anual", "2022");
//    DatoActividad datoActividad3 = new DatoActividad("Gas natural", "123", "anual", "2022");
//    DatoActividad datoActividad4 = new DatoActividad("Gas natural", "123", "anual", "2022");
//    DatoActividad datoActividad5 = new DatoActividad("Gas natural", "123", "anual", "2022");
//
//    //Actividad en otra fecha
//    DatoActividad datoActividad6_otraFecha = new DatoActividad("Gas natural", "2000", "anual", "1999");
//
//    List<DatoActividad> datos = Arrays.asList(datoActividad1, datoActividad2, datoActividad3, datoActividad4, datoActividad5, datoActividad6_otraFecha);
//    when(servicioCargaDatosActividadMock.obtenerDatosActividad()).thenReturn(datos);
//
//    Organizacion organizacion = unaOrganizacion(servicioCargaDatosActividadMock);
//    organizacion.setDatosActividad();
//
//    //Assert
//    int cantidadActividadesEnFecha = organizacion.getDatosActividad().stream().filter(d -> d.perteneceAlPeriodo(LocalDate.of(2022, 10, 1))).collect(Collectors.toList()).size();
//    Double valorCadaActividad = 123d;
//    Double factorEmision = factoresDeEmisionPorTipoConsumo.getFactorEmision(TipoConsumo.GAS_NATURAL).getValor();
//    Double expectedHuellaCarbono = valorCadaActividad * cantidadActividadesEnFecha * factorEmision;
//
//    Double actualHuellaCarbono = organizacion.huellaCarbonoOrganizacion(LocalDate.of(2022, 10, 1));
//    Assertions.assertEquals(expectedHuellaCarbono, actualHuellaCarbono);
//  }
//
//  @Test
//  @DisplayName("Prueba calculo huella carbono con datos de actividad con distintos tipos de consumo")
//  public void organizacionCalculaHuellaCarbonoDeSusActividadesConDistintosTiposConsumo2() {
//    DatoActividad datoActividad1 = new DatoActividad("Gas natural", "50", "mensual", "10/2022");
//    DatoActividad datoActividad2 = new DatoActividad("Diesel", "100", "anual", "2022");
//    DatoActividad datoActividad3 = new DatoActividad("Nafta", "200", "anual", "2022");
//    //Actividad en otra fecha
//    DatoActividad datoDeOtroPeriodo = new DatoActividad("Electricidad", "500", "anual", "1999");
//
//    List<DatoActividad> datos = Arrays.asList(datoActividad1, datoActividad2, datoActividad3, datoDeOtroPeriodo);
//    when(servicioCargaDatosActividadMock.obtenerDatosActividad()).thenReturn(datos);
//
//    Organizacion organizacion = unaOrganizacion(servicioCargaDatosActividadMock);
//    organizacion.setDatosActividad();
//
//    //Assert
//    Double factorEmisionGas = factoresDeEmisionPorTipoConsumo.getFactorEmision(TipoConsumo.GAS_NATURAL).getValor();
//    Double factorEmisionNafta = factoresDeEmisionPorTipoConsumo.getFactorEmision(TipoConsumo.NAFTA).getValor();
//    Double factorEmisionDiesel = factoresDeEmisionPorTipoConsumo.getFactorEmision(TipoConsumo.DIESEL).getValor();
//    Double expectedHuellaCarbono =
//        datoActividad1.getValor() * factorEmisionGas +
//            datoActividad2.getValor() * factorEmisionDiesel +
//            datoActividad3.getValor() * factorEmisionNafta;
//
//    Double actualHuellaCarbono = organizacion.huellaCarbonoOrganizacion(LocalDate.of(2022, 10, 1));
//
//    Assertions.assertEquals(expectedHuellaCarbono, actualHuellaCarbono);
//  }
//
//  @Test()
//  @DisplayName("Prueba de carga de mediciones desde un file csv")
//  public void organizacionHuellaCarbono() {
//
//    Organizacion organizacion = unaOrganizacion(servicioCargaDatosActividad);
//    organizacion.setDatosActividad();
//    int cantidadDeDatosDeActividad = organizacion.cantidadDeDatosDeActividad();
//    Assertions.assertEquals(cantidadDeDatosDeActividad, 1);
//
//    Assertions.assertEquals(30824.0,
//        organizacion.huellaCarbonoOrganizacion(LocalDate.of(2020, 2, 1)));
//    Assertions.assertEquals(0,
//        organizacion.huellaCarbonoOrganizacion(LocalDate.of(1999, 2, 1)));
//  }
//
//  @Test()
//  @DisplayName("Unidad incompatible entre el Tipo de consumo y el factor de emision ")
//  public void unidadIncompatible() {
//    assertThrows(RuntimeException.class, () -> {
//      this.factoresDeEmisionPorTipoConsumo = new FactoresDeEmisionPorTipoConsumo(new HashMap<>());
//      this.factoresDeEmisionPorTipoConsumo.agregarFactorEmision(
//          TipoConsumo.GAS_NATURAL,
//          new FactorDeEmision("Gas", 2d, Unidad.km, Magnitud.kg));
//    });
//  }
//
//  @Test
//  @DisplayName("Prueba calculo huella carbono con datos de actividad + huella carbono miembros")
//  public void organizacionCalculaHuellaCarbonoDeSusActividadesConDistintosTiposConsumo() {
//    DatoActividad datoActividad1 = new DatoActividad("Gas natural", "50", "mensual", "02/2022");
//
//    List<DatoActividad> datos = Arrays.asList(datoActividad1);
//    when(servicioCargaDatosActividadMock.obtenerDatosActividad()).thenReturn(datos);
//
//    Organizacion organizacion = unaOrganizacion(servicioCargaDatosActividadMock);
//    organizacion.setDatosActividad();
//
//    Miembro miembro = new Miembro("Javier", "Fernandez", "39393939", TipoDocumento.DNI);
//    TransportePublico tp = new TransportePublico(TipoTransportePublico.COLECTIVO, new Linea(TipoTransportePublico.COLECTIVO, "89", new ArrayList<>()), 2d, TipoCombustible.NAFTA);
//    Tramo tramo = mock(Tramo.class);
//    when(tramo.huellaCarbono()).thenReturn(10d);
//    Tramo tramo2 = mock(Tramo.class);
//    when(tramo2.huellaCarbono()).thenReturn(40d);
//    List<Tramo> tramos = new ArrayList<>();
//    tramos.add(tramo);
//    tramos.add(tramo2);
//    Trayecto trayecto = new Trayecto(tramos);
//    trayecto.setFechaTrayecto(LocalDate.of(2022, 2, 1));
//    miembro.nuevoTrayecto(trayecto, new ArrayList<>());
//
//    //Assert
//    Double factorEmisionGas = factoresDeEmisionPorTipoConsumo.getFactorEmision(TipoConsumo.GAS_NATURAL).getValor();
//
//    Double expectedHuellaCarbono = datoActividad1.getValor() * factorEmisionGas;
//
//    Double actualHuellaCarbono = organizacion.huellaCarbonoOrganizacion(LocalDate.of(2022, 2, 1));
//
//    Assertions.assertEquals(expectedHuellaCarbono, actualHuellaCarbono);
//  }
}
