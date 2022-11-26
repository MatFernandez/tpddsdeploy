package controllers;

import dominio.common.DatoActividad;
import dominio.common.TipoConsumo;
import dominio.medicion.FactorDeEmision;
import dominio.medicion.FactoresDeEmisionPorTipoConsumo;
import dominio.medicion.enums.Magnitud;
import dominio.medicion.enums.Unidad;
import dominio.organizacion.Miembro;
import dominio.organizacion.Sector;
import dominio.trayecto.Tramo;
import dominio.trayecto.Trayecto;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.*;
import java.util.stream.Collectors;

public class CalculadoraHcController implements WithGlobalEntityManager, TransactionalOps {


  public ModelAndView verPeriodos(Request request, Response response) {
    return new ModelAndView(
        null,
        "calcular-hc.hbs");
  }


  public ModelAndView calcularHC(Request request, Response response) {
    String periodo = request.queryParams("periodo");
    String[] periodoBuscado = periodo.split("/");
    String mes = periodoBuscado[0];
    String anio = periodoBuscado[1];
    int anioEnDecimal = Integer.parseInt(anio);
    int mesEnDecimal = Integer.parseInt(mes);
    List<DatoActividad> datosActividad = DatosActividadRepositorio.getInstance().getDatosActividad();
    if (mes.equals("00")) {
      List<DatoActividad> datosAnualizados = datosActividad.stream().filter(datoActividad ->
          datoActividad.getPeriodoImputacion().getYear() == anioEnDecimal).collect(Collectors.toList());
      List<TipoConsumo> tiposDeConsumo = datosAnualizados.stream().map(DatoActividad::getTipoConsumo)
          .distinct().collect(Collectors.toList());
      List<FactoresDeEmisionPorTipoConsumo> factores = this.repositorioFactoresEmision().stream()
          .filter(factor -> tiposDeConsumo.contains(factor.getTipoConsumo()))
          .collect(Collectors.toList());

      Double huellaDatosActividad = datosAnualizados.stream().mapToDouble(dato -> dato.getValor() *
          factores.stream().
              filter(factor -> factor.getTipoConsumo().equals(dato.getTipoConsumo())).findFirst()
              .get().getFactoresDeEmision().getValor()).sum();


      Magnitud magnitud = repositorioFactoresEmision().get(0).getFactoresDeEmision().magnitud;

      List<Sector> sectores = SectorRepositorio.getInstance().getSectores(); //Traigo los sectores de la bd


      List<List<Miembro>> miembrosFiltrados = sectores.stream().map(Sector::getMiembros).collect(Collectors.toList());
      List<Miembro> miembrosAplanados = miembrosFiltrados.stream().flatMap(Collection::stream).collect(Collectors.toList());

      List<List<Trayecto>> trayectosFiltrados = miembrosAplanados.stream().map(Miembro::getTrayectos).collect(Collectors.toList());
      List<Trayecto> trayectosAplanados = trayectosFiltrados.stream().flatMap(Collection::stream).collect(Collectors.toList());

      List<List<Tramo>> tramosFiltrados = trayectosAplanados.stream().map(Trayecto::getTramos).collect(Collectors.toList());
      List<Tramo> tramosAplanados = tramosFiltrados.stream().flatMap(Collection::stream).collect(Collectors.toList());


      Double huellaTotalSectores = tramosAplanados.stream().mapToDouble(tramo -> tramo.combustibleGastado() * huellaDatosActividad).sum();

      Double huellaTotalOrganizacion = huellaTotalSectores + huellaDatosActividad;

      Map<String, Object> model = new HashMap<>();
      model.put("datosActividad", datosAnualizados);
      model.put("factoresEmision", repositorioFactoresEmision().get(0).getFactoresDeEmision());
      model.put("huellaDatos", huellaDatosActividad);
      model.put("magnitudDatos", magnitud);
      model.put("huellaSectores", huellaTotalSectores);
      model.put("magnitudSectores", magnitud);
      model.put("huellaTotal", huellaTotalOrganizacion);
      model.put("magnitudTotal", magnitud);
      model.put("magnitud", magnitud);
      return new ModelAndView(
          model,
          "calcular-hc.hbs");
    }


    List<DatoActividad> datosMensualizados = datosActividad.stream().filter(datoActividad -> datoActividad.getPeriodoImputacion().getYear() == anioEnDecimal
        && datoActividad.getPeriodoImputacion().getMonthValue() == mesEnDecimal).collect(Collectors.toList());
    List<TipoConsumo> tiposDeConsumo = datosMensualizados.stream().map(DatoActividad::getTipoConsumo).distinct().collect(Collectors.toList());

    List<FactoresDeEmisionPorTipoConsumo> factores = repositorioFactoresEmision().stream()
        .filter(factor -> tiposDeConsumo.contains(factor.getTipoConsumo()))
        .collect(Collectors.toList());

    Double huellaDatosActividad = datosMensualizados.stream().mapToDouble(dato -> dato.getValor() *
        factores.stream().
            filter(factor -> factor.getTipoConsumo().equals(dato.getTipoConsumo())).findFirst()
            .get().getFactoresDeEmision().getValor()).sum();


    Magnitud magnitud = repositorioFactoresEmision().get(0).getFactoresDeEmision().magnitud;


    List<Sector> sectores = SectorRepositorio.getInstance().getSectores(); //Traigo los sectores de la bd


    List<List<Miembro>> miembrosFiltrados = sectores.stream().map(Sector::getMiembros).collect(Collectors.toList());
    List<Miembro> miembrosAplanados = miembrosFiltrados.stream().flatMap(Collection::stream).collect(Collectors.toList());

    List<List<Trayecto>> trayectosFiltrados = miembrosAplanados.stream().map(Miembro::getTrayectos).collect(Collectors.toList());
    List<Trayecto> trayectosAplanados = trayectosFiltrados.stream().flatMap(Collection::stream).collect(Collectors.toList());

    List<List<Tramo>> tramosFiltrados = trayectosAplanados.stream().map(Trayecto::getTramos).collect(Collectors.toList());
    List<Tramo> tramosAplanados = tramosFiltrados.stream().flatMap(Collection::stream).collect(Collectors.toList());


    Double huellaTotalSectores = tramosAplanados.stream().mapToDouble(tramo -> tramo.combustibleGastado() * huellaDatosActividad).sum();

    Double huellaTotalOrganizacion = huellaTotalSectores + huellaDatosActividad;


    Map<String, Object> model = new HashMap<>();
    model.put("datosActividad", datosMensualizados);
    model.put("factoresEmision", repositorioFactoresEmision().get(0).getFactoresDeEmision());
    model.put("huellaDatos", huellaDatosActividad);
    model.put("magnitudDatos", magnitud);
    model.put("huellaSectores", huellaTotalSectores);
    model.put("magnitudSectores", magnitud);
    model.put("huellaTotal", huellaTotalOrganizacion);
    model.put("magnitudTotal", magnitud);
    model.put("magnitud", magnitud);
    return new ModelAndView(
        model,
        "calcular-hc.hbs");

  }


  public List<FactoresDeEmisionPorTipoConsumo> repositorioFactoresEmision(){
    return FactoresEmisionRepositorio.getInstance().repositorioFactoresEmision();
  }
}
