package db;

import dominio.common.Transporte;
import dominio.mediotransporte.TransporteParticular;
import dominio.mediotransporte.enums.TipoCombustible;
import dominio.mediotransporte.enums.TipoTransporteParticular;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import dominio.sectorTerritorial.SectorTerritorial;
import dominio.sectorTerritorial.enums.TipoSectorTerritorial;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;


public class TransporteEntityTest extends AbstractPersistenceTest implements WithGlobalEntityManager {


  @BeforeEach
  public void abrirTr(){
    entityManager().getTransaction().begin();
  }

  @AfterEach
  public void rollback(){
    entityManager().getTransaction().rollback();
  }


  @Test
  public void test_trayecto_auto_particular() {

    Transporte transporte =
        new TransporteParticular(TipoCombustible.GASOIL, TipoTransporteParticular.AUTO,
            null, 1D);

    entityManager().persist(transporte);
    List<Transporte> transportes = entityManager().createQuery("from Transporte").getResultList();
    assertTrue(transportes.size()>0);
  }


  @Test
  public void guardarSectorTerritorial() {

    SectorTerritorial sectorTerritorial = new SectorTerritorial(TipoSectorTerritorial.MUNICIPIO, new ArrayList<>());

    entityManager().persist(sectorTerritorial);

    List<SectorTerritorial> sectorTerritorial1 = entityManager().createQuery("from SectorTerritorial").getResultList();

    assertNotNull(sectorTerritorial1);

    assertTrue(sectorTerritorial1.size()>0);

  }

}
