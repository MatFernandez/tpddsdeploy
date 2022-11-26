package dominio.common;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public abstract class ObtenerDistancia {
  @Id
  @GeneratedValue

  private Long id;

  public abstract Distancia obtenerDistancia(Ubicacion puntoA, Ubicacion puntoB);
}
