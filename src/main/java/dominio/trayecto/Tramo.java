package dominio.trayecto;

import dominio.common.TipoConsumo;
import dominio.common.Ubicacion;
import dominio.common.Transporte;
import dominio.medicion.FactoresDeEmisionPorTipoConsumo;

import javax.persistence.*;
import repositorios.FactoresEmisionRepositorio;


@Entity
public class Tramo {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  private Ubicacion inicio;

  @OneToOne(cascade = CascadeType.ALL)
  private Ubicacion fin;

  @OneToOne(cascade = CascadeType.ALL)
  private Transporte tipoDeTransporte;

  public Tramo() {
  }

  public Tramo(Ubicacion inicioTramo, Ubicacion finTramo, Transporte tipoDeTrasporte) {
    this.inicio = inicioTramo;
    this.fin = finTramo;
    this.tipoDeTransporte = tipoDeTrasporte;
  }

  public Long getDistanciaTramo() {
    return tipoDeTransporte.calcularDistanciaTransporte(inicio, fin);
  }

  public Transporte getTipoDeTransporte() {
    return tipoDeTransporte;
  }

  public Double combustibleGastado() {
    return getDistanciaTramo() * getTipoDeTransporte().getConsumoDeCombustiblePorKm();
  }

  public Ubicacion getInicio() {
    return inicio;
  }

  public Ubicacion getFin() {
    return fin;
  }

  public TipoConsumo getTipoConsumo() {
    return tipoDeTransporte.getTipoCombustible().getTipoConsumo();
  }

  public Double huellaCarbono() {
    return FactoresEmisionRepositorio.getInstance().repositorioFactoresEmision().get(0).getFactoresDeEmision().getValor() * combustibleGastado();
  }

  public Long getId() {
    return id;
  }
}
