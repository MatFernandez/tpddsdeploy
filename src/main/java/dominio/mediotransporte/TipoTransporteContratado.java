package dominio.mediotransporte;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity

public class TipoTransporteContratado {

  @Id
  @GeneratedValue

  private Long id;
  private String nombreDelTransporteContratado;

  public TipoTransporteContratado() {
  }

  public TipoTransporteContratado(String nombreDelTransporteContratado) {
    this.nombreDelTransporteContratado = nombreDelTransporteContratado;
  }

  public String getNombreDelTransporte() {
    return nombreDelTransporteContratado;
  }

  public Long getId() {
    return id;
  }
}
