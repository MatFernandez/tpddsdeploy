package servicios;

import dominio.common.DatoActividad;
import dominio.common.TipoConsumo;
import dominio.medicion.FactorDeEmision;
import dominio.medicion.FactoresDeEmisionPorTipoConsumo;
import dominio.medicion.enums.Magnitud;
import dominio.medicion.enums.Unidad;
import dominio.organizacion.Miembro;
import dominio.organizacion.Organizacion;
import dominio.organizacion.Sector;
import dominio.trayecto.Tramo;
import dominio.trayecto.Trayecto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import repositorios.FactoresEmisionRepositorio;
import repositorios.OrganizacionRepositorio;
import repositorios.SectorTerritorialRepositorio;

public class ReporteServicio {

  public Double getHuellaPorSectorTerritorial(String nombreSectorTerritorial) {
    List<Organizacion> organizaciones = SectorTerritorialRepositorio.getInstance().getSectoresTerritorialesByName(nombreSectorTerritorial).stream().flatMap(sectorTerritorial -> sectorTerritorial.getOrganizaciones().stream()).collect(
        Collectors.toList());

    Double huellaTotalSectorTerritorial = getHuellaOrganizaciones(organizaciones);

    return  huellaTotalSectorTerritorial;
  }

  public Double getHuellaPorTipoOrganizacion(String tipoOrganizacion) {
    List<Organizacion> organizaciones = OrganizacionRepositorio.getInstance().getOrganizaciones().stream().filter(organizacion -> organizacion.getTipoOrganizacion().name().equals(tipoOrganizacion)).collect(
        Collectors.toList());

    Double huellaTotalSectorTerritorial = getHuellaOrganizaciones(organizaciones);

    return  huellaTotalSectorTerritorial;
  }

  public Map<String, Double> getComposicionHuellaOrganizacionPorSector(String nombre) {
    Organizacion organizacion = OrganizacionRepositorio.getInstance().getOrganizaciones().stream().filter(org -> org.getRazonSocial().equals(nombre)).findFirst().get();

    Map<String, Double> composicion = new HashMap<>();
    organizacion.getSectores().forEach(sector -> {
      Double huella = getHuellaPorSector(sector);
      composicion.put(sector.getNombreSector(), huella);
    });

    return  composicion;
  }

  public Map<String, Double> getComposicionHuellaSectorTerritorialPorOrganizacion(String nombreSectorTerritorial) {
    List<Organizacion> organizaciones = SectorTerritorialRepositorio.getInstance().getSectoresTerritorialesByName(nombreSectorTerritorial).stream().flatMap(sectorTerritorial -> sectorTerritorial.getOrganizaciones().stream()).collect(
        Collectors.toList());

    Map<String, Double> composicion = new HashMap<>();
    organizaciones.forEach(organizacion -> {
      Double huella = getHuellaOrganizacion(organizacion);
      composicion.put(organizacion.getRazonSocial(), huella);
    });


    return  composicion;
  }

  public Map<String, Double> getEvolucionHuellaOrganizacion(String nombre) {
    Map<String,Double> huellaPorMes = new HashMap<>();
    Organizacion organizacion = OrganizacionRepositorio.getInstance().getOrganizaciones().stream().filter(org -> org.getRazonSocial().equals(nombre)).findFirst().get();

    return getHuellaEvolucion(organizacion.getDatosActividad(), organizacion.getSectores(), huellaPorMes);
  }

  public Map<String, Double> getEvolucionSectorTerritorial(String nombreSectorTerritorial){
    Map<String,Double> huellaPorMes = new HashMap<>();
    List<Organizacion> organizaciones = SectorTerritorialRepositorio.getInstance().getSectoresTerritorialesByName(nombreSectorTerritorial).stream().flatMap(sectorTerritorial -> sectorTerritorial.getOrganizaciones().stream()).collect(
        Collectors.toList());

    List<DatoActividad> datoActividad = organizaciones.stream().flatMap(organizacion -> organizacion.getDatosActividad().stream()).collect(
        Collectors.toList());

    List<Sector> sectores = organizaciones.stream().flatMap(organizacion -> organizacion.getSectores().stream()).collect(
        Collectors.toList());

    return getHuellaEvolucion(datoActividad, sectores ,huellaPorMes);
  }

  private Double getHuellaOrganizaciones(List<Organizacion> organizaciones) {
    return organizaciones.stream().mapToDouble(organizacion->getHuellaOrganizacion(organizacion)).sum();
  }

