package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.TramoRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import dominio.trayecto.Tramo;

public class TramosController implements WithGlobalEntityManager, TransactionalOps {

  public ModelAndView verTramos(Request request, Response response) {
    List<Tramo> tramos = TramoRepositorio.getInstance().getTramos();
    Map<String,Object> map = new HashMap<>();
    map.put("tramos",tramos);
    return new ModelAndView(
        map,
        "crear-tramo.html.hbs");
  }

  public ModelAndView registraTramo(Request request, Response response) {
    String localidad1 = request.queryParams("localidad1");
    String calle1 = request.queryParams("calle1");
    String altura1 = request.queryParams("altura1");
    String localidad2 = request.queryParams("localidad2");
    String calle2 = request.queryParams("calle2");
    String altura2 = request.queryParams("altura2");
    withTransaction(() -> {
      TramoRepositorio.getInstance().crearTramo(localidad1,calle1,altura1,localidad2,calle2,altura2);
    });

    response.redirect("/tramos");
    return null;
  }

}
