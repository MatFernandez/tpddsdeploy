package db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import dominio.organizacion.Miembro;
import dominio.organizacion.enums.TipoDocumento;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class MiembroEntityTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

  @BeforeEach
  public void abrirTr(){
    entityManager().getTransaction().begin();
  }

  @AfterEach
  public void rollback(){
    entityManager().getTransaction().rollback();
  }

  @Test
  public void guardarUnMiembro() {

    Miembro miembro = new Miembro("Roberto", "Carlos", "20482345", TipoDocumento.DNI);

    entityManager().persist(miembro);

    List<Miembro> miembros = entityManager().createQuery("from Miembro").getResultList();

    assertNotNull(miembros);
    assertTrue(miembros.size()>0);

  }
}
