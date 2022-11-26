package dominio.medicion;

import dominio.common.TipoConsumo;

import dominio.medicion.enums.Unidad;
import java.util.List;
import javax.persistence.*;
import java.util.Map;
import java.util.Set;


@Entity

public class FactoresDeEmisionPorTipoConsumo {

  @Id
  @GeneratedValue
  private Long id;

  @Enumerated
  private TipoConsumo tipoConsumo;

  @Embedded
  private FactorDeEmision factoresDeEmision;

  public FactoresDeEmisionPorTipoConsumo() {

  }

  public FactoresDeEmisionPorTipoConsumo(TipoConsumo tipoConsumo,
      FactorDeEmision factoresDeEmision) {
    this.tipoConsumo = tipoConsumo;
    this.factoresDeEmision = factoresDeEmision;
  }

  public TipoConsumo getTipoConsumo() {
    return tipoConsumo;
  }

  public FactorDeEmision getFactoresDeEmision() {
    return factoresDeEmision;
  }
}
