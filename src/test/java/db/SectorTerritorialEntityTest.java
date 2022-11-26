package db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import dominio.sectorTerritorial.SectorTerritorial;
import dominio.sectorTerritorial.enums.TipoSectorTerritorial;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class SectorTerritorialEntityTest extends AbstractPersistenceTest implements WithGlobalEntityManager {


  @BeforeEach
  public void abrirTr(){
    entityManager().getTransaction().begin();
  }

  @AfterEach
  public void rollback(){
    entityManager().getTransaction().rollback();
  }


  @Test
  public void guardarSectorTerritorial() {

    SectorTerritorial sectorTerritorial = new SectorTerritorial(TipoSectorTerritorial.MUNICIPIO, new ArrayList<>());

    entityManager().persist(sectorTerritorial);

    SectorTerritorial sectorTerritorial1 = entityManager().find(SectorTerritorial.class,1L);

    assertNotNull(sectorTerritorial1);

  }


}
