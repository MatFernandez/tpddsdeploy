package dominio.organizacion;

import dominio.common.Ubicacion;
import dominio.medicion.FactoresDeEmisionPorTipoConsumo;
import dominio.organizacion.exceptions.VinculacionMiembroInvalida;
import dominio.organizacion.enums.*;
import dominio.common.DatoActividad;
import dominio.organizacion.interfaces.Contacto;
import servicios.ServicioCargaDatosActividad;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Entity
public class Organizacion {

  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Long id;

  @Column(name = "Razon_Social")
  private String razonSocial;


  @OneToOne(cascade = CascadeType.ALL)
  private Ubicacion ubicacionGeografica;
  @Column(name = "Tipo")
  @Enumerated(value = EnumType.STRING)
  private TipoOrganizacion tipoOrganizacion;


  @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
  private List<Sector> sectores;

  @Column(name = "Clasificacion")
  @Enumerated(value = EnumType.STRING)
  private Clasificacion clasificacion;

  @OneToMany(cascade = CascadeType.ALL)
  private List<DatoActividad> datosActividad;

  @Transient
  private ServicioCargaDatosActividad servicioCargaDatosActividad;

  @Transient
  private List<Contacto> contactosOrganizacion;
  @ManyToOne(cascade = CascadeType.ALL)
  private FactoresDeEmisionPorTipoConsumo factoresDeEmisionPorTipoConsumo;

  public Organizacion() {
  }


  public Organizacion(String razonSocial, Ubicacion ubicacionGeografica,
                      TipoOrganizacion tipoOrganizacion, List<Sector> sectores,
                      Clasificacion clasificacion,
                      List<DatoActividad> datos,
                      ServicioCargaDatosActividad servicioCargaDatosActividad, FactoresDeEmisionPorTipoConsumo factoresDeEmisionPorTipoConsumo) {
    this.razonSocial = razonSocial;
    this.ubicacionGeografica = ubicacionGeografica;
    this.tipoOrganizacion = tipoOrganizacion;
    this.sectores = sectores;
    this.clasificacion = clasificacion;
    this.datosActividad = datos;
    this.servicioCargaDatosActividad = servicioCargaDatosActividad;
    this.factoresDeEmisionPorTipoConsumo = factoresDeEmisionPorTipoConsumo;
    this.contactosOrganizacion = new ArrayList<>();
  }


  public void darAltaSector(Sector sector) {
    this.sectores.add(sector);
  }

  public void aceptarVinculacionMiembro(Miembro miembro, String sector) {

  }

  private void validarMiembro(Miembro miembro, Predicate<Miembro> criterioVinculacion) {
    if (criterioVinculacion.test(miembro)) {
      throw new VinculacionMiembroInvalida("El miembro no puede vincularse con dicha dominio.organizacion");
    }
  }

  public Integer cantidadDeSectores() {
    return this.sectores.size();
  }

  public Integer cantidadDeDatosDeActividad() {
    return this.datosActividad.size();
  }

  public List<DatoActividad> getDatosActividad() {
    return datosActividad;
  }

  public void setDatosActividad() {
    List<DatoActividad> resultado = servicioCargaDatosActividad.obtenerDatosActividad();
    this.datosActividad = resultado;
  }

  public void guardarDatosActividad(List<DatoActividad> lista) {
    if(datosActividad==null){
      datosActividad = new ArrayList<>();
    }
    this.datosActividad.addAll(lista);
  }

  public void agregarContacto(Contacto contacto) {
    this.contactosOrganizacion.add(contacto);
  }

  private double huellaCarbonoDeActividad(LocalDate fecha) {
    return this.getDatosActividad()
        .stream()
        .filter(dato -> dato.perteneceAlPeriodo(fecha))
        .mapToDouble(dato -> dato.getValor() * factoresDeEmisionPorTipoConsumo.getFactoresDeEmision().getValor())
        .sum();
  }

  private double huellaCarbonoDeMiembros(LocalDate fecha) {
    return this.sectores
        .stream()
        .mapToDouble(sector -> sector.huellaSector(fecha))
        .sum();
  }

  public Double huellaCarbonoOrganizacion(LocalDate fecha) {
    return this.huellaCarbonoDeActividad(fecha) + this.huellaCarbonoDeMiembros(fecha);
  }

  public Long getId() {
    return id;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public List<Sector> getSectores() {
    return sectores;
  }

  public TipoOrganizacion getTipoOrganizacion() {
    return tipoOrganizacion;
  }
}

