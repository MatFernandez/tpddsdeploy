package dominio.medicion;

import dominio.medicion.enums.*;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Embeddable
public class FactorDeEmision {


  public String actividadPrimaria;
  public Unidad unidadFactorEmision;
  public Double valor;
  public Magnitud magnitud;

  public FactorDeEmision(String actividadPrimaria, Double valor, Unidad unidadFactorEmision, Magnitud magnitud) {
    this.actividadPrimaria = actividadPrimaria;
    this.unidadFactorEmision = unidadFactorEmision;
    this.valor = valor;
    this.magnitud = magnitud;
  }

  public FactorDeEmision() {

  }

  public FactorDeEmision(String actividadPrimaria, Unidad unidadFactorEmision, Double valor,
      Magnitud magnitud) {
    this.actividadPrimaria = actividadPrimaria;
    this.unidadFactorEmision = unidadFactorEmision;
    this.valor = valor;
    this.magnitud = magnitud;
  }

  public Double getValor() {
    return this.valor;
  }

  public Unidad getUnidadFactorEmision() {
    return unidadFactorEmision;
  }

  public String getActividadPrimaria(){
    return actividadPrimaria;
  }

  public Magnitud getMagnitud() {
    return magnitud;
  }
}
