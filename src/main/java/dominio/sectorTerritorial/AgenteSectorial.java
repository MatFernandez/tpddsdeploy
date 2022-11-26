package dominio.sectorTerritorial;

//@Entity
public class AgenteSectorial {

  //@Id
  //@GeneratedValue

  private Long id;

  //@OneToOne
  private SectorTerritorial sectorTerritorial;

  public AgenteSectorial() {
  }

  public AgenteSectorial(SectorTerritorial sectorTerritorial) {
    this.sectorTerritorial = sectorTerritorial;
  }

  public Long getId() {
    return id;
  }
}
