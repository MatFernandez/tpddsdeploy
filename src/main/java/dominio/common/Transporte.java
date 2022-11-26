package dominio.common;

import dominio.mediotransporte.enums.TipoCombustible;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public abstract class Transporte {

  @Id
  @GeneratedValue

  public Long id;

  public abstract String getSubtipo();

  public abstract Long calcularDistanciaTransporte(Ubicacion puntoPartida, Ubicacion puntoDestino);

  public abstract Double getConsumoDeCombustiblePorKm();

  public abstract TipoCombustible getTipoCombustible();
}
