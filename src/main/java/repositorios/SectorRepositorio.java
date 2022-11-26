package repositorios;

import dominio.organizacion.Sector;
import dominio.trayecto.Tramo;
import dominio.trayecto.Trayecto;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import javax.persistence.Query;
import java.util.List;

public class SectorRepositorio implements WithGlobalEntityManager, TransactionalOps {

  private static SectorRepositorio sectorRepositorio;
  private List<Trayecto> sectores;

  private SectorRepositorio() {

    Query query = entityManager().createQuery("select a from Sector a");
    sectores = query.getResultList();

  }

  public static SectorRepositorio getInstance() {
    if (sectorRepositorio == null) {
      sectorRepositorio = new SectorRepositorio();
    }
    return sectorRepositorio;
  }

  public List<Sector> getSectores() {
    Query query = entityManager().createQuery(
        "select a from Sector a");
    return query.getResultList();
  }

  public void crearSector() {

    entityManager().persist(new Sector());
  }

}
