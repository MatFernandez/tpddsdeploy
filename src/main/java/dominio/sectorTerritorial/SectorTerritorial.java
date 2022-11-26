package dominio.sectorTerritorial;

import dominio.organizacion.Organizacion;
import dominio.sectorTerritorial.enums.TipoSectorTerritorial;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
public class SectorTerritorial {

  @Id
  @GeneratedValue

  private Long id;

  private String nombre;

  @Enumerated
  private TipoSectorTerritorial tipoSectorTerritorial;

  @OneToMany
  private List<Organizacion> organizaciones;

  public SectorTerritorial(String nombre,
      TipoSectorTerritorial tipoSectorTerritorial,
      List<Organizacion> organizaciones) {
    this.nombre = nombre;
    this.tipoSectorTerritorial = tipoSectorTerritorial;
    this.organizaciones = organizaciones;
  }


  public SectorTerritorial() {
  }

  public SectorTerritorial(TipoSectorTerritorial tipoSectorTerritorial, List<Organizacion> organizaciones) {
    this.organizaciones = organizaciones;
    this.tipoSectorTerritorial = tipoSectorTerritorial;
  }

  public Double HCTotalDelSector(LocalDate fecha) {
    return organizaciones.stream().mapToDouble(organizacion -> organizacion.huellaCarbonoOrganizacion(fecha)).sum();
  }

  public List<Organizacion> getOrganizaciones() {
    return organizaciones;
  }

  public TipoSectorTerritorial getTipoSectorTerritorial() {
    return tipoSectorTerritorial;
  }

  public Long getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }
}
