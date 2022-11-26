package controllers;

import dominio.organizacion.Organizacion;
import dominio.organizacion.enums.TipoOrganizacion;
import dominio.sectorTerritorial.SectorTerritorial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import repositorios.OrganizacionRepositorio;
import repositorios.SectorTerritorialRepositorio;
import servicios.ReporteServicio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ReportesController {

  private ReporteServicio reporteServicio;

  public ReportesController(ReporteServicio reporteServicio) {
    this.reporteServicio = reporteServicio;
  }

  public ModelAndView verReportes(Request request, Response response) {

    Map model = new HashMap<>();

    return new ModelAndView(
        model,
        "reportes/gestor-reportes.html.hbs");

  }



  public ModelAndView huellaCarbonoPorST(Request request, Response response) {

    List<SectorTerritorial> sectorTerritorialList = SectorTerritorialRepositorio.getInstance().getSectoresTerritoriales();
    String st = request.queryParams("sector_territorial");
    Double total = reporteServicio.getHuellaPorSectorTerritorial(st);

    String sectorTerritorial = request.queryParams("sector_territorial");
    Map model = new HashMap<>();

    model.put("sectorElegido", st);
    model.put("total", total);
    model.put("sectoresTerritoriales", sectorTerritorialList);
    return new ModelAndView(
        model,
        "reportes/total-st.html.hbs");


  }


  public ModelAndView huellaCarbonoPorTipoOrganizacion(Request request, Response response) {


    String tipoOrg = request.queryParams("tipoOrganizacion");
    Double huella = reporteServicio.getHuellaPorTipoOrganizacion(tipoOrg);
    Map model = new HashMap<>();
    List<TipoOrganizacion> tipoOrganizacions = new ArrayList<>(Arrays.asList(TipoOrganizacion.ONG,TipoOrganizacion.EMPRESA,TipoOrganizacion.GUBERNAMENTAL,TipoOrganizacion.INSTITUCION));
    model.put("tipos", tipoOrganizacions);
    model.put("tipoOrganizacions", tipoOrganizacions);
    model.put("tipoOrg", tipoOrg);
    model.put("huellaTotal", huella);
    return new ModelAndView(
        model,
        "reportes/total-tipo-org.html.hbs");

  }

  public ModelAndView composicionHuellaCarbonoPorOrganizacionDeUnSectorTerritorial(Request request, Response response) {
    List<SectorTerritorial> sectorTerritorialList = SectorTerritorialRepositorio.getInstance().getSectoresTerritoriales();
    String st = request.queryParams("sector_territorial");
    Map<String, Double> composicion = reporteServicio.getComposicionHuellaSectorTerritorialPorOrganizacion(st);

    String sectorTerritorial = request.queryParams("sector_territorial");
    Map model = new HashMap<>();

    model.put("composicion", composicion);
    model.put("sectoresTerritoriales", sectorTerritorialList);
    return new ModelAndView(
        model,
        "reportes/composicion-st.html.hbs");

  }


  public ModelAndView composicionHuellaCarbonoOrganizacionPorSector(Request request, Response response) {

    List<Organizacion> organizaciones = OrganizacionRepositorio.getInstance().getOrganizaciones();
    String orgName = request.queryParams("organizacion");
    Map model = new HashMap<>();
    model.put("organizaciones", organizaciones);
    if(orgName!=null){

      Map<String, Double> composicion = reporteServicio.getComposicionHuellaOrganizacionPorSector(orgName);


      model.put("composicion", composicion);
    }
    return new ModelAndView(
        model,
        "reportes/composicion-organizacion.html.hbs");

  }


  public ModelAndView evolucionHuellaCarbonoOrganizacionPorSector(Request request, Response response) {

    List<Organizacion> organizaciones = OrganizacionRepositorio.getInstance().getOrganizaciones();
    String orgName = request.queryParams("organizacion");
    Map model = new HashMap<>();
    model.put("organizaciones", organizaciones);

    if(orgName!=null){
      Map<String, Double> evolucionHuellaOrganizacion = reporteServicio.getEvolucionHuellaOrganizacion(orgName);
      model.put("evolucion", evolucionHuellaOrganizacion);
    }


    return new ModelAndView(
        model,
        "reportes/evolucion-organizacion.html.hbs");

  }


  public ModelAndView evolucionHuellaCarbonoSectorTerritorial(Request request, Response response) {

    List<SectorTerritorial> sectorTerritorialList = SectorTerritorialRepositorio.getInstance().getSectoresTerritoriales();
    String st = request.queryParams("sector_territorial");
    Map<String, Double> evolucionSectorTerritorial = reporteServicio.getEvolucionSectorTerritorial(st);

    Map model = new HashMap<>();

    model.put("evolucion", evolucionSectorTerritorial);
    model.put("sectoresTerritoriales", sectorTerritorialList);
    return new ModelAndView(
        model,
        "reportes/evolucion-st.html.hbs");
  }


}
