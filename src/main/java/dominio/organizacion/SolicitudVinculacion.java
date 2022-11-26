package dominio.organizacion;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class SolicitudVinculacion {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  private Miembro miembro;

  @ManyToOne
  private Organizacion organizacion;

  private String nombreSector;

  @Column(name = "solicitud_aprobada")
  private Boolean solicitudAprobada;

  public SolicitudVinculacion() {
  }

  public SolicitudVinculacion(Miembro miembro, Organizacion organizacion,
      Boolean solicitudAprobada) {
    this.miembro = miembro;
    this.organizacion = organizacion;
    this.solicitudAprobada = solicitudAprobada;
  }

  public Long getId() {
    return id;
  }

  public Miembro getMiembro() {
    return miembro;
  }

  public Organizacion getOrganizacion() {
    return organizacion;
  }

  public Boolean getSolicitudAprobada() {
    return solicitudAprobada;
  }

  public SolicitudVinculacion setSolicitudAprobada(Boolean solicitudAprobada) {
    this.solicitudAprobada = solicitudAprobada;
    return this;
  }

  public String getNombreSector() {
    return nombreSector;
  }
}
