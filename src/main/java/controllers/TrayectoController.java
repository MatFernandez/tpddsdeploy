package controllers;

import dominio.organizacion.SolicitudVinculacion;
import dominio.trayecto.Tramo;
import dominio.trayecto.Trayecto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import repositorios.TramoRepositorio;
import repositorios.TrayectoRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class TrayectoController {

  public ModelAndView verTrayectos(Request request, Response response) {
    List<Tramo> tramos = TramoRepositorio.getInstance().getTramos();
    Map model = new HashMap<>();
    List<Trayecto> trayectos = TrayectoRepositorio.getInstance().getTrayectos();
    model.put("tramosDisponibles", tramos);
    model.put("trayectos", trayectos);
    return new ModelAndView(
        model,
        "crear-trayecto.html.hbs");

  }

  public ModelAndView registraTuTrayecto(Request request, Response response) {
    String[] values = request.queryParamsValues("tramos");
    String nombreTrayecto = request.queryParams("nombreTrayecto");

    List<Tramo> tramos = TramoRepositorio.getInstance().getTramos();
    List<Tramo> solicitudVinculacionesParaAprobar = tramos.stream().filter(tramo -> Arrays.stream(
        values).anyMatch(val->val.equals(tramo.getId().toString()))).collect(
        Collectors.toList());

    TrayectoRepositorio.getInstance().crearTrayecto(new Trayecto(solicitudVinculacionesParaAprobar, nombreTrayecto));

    List<Trayecto> trayectos = TrayectoRepositorio.getInstance().getTrayectos();
    Map model = new HashMap<>();
    model.put("tramosDisponibles", tramos);
    model.put("trayectos", trayectos);
    return new ModelAndView(
        model,
        "crear-trayecto.html.hbs");
  }

}
