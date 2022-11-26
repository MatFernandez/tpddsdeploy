package controllers;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class PerfilController {

  public ModelAndView mostrar(Request req, Response res) {

    Map<String, String> model = new HashMap<>();
    model.put("nombre", req.session().attribute("nombre"));
    model.put("apellido", req.session().attribute("apellido"));
    String rol = req.session().attribute("rol").equals("M") ? "miembro": null;
    String adminRol = req.session().attribute("rol").equals("A") ? "adminRol": null;
    model.put("miembro",rol);
    model.put("adminRol",adminRol);

    return new ModelAndView(model, "perfil.hbs");
  }


}