  private Double getHuellaOrganizacion(Organizacion organizacion) {
    List<Sector> sectores = organizacion.getSectores();

    List<DatoActividad> datosActividad = organizacion.getDatosActividad();

    List<TipoConsumo> tiposDeConsumo = datosActividad.stream().map(DatoActividad::getTipoConsumo)
        .distinct().collect(Collectors.toList());
    List<FactoresDeEmisionPorTipoConsumo> factores = this.repositorioFactoresEmision().stream()
        .filter(factor -> tiposDeConsumo.contains(factor.getTipoConsumo()))
        .collect(Collectors.toList());

    Double huellaDatosActividad = datosActividad.stream().mapToDouble(dato -> dato.getValor() *
        factores.stream().
            filter(factor -> factor.getTipoConsumo().equals(dato.getTipoConsumo())).findFirst()
            .get().getFactoresDeEmision().getValor()).sum();

    Double huellaTotalPorTransporte = sectores.stream().mapToDouble(sector->getHuellaPorSector(sector)).sum();

    return huellaTotalPorTransporte + huellaDatosActividad;
  }


  private Double getHuellaPorSector(Sector sector) {

    List<Tramo> listaTramosOrganizacion = sector.getMiembros().stream()
        .map(Miembro::getTrayectos)
        .flatMap(Collection::stream)
        .map(Trayecto::getTramos)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());


    Double huellaTotalPorTransporte = listaTramosOrganizacion.stream().mapToDouble(
        tramo -> tramo.combustibleGastado() * tramo.getTipoDeTransporte()
            .getConsumoDeCombustiblePorKm()).sum();

    return huellaTotalPorTransporte;
  }


  private Map<String, Double> getHuellaEvolucion(List<DatoActividad> datosActividad, List<Sector> sectores,  Map<String, Double> huellaPorMes) {

    getHuellaDatoActividadPorMes(datosActividad, huellaPorMes);

    getHuellaTrayectoPorMes(sectores, huellaPorMes);

    return huellaPorMes;
  }

  private void getHuellaDatoActividadPorMes(List<DatoActividad> datosActividad, Map<String, Double> huellaPorMes) {
    List<TipoConsumo> tiposDeConsumo = datosActividad.stream().map(DatoActividad::getTipoConsumo)
        .distinct().collect(Collectors.toList());

    List<FactoresDeEmisionPorTipoConsumo> factores = this.repositorioFactoresEmision().stream()
        .filter(factor -> tiposDeConsumo.contains(factor.getTipoConsumo()))
        .collect(Collectors.toList());

    datosActividad.forEach(dato -> {
       Double huella = dato.getValor() *
           factores.stream().
               filter(factor -> factor.getTipoConsumo().equals(dato.getTipoConsumo())).findFirst()
               .get().getFactoresDeEmision().getValor();
      huellaPorMes.merge(dato.getPeriodoImputacion().toString(), huella, (huellaOld, huellaNew) -> huellaNew + huellaOld);
     });
  }

  private void getHuellaTrayectoPorMes(List<Sector> sectores, Map<String, Double> huellaPorMes) {
    List<Trayecto> trayectos = sectores.stream().flatMap(sector -> sector.getMiembros().stream())
        .map(Miembro::getTrayectos)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());

    trayectos.forEach(trayecto -> {
      Double huellaPorTramo = trayecto.huellaCarbono();
      String fechaTramo = trayecto.getFechaTrayecto().toString();
      huellaPorMes.merge(fechaTramo, huellaPorTramo, (huellaOld, huellaNew) -> huellaNew + huellaOld);

    });
  }


  public List<FactoresDeEmisionPorTipoConsumo> repositorioFactoresEmision() {
    return FactoresEmisionRepositorio.getInstance().repositorioFactoresEmision();
  }

}

//  dropdownt para la pantalla de registra datos de actividad
//    los botones mas chicos/formulario mas chico(UX)
//    colores del login(UX)
//    arreglar lo de los trayectos(que se pueda agregar los transportes)
//
//    cambiar el false por pendiente y tu por Aprobado
//    reportes 1-
//    2-
//    3-por miembro del sector
//    4-por sectores de la organizacion
//    5-indicar un periodo de anio a anio y mostrar
//    la evolucion en el tiempo (los totales)
//    6-
//
//    que solo puedas entrar con el usuario indicado en cada url
//    1-hacerlo por controlers
//    2-hacerlo por filtro
//    3-redireccion a la pag 404
