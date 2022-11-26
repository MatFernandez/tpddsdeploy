package controllers;

import dominio.administrador.Administrador;
import dominio.administrador.MiembroRol;
import dominio.common.*;


import dominio.mediotransporte.*;
import dominio.mediotransporte.enums.TipoCombustible;
import dominio.mediotransporte.enums.TipoTransporteParticular;
import dominio.mediotransporte.enums.TipoTransportePublico;
import dominio.organizacion.Miembro;
import dominio.organizacion.Organizacion;
import dominio.organizacion.Sector;
import dominio.organizacion.enums.Clasificacion;
import dominio.organizacion.enums.TipoDocumento;
import dominio.organizacion.enums.TipoOrganizacion;
import dominio.sectorTerritorial.enums.TipoSectorTerritorial;
import dominio.trayecto.Trayecto;
import java.time.LocalDate;
import java.util.Arrays;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import dominio.trayecto.Tramo;
import repositorios.DatosActividadRepositorio;
import repositorios.OrganizacionRepositorio;
import repositorios.SectorTerritorialRepositorio;
import restclient.classes.ObtenerDistanciaGeodds;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejecutar antes de levantar el servidor por primera vez
 *
 * @author flbulgarelli
 */
public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void run() {
    withTransaction(() -> {
      Ubicacion a = new Ubicacion("Lanus", "2", "4");
      Ubicacion b = new Ubicacion("Lomas de Zamora", "7", "10");
      Ubicacion c = new Ubicacion("Remedios de Escalada", "2", "4");
      Ubicacion d = new Ubicacion("Banfield", "8", "20");
      Ubicacion e = new Ubicacion("Temperley", "9","23");
      Ubicacion f = new Ubicacion("Varela", "24", "21");
      Ubicacion g1 = new Ubicacion("Caballito", "calle 5", "150");
      Ubicacion g2 = new Ubicacion("Almagro", "calle  6", "150");


      Miembro miembro1 = new Miembro("Luis", "Figo", "27482389", TipoDocumento.DNI);
      Miembro miembro2 = new Miembro("Roberto", "Carlos", "20482345", TipoDocumento.DNI);
      Miembro miembro3 = new Miembro("Claudio", "Tapia", "21488342", TipoDocumento.DNI);

      List<Tramo> sinTramos = new ArrayList<>();

      Trayecto trayecto1 = new Trayecto(sinTramos, LocalDate.of(2022,10,1));
      Trayecto trayecto2 = new Trayecto(sinTramos,LocalDate.of(2020,10,1));
      Trayecto trayecto3= new Trayecto(sinTramos,LocalDate.of(2019,10,1));


      ObtenerDistanciaGeodds obtener = ObtenerDistanciaGeodds.getInstancia();

      Estacion estacionOnce = new Estacion("Once",g1 , 20L, 15L, 2,  obtener);
      Estacion estacionCongreso = new Estacion("Congreso",g2 , 30L, 25L, 3, obtener);


      List<Estacion> estaciones = new ArrayList<>();
      estaciones.add(estacionOnce);
      estaciones.add(estacionCongreso);

      Linea lineaA = new Linea(TipoTransportePublico.SUBTE, "A", estaciones);

      Transporte transportePublico1 = new TransportePublico(TipoTransportePublico.SUBTE, lineaA, 1D, TipoCombustible.GASOIL);
      Transporte transporteParticular1 = new TransporteParticular(TipoCombustible.NAFTA,TipoTransporteParticular.CAMIONETA,obtener, 4.0);
      Transporte transporteContratado1 = new TransporteParticular(TipoCombustible.GASOIL, TipoTransporteParticular.AUTO, obtener, 2.0);
      Transporte transporteContratado2 = new TransporteParticular(TipoCombustible.GASOIL, TipoTransporteParticular.AUTO, obtener, 3.0);

      Tramo tramo1 = new Tramo(a,b,transporteParticular1);
      Tramo tramo2 = new Tramo(c,d, transporteContratado1);
      Tramo tramo3 = new Tramo(e,f, transporteContratado2);
      Tramo tramo4 = new Tramo(e,f, transporteContratado2);
      persist(tramo4);

      trayecto1.agregarTramo(tramo1);
      trayecto2.agregarTramo(tramo2);
      trayecto3.agregarTramo(tramo3);

      List<Miembro> sinMiembros = new ArrayList<>();

      miembro1.nuevoTrayecto(trayecto1,sinMiembros);
      miembro2.nuevoTrayecto(trayecto2, sinMiembros);
      miembro3.nuevoTrayecto(trayecto3,sinMiembros);


      Sector sectorRRHH = new Sector(sinMiembros,"Recursos humanos");
      Sector sectorAdministracion = new Sector(sinMiembros, "Administracion");

      sectorRRHH.addMiembro(miembro1);
      sectorRRHH.addMiembro(miembro2);
      sectorAdministracion.addMiembro(miembro3);

      DatoActividad datoActividad1 = new DatoActividad("Gas natural", "200", "mensual", "10/2022");
      DatoActividad datoActividad2 = new DatoActividad("Nafta", "55", "mensual", "01/2016");
      DatoActividad datoActividad4 = new DatoActividad("Gas natural", "89", "mensual", "01/2016");
      DatoActividad datoActividad5 = new DatoActividad("Electricidad", "2009", "mensual", "02/2016");
      DatoActividad datoActividad6 = new DatoActividad("Electricidad", "62", "mensual", "02/2016");
      DatoActividad datoActividad3 = new DatoActividad("Electricidad", "5000", "anual", "2019");

      persist(a);
      persist(b);
      persist(c);
      persist(d);
      persist(e);
      persist(f);
      persist(lineaA);
      persist(estacionCongreso);
      persist(estacionOnce);
      persist(transporteContratado1);
      persist(transporteContratado2);
      persist(transportePublico1);
      persist(transporteParticular1);
      persist(tramo1);
      persist(tramo2);
      persist(tramo3);
      persist(miembro1);
      persist(miembro2);
      persist(miembro3);
      persist(sectorAdministracion);
      persist(sectorRRHH);
      persist(datoActividad1);
      persist(datoActividad2);
      persist(datoActividad3);
      persist(datoActividad4);
      persist(datoActividad5);
      persist(datoActividad6);

      Sector sector = new Sector("sector A",null);
      Sector sector2 = new Sector("sector B",null);
      List<Sector> sectores = new ArrayList<>();
      sectores.add(sectorRRHH);
      sectores.add(sector2);
      Organizacion googleOrg = new Organizacion("Google", null, TipoOrganizacion.EMPRESA, sectores,
          Clasificacion.MINISTERIO, null,
          null, null);

      Organizacion amazonOrg = new Organizacion("Amazon", null, TipoOrganizacion.EMPRESA, new ArrayList<>(),
          Clasificacion.MINISTERIO, null,
          null, null);

      Organizacion unicefOrg = new Organizacion("Unicef", null, TipoOrganizacion.ONG, new ArrayList<>(),
          Clasificacion.MINISTERIO, null,
          null, null);
      List<DatoActividad> datoActividadList = new ArrayList<>(Arrays.asList(datoActividad1,datoActividad2,datoActividad3));
      List<DatoActividad> datoActividadList2 = new ArrayList<>(Arrays.asList(datoActividad4,datoActividad5,datoActividad6));


      Miembro miembro7 = new Miembro("Ignacio", "Campano", "3838383", TipoDocumento.DNI);
      Miembro miembro5 = new Miembro("Mati", "Fernandez", "38383812", TipoDocumento.DNI);


      persist(miembro5);
      persist(miembro7);

      persist(new Administrador("admin","nach", googleOrg));
      persist(new MiembroRol("miembro","nach", miembro7));

      OrganizacionRepositorio.getInstance().guardarOrganizacion(amazonOrg);
      OrganizacionRepositorio.getInstance().updateOrganizacion(amazonOrg,datoActividadList);

      OrganizacionRepositorio.getInstance().guardarOrganizacion(unicefOrg);
      OrganizacionRepositorio.getInstance().updateOrganizacion(unicefOrg,datoActividadList2);

      SectorTerritorialRepositorio.getInstance().crearSectorTerritorial(TipoSectorTerritorial.DEPARTAMENTO, new ArrayList<>(Arrays.asList(googleOrg,amazonOrg)),"Silicon Valley");
      SectorTerritorialRepositorio.getInstance().crearSectorTerritorial(TipoSectorTerritorial.DEPARTAMENTO, new ArrayList<>(Arrays.asList(unicefOrg)),"Nueva York");
    });
  }
}
