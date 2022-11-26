package dominio.mediotransporte;

import dominio.common.Ubicacion;
import dominio.common.ObtenerDistancia;
import dominio.common.Transporte;
import dominio.mediotransporte.enums.TipoCombustible;
import dominio.mediotransporte.enums.TipoTransporteSustentable;
import restclient.classes.ObtenerDistanciaGeodds;

import javax.persistence.*;


//Originalmente implementaba la interface transporte, pero por el mapeo extiende de la clase abstracta transporte

@Entity

public class TransporteSustentable extends Transporte {
  @Enumerated
  private TipoTransporteSustentable tipoTransporteSustentable;
  //@OneToOne(cascade = CascadeType.ALL)
  //@Transient
  //private ObtenerDistancia obtenerDistancia;

  public TransporteSustentable() {
  }

  public TransporteSustentable(TipoTransporteSustentable tipoTransporteSustentable,
                               ObtenerDistancia obtenerDistancia) {
    this.tipoTransporteSustentable = tipoTransporteSustentable;
    //this.obtenerDistancia = obtenerDistancia;
  }

  public String getSubtipo() {
    return tipoTransporteSustentable.toString();
  }

  @Override
  public Long calcularDistanciaTransporte(Ubicacion puntoPartida, Ubicacion puntoDestino) {
    return ObtenerDistanciaGeodds.getInstancia().obtenerDistancia(puntoPartida, puntoDestino).getValor();
  }

  @Override
  public Double getConsumoDeCombustiblePorKm() {
    return 0.0;
  }

  @Override
  public TipoCombustible getTipoCombustible() {
    return TipoCombustible.SUSTENTABLE;
  }
}
