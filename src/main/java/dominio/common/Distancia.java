package dominio.common;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Distancia {

  @Id
  @GeneratedValue

  private Long id;
  private Long valor;
  private  String unidad;

  public Distancia(){};

  public Distancia(Long valor, String unidad) {
    this.valor = valor;
    this.unidad = unidad;
  }

  public Long getValor() {
    return valor;
  }

  public String getUnidad() {
    return unidad;
  }

  public Long getId() {
    return id;
  }
}
