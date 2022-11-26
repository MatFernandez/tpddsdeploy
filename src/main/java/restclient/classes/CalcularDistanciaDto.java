package restclient.classes;

import dominio.common.Ubicacion;

import java.util.HashMap;

public class CalcularDistanciaDto extends HashMap<String, String> {
  private String localidadInicio;
  private String calleInicio;
  private String alturaInicio;
  private String localidadDestino;
  private String calleDestino;
  private String alturaDestino;

  public CalcularDistanciaDto(Ubicacion inicio, Ubicacion destino) {
    put("localidadOrigenId", inicio.getLocalidad());
    put("calleOrigen", inicio.getCalle());
    put("alturaOrigen", inicio.getAltura());
    put("localidadDestinoId", destino.getLocalidad());
    put("calleDestino", destino.getCalle());
    put("alturaDestino", destino.getAltura());
  }

  public CalcularDistanciaDto(String localidadInicio, String calleInicio,
                              String alturaInicio, String localidadDestino,
                              String calleDestino, String alturaDestino) {
    put("localidadOrigenId", localidadInicio);
    put("calleOrigen", calleInicio);
    put("alturaOrigen", alturaInicio);
    put("localidadDestinoId", localidadDestino);
    put("calleDestino", calleDestino);
    put("alturaDestino", alturaDestino);
  }
}
