package dominio.mediotransporte;

import dominio.common.Ubicacion;
import dominio.common.ObtenerDistancia;
import restclient.classes.ObtenerDistanciaGeodds;

import javax.persistence.*;

@Entity
public class Estacion {

  @Id
  @GeneratedValue
  public Long id;

  private String nombreEstacion;
  @OneToOne(cascade = CascadeType.ALL)
  private Ubicacion ubicacionEstacion;
  private Long distanciaProximaEstacionIda;
  private Long distanciaProximaEstacionVuelta;
  private int indiceEstacion;
  //@OneToOne(cascade = CascadeType.ALL)
  //@Transient
  //private ObtenerDistancia obtenerDistancia;


  public Estacion() {
  }

  public Estacion(String nombreEstacion, Ubicacion ubicacionEstacion,
                  Long distanciaProximaEstacionIda, Long distanciaProximaEstacionVuelta,
                  int indiceEstacion, ObtenerDistancia obtenerDistancia) {
    this.nombreEstacion = nombreEstacion;
    this.ubicacionEstacion = ubicacionEstacion;
    this.distanciaProximaEstacionIda = distanciaProximaEstacionIda;
    this.distanciaProximaEstacionVuelta = distanciaProximaEstacionVuelta;
    this.indiceEstacion = indiceEstacion;
    //this.obtenerDistancia = obtenerDistancia;
  }

  public String getNombreEstacion() {
    return nombreEstacion;
  }

  public Ubicacion getUbicacionEstacion() {
    return ubicacionEstacion;
  }

  public Long getDistanciaProximaEstacionIda() {
    return distanciaProximaEstacionIda;
  }

  public Long getDistanciaProximaEstacionVuelta() {
    return distanciaProximaEstacionVuelta;
  }

  public int getIndiceEstacion() {
    return indiceEstacion;
  }

  public Long getEstacionMasCercana(Ubicacion ubicacion) {
    return ObtenerDistanciaGeodds.getInstancia().obtenerDistancia(ubicacion, this.ubicacionEstacion).getValor();
  }
}
