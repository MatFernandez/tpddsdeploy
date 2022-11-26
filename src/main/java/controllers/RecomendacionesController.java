package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class RecomendacionesController {
  public ModelAndView verRecomendaciones(Request request, Response response) {
    return new ModelAndView(
        null,
        "recomendaciones.html.hbs");
  }
}
