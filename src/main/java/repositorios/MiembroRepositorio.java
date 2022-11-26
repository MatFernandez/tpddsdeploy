package repositorios;

import dominio.organizacion.Miembro;
import java.util.List;
import javax.persistence.Query;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class MiembroRepositorio implements WithGlobalEntityManager, TransactionalOps {

  private static MiembroRepositorio miembroRepositorio;
  private List<Miembro> miembros;

  private MiembroRepositorio() {

    Query query = entityManager().createQuery("select a from Miembro a"); //get Tramos
    miembros = query.getResultList();

  }

  public static MiembroRepositorio getInstance() {
    if (miembroRepositorio == null) {
      miembroRepositorio = new MiembroRepositorio();
    }
    return miembroRepositorio;
  }

  public List<Miembro> getMiembros() {
    Query query = entityManager().createQuery(
        "select a from Miembro a"); //levantamos la lista de usuarios de la BBDD
    return query.getResultList();
  }


}
