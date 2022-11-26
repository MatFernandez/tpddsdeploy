package dominio.mediotransporte;

import dominio.common.Ubicacion;
import dominio.mediotransporte.enums.TipoCombustible;
import dominio.mediotransporte.enums.TipoTransportePublico;
import dominio.common.Transporte;

import javax.persistence.*;


//Originalmente implementaba la interface transporte, pero por el mapeo extiende de la clase abstracta transporte

@Entity

public class TransportePublico extends Transporte {
  @Enumerated
  private TipoTransportePublico tipoTransportePublico;
  @ManyToOne
  private Linea linea;
  @Enumerated
  private TipoCombustible tipoCombustible;

  private Double consumoDeCombustiblePorKm;

  public TransportePublico() {
  }

  public TransportePublico(TipoTransportePublico tipoDeTrasporte,
                           Linea linea,
                           Double consumoPorKm,
                           TipoCombustible tipoCombustible) {
    this.linea = linea;
    this.tipoTransportePublico = tipoDeTrasporte;
    this.consumoDeCombustiblePorKm = consumoPorKm;
    this.tipoCombustible = tipoCombustible;
  }

  public String getSubtipo() {
    return tipoTransportePublico.toString();
  }

  @Override
  public Double getConsumoDeCombustiblePorKm() {
    return this.consumoDeCombustiblePorKm;
  }

  @Override
  public Long calcularDistanciaTransporte(Ubicacion puntoPartida, Ubicacion puntoDestino) {
    return linea.getDistanciaRecorrido(puntoPartida, puntoDestino);
  }

  @Override
  public TipoCombustible getTipoCombustible() {
    return this.tipoCombustible;
  }
}

