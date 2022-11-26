package dominio.organizacion;

import dominio.organizacion.enums.TipoDocumento;
import dominio.organizacion.exceptions.ErrorAgregarTrayectoMiembroException;
import dominio.trayecto.Trayecto;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Miembro {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String nombre;
  private String apellido;
  private String numeroDocumento;

  @Enumerated
  private TipoDocumento tipoDocumento;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Trayecto> trayectos = new ArrayList<>();
  @Transient
  private List<Trayecto> trayectosCompartidos = new ArrayList<>();

  public Miembro() {
  }

  public Miembro(String nombre, String apellido, String numeroDocumento,
      TipoDocumento tipoDocumento) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.numeroDocumento = numeroDocumento;
    this.tipoDocumento = tipoDocumento;
  }


  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public Double huellaCarbono(LocalDate fecha) {
    return this.trayectos.stream()
        .filter(trayecto -> trayecto.getFechaTrayecto().isEqual(fecha))
        .mapToDouble(trayecto -> trayecto.huellaCarbono()).sum();
  }

  private void cargarTrayectoCompartido(Trayecto trayecto) {
    this.trayectosCompartidos.add(trayecto);
  }

  public void nuevoTrayecto(Trayecto trayecto, List<Miembro> miembros) {
    if (trayecto == null) {
      throw new ErrorAgregarTrayectoMiembroException("Trayecto invalido");
    }
    if (miembros == null) {
      throw new ErrorAgregarTrayectoMiembroException("Miembros no validos");
    }
    this.trayectos.add(trayecto);// Creador del dominio.trayecto lo carga en su lista principal
    miembros.forEach(miembro -> miembro.cargarTrayectoCompartido(trayecto));// Los otros miembros lo tienen en su lista compartida
  }

  public Long getId() {
    return id;
  }

  public List<Trayecto> getTrayectos() {
    return trayectos;
  }

  public void setTrayectos(List<Trayecto> trayectos) {
    this.trayectos = trayectos;
  }

  public void setTrayectosCompartidos(List<Trayecto> trayectosCompartidos) {
    this.trayectosCompartidos = trayectosCompartidos;
  }
}
