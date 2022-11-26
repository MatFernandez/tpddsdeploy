package dominio.mediotransporte;

import dominio.common.Ubicacion;
import dominio.common.ObtenerDistancia;
import dominio.common.Transporte;
import dominio.mediotransporte.enums.*;
import restclient.classes.ObtenerDistanciaGeodds;

import javax.persistence.*;

//Originalmente implementaba la interface transporte, pero por el mapeo extiende de la clase abstracta transporte

@Entity

public class TransporteParticular extends Transporte {
  @Enumerated
  private TipoCombustible tipoCombustible;
  @Enumerated
  private TipoTransporteParticular tipoTransporteParticular;

  //@OneToOne(cascade = CascadeType.ALL)
  //@Transient
  //private ObtenerDistancia obtenerDistancia;
  private Double consumoPromedioPorKm;

  public TransporteParticular() {
  }

  public TransporteParticular(TipoCombustible tipoCombustible,
                              TipoTransporteParticular tipoTransporteParticular,
                              ObtenerDistancia obtenerDistancia,
                              Double consumoPorKm) {
    this.tipoCombustible = tipoCombustible;
    this.tipoTransporteParticular = tipoTransporteParticular;
    //this.obtenerDistancia = obtenerDistancia;
    this.consumoPromedioPorKm = consumoPorKm;
  }

  public TipoTransporteParticular getTipoTransporteParticular() {
    return tipoTransporteParticular;
  }

  public TipoCombustible getTipoCombustible() {
    return tipoCombustible;
  }

  public String getSubtipo() {
    return tipoTransporteParticular.toString();
  }

  @Override
  public Long calcularDistanciaTransporte(Ubicacion puntoPartida, Ubicacion puntoDestino) {
    return ObtenerDistanciaGeodds.getInstancia().obtenerDistancia(puntoPartida, puntoDestino).getValor();
  }

  @Override
  public Double getConsumoDeCombustiblePorKm() {
    return this.consumoPromedioPorKm;
  }
}
