package controllers;

import dominio.administrador.Administrador;
import dominio.organizacion.Miembro;
import dominio.organizacion.Organizacion;
import dominio.organizacion.Sector;
import dominio.organizacion.SolicitudVinculacion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.MiembroRepositorio;
import repositorios.OrganizacionRepositorio;
import repositorios.SolicitudVinculacionRepositorio;
import repositorios.UsuarioRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class OrganizacionSolicitudesController implements WithGlobalEntityManager, TransactionalOps {

  public ModelAndView verSolicitudesMiembrosParaVincular(Request request, Response response) {
    List<SolicitudVinculacion> solicitudVinculaciones = SolicitudVinculacionRepositorio.getInstance().getSolicitudVinculacionPendientes();

    Administrador usuarioLogin = UsuarioRepositorio.getInstance().getAdministrador(
        request.session().attribute("user"));
    Organizacion org = OrganizacionRepositorio.getInstance().getOrganizacion(usuarioLogin.getOrganizacion().getId()).get(0);

    Map model = new HashMap<>();
    model.put("solicitudes", solicitudVinculaciones);
    model.put("sectores", org.getSectores() );
    return new ModelAndView(
        model,
        "organizacion-acepta-solicitudes.html.hbs");

  }

  public ModelAndView aceptarVinculacion(Request request, Response response) {
    List<String> params = request.queryParams().stream().filter(param->param.startsWith("solicitud")).collect(
        Collectors.toList());

    List<String> idSolicitudesParaAprobar = params.stream().map(param-> request.queryParams(param)).collect(
        Collectors.toList());
    if(!idSolicitudesParaAprobar.isEmpty()){

      List<SolicitudVinculacion> solicitudVinculaciones = SolicitudVinculacionRepositorio.getInstance().getSolicitudVinculacion();
      List<SolicitudVinculacion> solicitudVinculacionesParaAprobar = solicitudVinculaciones.stream().filter(solicitudVinculacion -> idSolicitudesParaAprobar.stream().anyMatch(val->val.equals(solicitudVinculacion.getId().toString()))).collect(
          Collectors.toList());

      solicitudVinculacionesParaAprobar.forEach(solicitud ->{
            withTransaction(() -> {
              SolicitudVinculacionRepositorio.getInstance().aprobarSolicitud(solicitud);
              Organizacion organizacion = solicitud.getOrganizacion();
              Sector sector = organizacion.getSectores().stream().filter(sect->sect.getId().toString().equals(request.queryParams("sector"))).collect(Collectors.toList()).get(0);
              sector.addMiembro(solicitud.getMiembro());

              OrganizacionRepositorio.getInstance().guardarOrganizacion(organizacion);

            });


    });
    }


    response.redirect("/solicitudes_pendientes");
    return null;
  }

}
