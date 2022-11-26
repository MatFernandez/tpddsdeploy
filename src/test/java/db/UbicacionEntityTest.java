package db;

import dominio.common.Ubicacion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class UbicacionEntityTest extends AbstractPersistenceTest implements WithGlobalEntityManager {


  @BeforeEach
  public void abrirTr(){
    entityManager().getTransaction().begin();
  }

  @AfterEach
  public void rollback(){
    entityManager().getTransaction().rollback();
  }


  @Test
  public void calcular_distancia_transportePublico_camino_ida() {
    Ubicacion ubi = new Ubicacion("1", "calle" + " 6", "150");
    entityManager().persist(ubi);

    List<Ubicacion> ubicacion = entityManager().createQuery("from Ubicacion").getResultList();
    assertTrue(ubicacion.size()>0);

  }

}
