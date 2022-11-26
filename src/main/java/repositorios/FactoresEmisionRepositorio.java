package repositorios;

import dominio.common.DatoActividad;
import dominio.common.TipoConsumo;
import dominio.medicion.FactorDeEmision;
import dominio.medicion.FactoresDeEmisionPorTipoConsumo;
import dominio.medicion.enums.Magnitud;
import dominio.medicion.enums.Unidad;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FactoresEmisionRepositorio implements WithGlobalEntityManager {


  private static FactoresEmisionRepositorio factoresEmisionRepositorio;
  public List<FactoresDeEmisionPorTipoConsumo> factoresDeEmision;

  public static FactoresEmisionRepositorio getInstance() {
    if (factoresEmisionRepositorio == null) {
      factoresEmisionRepositorio = new FactoresEmisionRepositorio();
    }
    return factoresEmisionRepositorio;
  }

  public List<FactoresDeEmisionPorTipoConsumo> repositorioFactoresEmision() {
    List<FactoresDeEmisionPorTipoConsumo> factoresDeEmision = new ArrayList<>();
    FactoresDeEmisionPorTipoConsumo factor1 = new FactoresDeEmisionPorTipoConsumo(TipoConsumo.GAS_NATURAL, new FactorDeEmision("Combustion Fija", 2.0, Unidad.m3, Magnitud.kg));
    FactoresDeEmisionPorTipoConsumo factor2 = new FactoresDeEmisionPorTipoConsumo(TipoConsumo.NAFTA, new FactorDeEmision("Combustion Fija", 3.0, Unidad.lt, Magnitud.kg));
    FactoresDeEmisionPorTipoConsumo factor3 = new FactoresDeEmisionPorTipoConsumo(TipoConsumo.DIESEL, new FactorDeEmision("Combustion Fija", 5.0, Unidad.lt, Magnitud.kg));
    FactoresDeEmisionPorTipoConsumo factor4 = new FactoresDeEmisionPorTipoConsumo(TipoConsumo.ELECTRICIDAD, new FactorDeEmision("Electricidad adquirida", 2.0, Unidad.Kwh, Magnitud.kg));
    factoresDeEmision.add(factor1);
    factoresDeEmision.add(factor2);
    factoresDeEmision.add(factor3);
    factoresDeEmision.add(factor4);
    return factoresDeEmision;
  }

  public List<FactoresDeEmisionPorTipoConsumo> getFactoresDeEmisionPorTipoDeConsumo() {
    Query query = entityManager().createQuery(
        "select factores from FactoresDeEmisionPorTipoConsumo factores");
    return query.getResultList();
  }

}

