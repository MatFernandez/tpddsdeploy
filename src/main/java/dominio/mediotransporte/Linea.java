package dominio.mediotransporte;

import dominio.common.Ubicacion;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dominio.mediotransporte.enums.*;

import javax.persistence.*;

@Entity

public class Linea {

  @Id
  @GeneratedValue

  private Long id;
  @Enumerated
  private TipoTransportePublico transporte;
  private String nombreLinea;
  @OneToMany
  private List<Estacion> estaciones;


  public Linea() {
  }

  public Linea(TipoTransportePublico tipoDeTransportePublico, String nombreLinea,
               List<Estacion> estaciones) {
    this.transporte = tipoDeTransportePublico;
    this.nombreLinea = nombreLinea;

    // Carga estaciones ordenadas por indice de estacion
    Comparator<Estacion> compararPorIndice =
        (estacionA, estacionB) -> estacionA.getIndiceEstacion()
            - estacionB.getIndiceEstacion();
    this.estaciones = estaciones.stream().sorted(compararPorIndice).collect(Collectors.toList());
  }

  public Long getDistanciaRecorrido(Ubicacion puntoA, Ubicacion puntoB) {
    Estacion inicio = estacionMasCercana(puntoA);
    Estacion destino = estacionMasCercana(puntoB);
    Recorrido recorrido = getRecorrido(inicio, destino);

    return getDistanciaTotal(recorrido);
  }

  private Recorrido getRecorrido(Estacion inicio, Estacion destino) {
    Recorrido recorrido;
    if (inicio.getIndiceEstacion() > destino.getIndiceEstacion()) {
      recorrido = new Recorrido(destino.getIndiceEstacion(), inicio.getIndiceEstacion(), false);
    } else {
      recorrido = new Recorrido(inicio.getIndiceEstacion(), destino.getIndiceEstacion(), true);
    }
    return recorrido;
  }

  private Long getDistanciaTotal(Recorrido recorrido) {
    List<Estacion> estacionesRecorrido = getEstacionesRecorrido(recorrido);

    return distanciaTotal(recorrido.esIda(), estacionesRecorrido);
  }

  private Long distanciaTotal(boolean esIda, List<Estacion> recorrido) {
    Long distanciaTotal = 0L;
    if (esIda) {
      distanciaTotal =
          recorrido.stream().map(estacion -> estacion.getDistanciaProximaEstacionIda()).reduce(0L,
              (a, b) -> a + b);
    } else {
      distanciaTotal =
          recorrido.stream().map(estacion -> estacion.getDistanciaProximaEstacionVuelta())
              .reduce(0L, (a, b) -> a + b);
    }
    return distanciaTotal;
  }

  private List<Estacion> getEstacionesRecorrido(Recorrido recorrido) {
    List<Estacion> recorridoEstaciones = estaciones.stream().filter(estacion ->
            estacion.getIndiceEstacion() >= recorrido.getEstacionInicial()
                && estacion.getIndiceEstacion() <= recorrido.getEstacionFinal())
        .collect(Collectors.toList());
    return recorridoEstaciones;
  }


  private Estacion estacionMasCercana(Ubicacion puntoA) {
    Comparator<Estacion> masCercana =
        (a, b) -> (int) (a.getEstacionMasCercana(puntoA) - b.getEstacionMasCercana(puntoA));

    Optional<Estacion> estacionMasCercana = estaciones.stream().sorted(masCercana).findFirst();
    return estacionMasCercana.orElseThrow(() -> new RuntimeException("Estacion no encontrada"));
  }

  public String getNombreLinea() {
    return this.nombreLinea;
  }

  public List<Estacion> getEstaciones() {
    return this.estaciones;
  }

  public TipoTransportePublico getTransporte() {
    return transporte;
  }

  public Long getId() {
    return id;
  }
}
