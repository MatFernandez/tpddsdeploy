package controllers;

import dominio.administrador.Administrador;
import dominio.administrador.Rol;
import dominio.common.DatoActividad;
import dominio.organizacion.Organizacion;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.DatosActividadRepositorio;
import repositorios.OrganizacionRepositorio;
import repositorios.UsuarioRepositorio;
import servicios.ServicioCargaDatosActividad;
import servicios.ServicioCargarDatosActividadMedianteCSV;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class DatosActividadController implements WithGlobalEntityManager, TransactionalOps {

  public ModelAndView verDatoActividad(Request request, Response response) {
    Administrador usuarioLogin = UsuarioRepositorio.getInstance().getAdministrador(
        request.session().attribute("user"));
    Organizacion org = usuarioLogin.getOrganizacion();
    Map model = new HashMap();
    model.put("datosActividad",org.getDatosActividad());
    return new ModelAndView(model,
        "cargar-dato-actividad.html.hbs");
  }

  public ModelAndView cargaDatoActividad(Request request, Response response) {
    String tipoConsumo = request.queryParams("tipo_consumo");
    String valor = request.queryParams("valor");
    String periocidad = request.queryParams("periocidad");
    String periodoImputacion = request.queryParams("periodo_imputacion");

    withTransaction(() -> {
      Administrador usuarioLogin = UsuarioRepositorio.getInstance().getAdministrador(
          request.session().attribute("user"));
      Organizacion org = usuarioLogin.getOrganizacion();
      org.getDatosActividad().add(new DatoActividad(tipoConsumo, valor, periocidad, periodoImputacion));
      OrganizacionRepositorio.getInstance().guardarListaDatoActividad(org.getDatosActividad());
    });
    List<DatoActividad> datosActividad = DatosActividadRepositorio.getInstance().getDatosActividad();
    System.out.println(datosActividad.get(0).getValor());
    Map<String, Object> model = new HashMap<>();
    model.put("datosActividad", datosActividad);
    return new ModelAndView(
        model,
        "cargar-dato-actividad.html.hbs");
  }

  public ModelAndView cargaCSV(Request request, Response response)
      throws ServletException, IOException {
    if (!Objects.isNull(request.contentType()) && request.contentType().contains("multipart")) {

      long maxFileSize = 5242880;
      long maxRequestSize = 10485760;
      int fileSizeThreshold = 1024;

      MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
          null, maxFileSize, maxRequestSize, fileSizeThreshold);
      request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
          multipartConfigElement);
    }
    Part p = request.raw().getPart("fileSelect");

    byte[] file = null;
    try {
      InputStream inputStream = p.getInputStream();
      file = IOUtils.toByteArray(inputStream);
    } catch (final IOException e) {
    }

    ServicioCargarDatosActividadMedianteCSV servicioCargaDatosActividad = new ServicioCargarDatosActividadMedianteCSV();
    List<DatoActividad> datoActividads = servicioCargaDatosActividad.convertCSVFromBytes(file);


    withTransaction(() -> {
      DatosActividadRepositorio.getInstance().guardarListaDatoActividad(datoActividads);
    });
    List<DatoActividad> datosActividad = DatosActividadRepositorio.getInstance().getDatosActividad();
    System.out.println(datosActividad.get(0).getValor());
    Map<String, Object> model = new HashMap<>();
    model.put("datosActividad", datosActividad);
    return new ModelAndView(
        model,
        "cargar-dato-actividad.html.hbs");
  }

}
