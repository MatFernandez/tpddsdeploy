package db;

import dominio.common.Ubicacion;
import dominio.mediotransporte.Estacion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EstacionEntityTest extends AbstractPersistenceTest implements WithGlobalEntityManager {


  //Revisar rollback
  //Hacer commit en los tests para chequear que no haya problemas de config
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


    Estacion estacionCongreso =
        new Estacion("Congreso", ubi, 30L,
            25L, 3, null);
    entityManager().persist(estacionCongreso);

    List<Estacion> estaciones = entityManager().createQuery("from Estacion").getResultList();
    assertTrue(estaciones.size()>0);
  }


}
