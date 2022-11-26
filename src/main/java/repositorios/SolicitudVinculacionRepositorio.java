package repositorios;

import dominio.common.DatoActividad;
import dominio.organizacion.Miembro;
import dominio.organizacion.Organizacion;
import dominio.organizacion.SolicitudVinculacion;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Query;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class SolicitudVinculacionRepositorio implements WithGlobalEntityManager, TransactionalOps {

  private static SolicitudVinculacionRepositorio tramoRepositorio;
  private List<Organizacion> tramos;

  private SolicitudVinculacionRepositorio() {

    Query query = entityManager().createQuery("select a from SolicitudVinculacion a"); //get Tramos
    tramos = query.getResultList();

  }

  public static SolicitudVinculacionRepositorio getInstance() {
    if (tramoRepositorio == null) {
      tramoRepositorio = new SolicitudVinculacionRepositorio();
    }
    return tramoRepositorio;
  }

  public List<SolicitudVinculacion> getSolicitudVinculacion() {
    Query query = entityManager().createQuery(
        "select a from SolicitudVinculacion a"); //levantamos la lista de usuarios de la BBDD
    return query.getResultList();
  }

  public List<SolicitudVinculacion> getSolicitudVinculacionPendientes() {
    Query query = entityManager().createQuery(
        "select a from SolicitudVinculacion a where solicitudAprobada = false "); //levantamos la lista de usuarios de la BBDD
    return query.getResultList();
  }

  public void crearSolicitud(Organizacion organizacion, Miembro miembro) {

    entityManager().persist(new SolicitudVinculacion(miembro, organizacion,false));
  }

  public void aprobarSolicitud(SolicitudVinculacion solicitudVinculacion) {
    withTransaction(() -> {
      solicitudVinculacion.setSolicitudAprobada(true);
      entityManager().merge(solicitudVinculacion);
    });

  }


}
