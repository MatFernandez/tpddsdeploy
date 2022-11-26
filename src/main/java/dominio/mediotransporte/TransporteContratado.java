package dominio.mediotransporte;

import dominio.common.Ubicacion;
import dominio.common.ObtenerDistancia;
import dominio.common.Transporte;
import dominio.mediotransporte.enums.TipoCombustible;
import restclient.classes.ObtenerDistanciaGeodds;

import javax.persistence.*;

@Entity

public class TransporteContratado extends Transporte {

  @OneToOne
  private TipoTransporteContratado tipoServicioContratado;

  //@OneToOne(cascade = CascadeType.ALL)
  @Transient
  //private ObtenerDistancia obtenerDistancia;

  private Double consumoPromedioPorKm;
  @Enumerated
  private TipoCombustible tipoCombustible;

  public TransporteContratado() {
  }

  public TransporteContratado(TipoTransporteContratado tipoServicioContratado,
                              ObtenerDistancia obtenerDistancia,
                              Double consumoPorKm, TipoCombustible tipoCombustible) {
    this.tipoServicioContratado = tipoServicioContratado;
    //this.obtenerDistancia = obtenerDistancia;
    this.consumoPromedioPorKm = consumoPorKm;
    this.tipoCombustible = tipoCombustible;
  }

  public String getSubtipo() {
    return tipoServicioContratado.toString();
  }

  @Override
  public Long calcularDistanciaTransporte(Ubicacion puntoPartida, Ubicacion puntoDestino) {
    return ObtenerDistanciaGeodds.getInstancia().obtenerDistancia(puntoPartida, puntoDestino).getValor();
  }

  @Override
  public Double getConsumoDeCombustiblePorKm() {
    return this.consumoPromedioPorKm;
  }

  public TipoTransporteContratado getTipoServicioContratado() {
    return tipoServicioContratado;
  }

  @Override
  public TipoCombustible getTipoCombustible() {
    return this.tipoCombustible;
  }

}
