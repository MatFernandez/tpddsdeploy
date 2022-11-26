package dominio.common;

import dominio.common.enums.Periodicidad;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Entity

public class DatoActividad {

  @Id
  @GeneratedValue

  private Long id;

  @Enumerated
  private TipoConsumo tipoConsumo;
  private Double valor;
  @Enumerated
  private Periodicidad periodicidad;
  private LocalDate periodoImputacion;

  public DatoActividad() {
  }

  public DatoActividad(String tipoConsumo, String valor, String periodicidad, String periodoImputacion) {
    this.tipoConsumo = TipoConsumo.lookUp(tipoConsumo).orElseThrow(() -> new IllegalArgumentException("No existe el tipo de consumo"));
    this.valor = Double.valueOf(valor);
    this.periodicidad = Periodicidad.lookup(periodicidad);
    this.periodoImputacion = getLocalDate(periodoImputacion, this.periodicidad);
  }

  private LocalDate getLocalDate(String periodoImputacion, Periodicidad periodicidad) {
    if (Periodicidad.MENSUAL.equals(periodicidad)) {
      DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yyyy");
      YearMonth yearMonth = YearMonth.parse(periodoImputacion, fmt);
      return yearMonth.atDay(1);
    } else {
      DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy");
      Year year = Year.parse(periodoImputacion, fmt);
      return year.atMonth(1).atDay(1);
    }
  }

  public TipoConsumo getTipoConsumo() {
    return tipoConsumo;
  }

  public Double getValor() {
    return valor;
  }

  public Periodicidad getPeriodicidad() {
    return periodicidad;
  }

  public LocalDate getPeriodoImputacion() {
    return periodoImputacion;
  }

  public boolean perteneceAlPeriodo(LocalDate fecha) {
    if (periodicidad.equals(Periodicidad.ANUAL)) {
      return periodoImputacion.getYear() == (fecha.getYear());
    }
    return periodoImputacion.getMonth().equals(fecha.getMonth()) && periodoImputacion.getYear() == (fecha.getYear());
  }

  public Long getId() {
    return id;
  }
}
