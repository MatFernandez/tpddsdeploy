package repositorios;

import java.util.List;
import javax.persistence.Query;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import dominio.trayecto.Tramo;
import dominio.trayecto.Trayecto;

public class TrayectoRepositorio implements WithGlobalEntityManager, TransactionalOps {

  private static TrayectoRepositorio trayectoRepositorio;
  private List<Trayecto> tramos;

  private TrayectoRepositorio() {

    Query query = entityManager().createQuery("select a from Trayecto a"); //get Tramos
    tramos = query.getResultList();

  }

  public static TrayectoRepositorio getInstance() {
    if (trayectoRepositorio == null) {
      trayectoRepositorio = new TrayectoRepositorio();
    }
    return trayectoRepositorio;
  }

  public List<Trayecto> getTrayectos() {
    Query query = entityManager().createQuery(
        "select a from Trayecto a");
    return query.getResultList();
  }

  public void crearTrayecto(Trayecto trayecto) {
    withTransaction(()->{

      entityManager().persist(trayecto);
    });
  }

}
