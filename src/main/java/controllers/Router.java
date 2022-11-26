package controllers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import servicios.ReporteServicio;
import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Router {

  public static final List<String> ROUTES_ONLY_FOR_ADMIN = new ArrayList<>(
      Arrays.asList("/dato_actividad", "/cargar_csv", "/cargar_dato_actividad", "/aprobar_solicitud", "/solicitudes_pendientes", "/huella_carbono_st", "/gestor_reportes"
  ));

  public static void main(String[] args) {
      new Bootstrap().run();
      Spark.staticFileLocation("/public");
      Spark.port(9000);
      Spark.init();
      TrayectoController trayectoController = new TrayectoController();
      TramosController tramosController = new TramosController();
      DatosActividadController datosActividadController = new DatosActividadController();

      MiembroController miembroController = new MiembroController();
      OrganizacionSolicitudesController organizacionSolicitudesController = new OrganizacionSolicitudesController();
      PerfilController perfilController = new PerfilController();
      ControllerSesion controllerSesion = new ControllerSesion();
      ReportesController reportesController = new ReportesController(new ReporteServicio());

      CalculadoraHcController calculadoraHcController = new CalculadoraHcController();

      HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
      RecomendacionesController recomendacionesController = new RecomendacionesController();

      Spark.before("*",(req,res)->{
        if (!req.uri().equalsIgnoreCase("/login")) {
          Long user = req.session().attribute("user");
          if (user == null) {
            res.redirect("/login");
            Spark.halt();
          }
        }
      });

      Spark.before((req,res)->{
        Long user = req.session().attribute("user");
        String rol = req.session().attribute("rol");
        if(user!=null && rol != null){
          if (ROUTES_ONLY_FOR_ADMIN.stream().anyMatch(uri->uri.equals(req.uri())) && rol.equalsIgnoreCase("M") ) {
              res.redirect("/perfil");
              Spark.halt();
          }
        }
      });



      Spark.get("/tramos", tramosController::verTramos, engine);
      Spark.post("/registra_tramo", tramosController::registraTramo, engine);

      Spark.get("/trayecto", trayectoController::verTrayectos, engine);
      Spark.post("/trayecto", trayectoController::registraTuTrayecto, engine);

      Spark.get("/dato_actividad", datosActividadController::verDatoActividad, engine);
      Spark.post("/cargar_dato_actividad", datosActividadController::cargaDatoActividad, engine);
      Spark.post("/cargar_csv", datosActividadController::cargaCSV, engine);
      Spark.get("/crear_solicitud", miembroController::verOrganizacionesParaVincular, engine);
      Spark.post("/crear_solicitud", miembroController::vincularme, engine);

      Spark.get("/solicitudes_pendientes", organizacionSolicitudesController::verSolicitudesMiembrosParaVincular, engine);
      Spark.post("/aprobar_solicitud", organizacionSolicitudesController::aceptarVinculacion, engine);


      Spark.get("/gestor_reportes", reportesController::verReportes, engine);
      Spark.get("/huella_carbono_st", reportesController::huellaCarbonoPorST, engine);
      Spark.post("/huella_carbono_st", reportesController::huellaCarbonoPorST, engine);
      Spark.get("/huella_carbono_total_tipo_organizacion", reportesController::huellaCarbonoPorTipoOrganizacion, engine);
      Spark.post("/huella_carbono_total_tipo_organizacion", reportesController::huellaCarbonoPorTipoOrganizacion, engine);
      Spark.get("/huella_carbono_composicion_st", reportesController::composicionHuellaCarbonoPorOrganizacionDeUnSectorTerritorial, engine);
      Spark.post("/huella_carbono_composicion_st", reportesController::composicionHuellaCarbonoPorOrganizacionDeUnSectorTerritorial, engine);
      Spark.get("/huella_carbono_composicion_organizacion", reportesController::composicionHuellaCarbonoOrganizacionPorSector, engine);
      Spark.post("/huella_carbono_composicion_organizacion", reportesController::composicionHuellaCarbonoOrganizacionPorSector, engine);
      Spark.get("/huella_carbono_evolucion_st", reportesController::evolucionHuellaCarbonoSectorTerritorial, engine);
      Spark.post("/huella_carbono_evolucion_st", reportesController::evolucionHuellaCarbonoSectorTerritorial, engine);
      Spark.get("/huella_carbono_evolucion_organizacion", reportesController::evolucionHuellaCarbonoOrganizacionPorSector, engine);
      Spark.post("/huella_carbono_evolucion_organizacion", reportesController::evolucionHuellaCarbonoOrganizacionPorSector, engine);

      Spark.get("/perfil", perfilController::mostrar, engine);
      Spark.get("/login", controllerSesion::mostrarLogin, engine);
      Spark.post("/login", controllerSesion::iniciarSesion, engine);
      Spark.post("/cerrar_sesion", controllerSesion::cerrarSesion, engine);

      Spark.after((req,res)->{
        PerThreadEntityManagers.getEntityManager().clear();
      });

      Spark.get("/periodos", calculadoraHcController::verPeriodos, engine);
      Spark.post("/calcular_hc", calculadoraHcController::calcularHC, engine);


      Spark.get("/recomendaciones", recomendacionesController::verRecomendaciones,engine);


      DebugScreen.enableDebugScreen();

    }

}
