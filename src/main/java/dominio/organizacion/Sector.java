package dominio.organizacion;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Sector {

  @Id
  @GeneratedValue

  private Long id;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Miembro> miembros;

  private String nombreSector;

  public Sector() {
  }

  public Sector(List<Miembro> miembros, String nombreSector) {
    this.miembros = new ArrayList<>();
    this.nombreSector = nombreSector;
  }

  public Sector(String nombreSector, List<Miembro> miembros) {
    this.miembros = miembros;
    this.nombreSector = nombreSector;
  }

  public void addMiembro(Miembro miembro) {
    this.miembros.add(miembro);
  }

  public Integer cantidadDeMiembros() {
    return this.miembros.size();
  }

  public Double huellaSector(LocalDate fecha) {
    return this.miembros.stream().mapToDouble(miembro -> miembro.huellaCarbono(fecha)).sum();
  }

  public String getNombreSector() {
    return nombreSector;
  }

  public Long getId() {
    return id;
  }

  public List<Miembro> getMiembros() {
    return miembros;
  }
}
