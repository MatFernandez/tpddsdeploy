package dominio.mediotransporte;

public class Recorrido {

  Integer estacionInicial = 0;
  Integer estacionFinal = 0;
  Boolean esIda = false;

  public Recorrido(Integer estacionInicial, Integer estacionFinal, Boolean esIda) {
    this.estacionInicial = estacionInicial;
    this.estacionFinal = estacionFinal;
    this.esIda = esIda;
  }

  public int getEstacionInicial() {
    return estacionInicial;
  }

  public int getEstacionFinal() {
    return estacionFinal;
  }

  public boolean esIda() {
    return esIda;
  }

}
