package dominio.common;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity

public class Ubicacion {

  @Id
  @GeneratedValue

  private Long id;

  private String localidad;
  private String calle;
  private String altura;

  public Ubicacion() {
  }

  public Ubicacion(String localidad, String calle, String altura) {
    this.localidad = localidad;
    this.calle = calle;
    this.altura = altura;
  }

  public String getLocalidad() {
    return localidad;
  }

  public String getCalle() {
    return calle;
  }

  public String getAltura() {
    return altura;
  }

  public Long getId() {
    return id;
  }
}
