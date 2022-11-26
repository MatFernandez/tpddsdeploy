package restclient.classes;

import dominio.common.Distancia;

public class DistanciaDto {
  public String valor;
  public String unidad;

  public DistanciaDto() {
  }

  public DistanciaDto(String valor, String unidad) {
    this.valor = valor;
    this.unidad = unidad;
  }

  public String getValor() {
    return valor;
  }

  public String getUnidad() {
    return unidad;
  }

  public Distancia createDistancia() {
    String valorString = getValor().replace(".", "");
    Long valor = Long.valueOf(valorString);
    return new Distancia(valor, getUnidad());
  }
}
