package repositorios;

import dominio.common.DatoActividad;
import dominio.organizacion.Organizacion;
import java.util.List;
import javax.persistence.Query;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class OrganizacionRepositorio implements WithGlobalEntityManager, TransactionalOps {

  private static OrganizacionRepositorio tramoRepositorio;
  private List<Organizacion> tramos;

  private OrganizacionRepositorio() {

    Query query = entityManager().createQuery("select a from Organizacion a"); //get Tramos
    tramos = query.getResultList();

  }

  public static OrganizacionRepositorio getInstance() {
    if (tramoRepositorio == null) {
      tramoRepositorio = new OrganizacionRepositorio();
    }
    return tramoRepositorio;
  }

  public List<Organizacion> getOrganizaciones() {
    Query query = entityManager().createQuery(
        "select a from Organizacion a"); //levantamos la lista de usuarios de la BBDD
    return query.getResultList();
  }

  public List<Organizacion> getOrganizacion(Long id) {
    Query query = entityManager().createQuery(
        "select a from Organizacion a where id=:id");
    query.setParameter("id",id);
    return query.getResultList();
  }

  public void crearDatoActividad(String tipoConsumo, String valor, String periocidad,
      String periodoImputacion) {

    entityManager().persist(new Organizacion());
  }

  public void guardarListaDatoActividad(List<DatoActividad> list) {
    list.forEach(da -> entityManager().merge(da));
  }

  public void updateOrganizacion(Organizacion organizacion, List<DatoActividad> datoActividads){
    withTransaction(()->{
      organizacion.guardarDatosActividad(datoActividads);
      entityManager().merge(organizacion);
    });
  }

  public void guardarOrganizacion(Organizacion organizacion) {
    withTransaction(()->{

      entityManager().persist(organizacion);
    });


  }
}
