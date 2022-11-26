package repositorios;

import dominio.common.Ubicacion;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Query;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import dominio.trayecto.Tramo;

public class TramoRepositorio implements WithGlobalEntityManager, TransactionalOps {

  private static TramoRepositorio tramoRepositorio;
  private List<Tramo> tramos;

  private TramoRepositorio() {

    Query query = entityManager().createQuery("select a from Tramo a"); //get Tramos
    tramos = query.getResultList();

  }

  public static TramoRepositorio getInstance() {
    if (tramoRepositorio == null) {
      tramoRepositorio = new TramoRepositorio();
    }
    return tramoRepositorio;
  }

  public List<Tramo> getTramos() {
    Query query = entityManager().createQuery(
        "select a from Tramo a"); //levantamos la lista de usuarios de la BBDD
    return query.getResultList();
  }

  public void crearTramo(String localidad, String calle, String altura, String localidad2, String calle2, String altura2){

    entityManager().persist(new Tramo(new Ubicacion(localidad, calle, altura), new Ubicacion(localidad2, calle2, altura2), null));
  }

  public Tramo obtenerPorLocalidadInicial(String localidadInicial) {
    List<Tramo> tramosEncontrados = tramos.stream().filter(tramo -> tramo.getInicio().getLocalidad().equals(localidadInicial)).collect(
        Collectors.toList());
    if(tramosEncontrados.isEmpty()) {
      return null;
    }
    else {
      return tramosEncontrados.get(0);
    }
  }

//  public void actualizarTramo(Tramo tramo) {
//
//    try {
//      em.getTransaction().begin();
//      em.merge(tramo);
//      em.getTransaction().commit();
//    }
//    catch (Exception e) {
//      em.getTransaction().rollback();
//      e.printStackTrace();
//    }
//    finally {
//      //manager.close();
//    }
//  }

}
