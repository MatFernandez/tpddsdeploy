package repositorios;

import dominio.common.DatoActividad;
import java.util.List;
import javax.persistence.Query;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class DatosActividadRepositorio implements WithGlobalEntityManager, TransactionalOps {

  private static DatosActividadRepositorio tramoRepositorio;
  private List<DatoActividad> tramos;

  private DatosActividadRepositorio() {

    Query query = entityManager().createQuery("select a from DatoActividad a"); //get Tramos
    tramos = query.getResultList();

  }

  public static DatosActividadRepositorio getInstance() {
    if (tramoRepositorio == null) {
      tramoRepositorio = new DatosActividadRepositorio();
    }
    return tramoRepositorio;
  }

  public List<DatoActividad> getDatosActividad() {
    Query query = entityManager().createQuery(
        "select a from DatoActividad a"); //levantamos la lista de usuarios de la BBDD
    return query.getResultList();
  }

  public void crearDatoActividad(String tipoConsumo, String valor, String periocidad,
      String periodoImputacion) {

    entityManager().persist(new DatoActividad(tipoConsumo, valor, periocidad, periodoImputacion));
  }

  public void guardarListaDatoActividad(List<DatoActividad> list) {
    list.forEach(da -> entityManager().persist(da));
  }

}
