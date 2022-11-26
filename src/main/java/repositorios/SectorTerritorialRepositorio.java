package repositorios;

import dominio.common.DatoActividad;
import dominio.organizacion.Organizacion;
import dominio.sectorTerritorial.SectorTerritorial;
import dominio.sectorTerritorial.enums.TipoSectorTerritorial;
import java.util.List;
import javax.persistence.Query;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class SectorTerritorialRepositorio implements WithGlobalEntityManager, TransactionalOps {

  private static SectorTerritorialRepositorio tramoRepositorio;
  private List<Organizacion> tramos;

  private SectorTerritorialRepositorio() {

    Query query = entityManager().createQuery("select a from SectorTerritorial a"); //get Tramos
    tramos = query.getResultList();

  }

  public static SectorTerritorialRepositorio getInstance() {
    if (tramoRepositorio == null) {
      tramoRepositorio = new SectorTerritorialRepositorio();
    }
    return tramoRepositorio;
  }

  public List<SectorTerritorial> getSectoresTerritoriales() {
    Query query = entityManager().createQuery(
        "select a from SectorTerritorial a"); //levantamos la lista de usuarios de la BBDD
    return query.getResultList();
  }

  public List<SectorTerritorial> getSectoresTerritorialesByName(String nombreSectorTerritorial) {
    Query query = entityManager().createQuery(
        "select a from SectorTerritorial a where nombre=:nombreSectorTerritorial"); //levantamos la lista de usuarios de la BBDD
    query.setParameter("nombreSectorTerritorial",nombreSectorTerritorial);
    return query.getResultList();
  }

  public List<SectorTerritorial> getSectoresTerritorialesById(Long id) {
    Query query = entityManager().createQuery(
        "select a from SectorTerritorial a where id=:id"); //levantamos la lista de usuarios de la BBDD
    query.setParameter("id",id);
    return query.getResultList();
  }


  public List<SectorTerritorial> getSectorTerritorial(String id) {
    Query query = entityManager().createQuery(
        "select a from SectorTerritorial a where id=:id");
    query.setParameter("id",id);
    return query.getResultList();
  }

  public void crearSectorTerritorial(TipoSectorTerritorial tipoSectorTerritorial, List<Organizacion> organizaciones, String nombre) {

    withTransaction(()->{

      entityManager().persist(new SectorTerritorial(nombre, tipoSectorTerritorial, organizaciones));
    });
  }




}
