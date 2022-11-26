package dominio.trayecto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Trayecto {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToMany
  private List<Tramo> tramos;


  private LocalDate fechaTrayecto;

  private String nombreTrayecto;


  public LocalDate getFechaTrayecto() {
    return fechaTrayecto;
  }

  public Trayecto(List<Tramo> tramos, LocalDate fechaTrayecto) {
    this.tramos = tramos;
    this.fechaTrayecto = fechaTrayecto;
  }

  public Trayecto(List<Tramo> tramos) {
    this.tramos = tramos;
  }

  public Trayecto(List<Tramo> tramos, String nombreTrayecto) {
    this.tramos = tramos;
    this.nombreTrayecto = nombreTrayecto;
  }

  public Trayecto() {
  }

  public Trayecto setFechaTrayecto(LocalDate fechaTrayecto) {
    this.fechaTrayecto = fechaTrayecto;
    return this;
  }

  public void agregarTramo(Tramo tramo) {
    this.tramos.add(tramo);
  }

  public int cantidadDeTramos() {
    return this.tramos.size();
  }

  public Long getDistanciaTotal() {
    return tramos.stream().mapToLong(tramo -> tramo.getDistanciaTramo()).sum();
  }

  public Double huellaCarbono() {
    return tramos.stream().mapToDouble(tramo -> tramo.huellaCarbono()).sum();
  }

  public List<Tramo> getTramos() {
    return tramos;
  }

  public Long getId() {
    return id;
  }

  public String getNombreTrayecto() {
    return nombreTrayecto;
  }
}
