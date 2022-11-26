package controllers;

import dominio.administrador.MiembroRol;
import dominio.organizacion.Miembro;
import dominio.organizacion.Organizacion;
import dominio.organizacion.SolicitudVinculacion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.OrganizacionRepositorio;
import repositorios.SolicitudVinculacionRepositorio;
import repositorios.UsuarioRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MiembroController implements WithGlobalEntityManager, TransactionalOps {

  public ModelAndView verOrganizacionesParaVincular(Request request, Response response) {
    List<Organizacion> organizaciones = OrganizacionRepositorio.getInstance().getOrganizaciones();
    Map model = new HashMap<>();
    model.put("organizaciones", organizaciones);
    List<SolicitudVinculacion> solicitudVinculaciones = SolicitudVinculacionRepositorio.getInstance().getSolicitudVinculacion();
    model.put("solicitudes", solicitudVinculaciones);
    return new ModelAndView(
        model,
        "miembro-solicita-vinculacion.html.hbs");

  }

  public ModelAndView vincularme(Request request, Response response) {
    MiembroRol usuarioLogin = UsuarioRepositorio.getInstance().getMiembroRol(
        request.session().attribute("user"));

    Miembro miembro = usuarioLogin.getMiembro();
    List<Organizacion> organizaciones = OrganizacionRepositorio.getInstance().getOrganizaciones();
    Organizacion selected = organizaciones.stream().filter(organizacion-> organizacion.getId().toString().equals(request.queryParams("idOrganizacion"))).findFirst().get();

    withTransaction(() -> {
      SolicitudVinculacionRepositorio.getInstance().crearSolicitud(selected,miembro);

    });
    List<SolicitudVinculacion> solicitudVinculaciones = SolicitudVinculacionRepositorio.getInstance().getSolicitudVinculacion();

    Map model = new HashMap<>();
    model.put("organizaciones", organizaciones);
    model.put("solicitudes", solicitudVinculaciones);
    return new ModelAndView(
        model,
        "miembro-solicita-vinculacion.html.hbs");
  }

}
