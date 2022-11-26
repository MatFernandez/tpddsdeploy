package dominio.common;

import dominio.medicion.enums.Actividad;
import dominio.medicion.enums.Alcance;
import dominio.medicion.enums.Unidad;

import java.util.Arrays;
import java.util.Optional;
import javax.persistence.Enumerated;


public enum TipoConsumo {
  GAS_NATURAL("Gas Natural", Unidad.m3, Actividad.COMBUSTION_FIJA, Alcance.EMISIONES_DIRECTAS),
  DIESEL("Diesel", Unidad.lt, Actividad.COMBUSTION_FIJA, Alcance.EMISIONES_DIRECTAS),
  NAFTA("Nafta", Unidad.lt, Actividad.COMBUSTION_FIJA, Alcance.EMISIONES_DIRECTAS),
  CARBON("Carbon", Unidad.kg, Actividad.COMBUSTION_FIJA, Alcance.EMISIONES_DIRECTAS),
  DIESEL_CONSUMIDO("Diesel Consumido", Unidad.lts, Actividad.COMBUSTION_MOVIL,
      Alcance.EMISIONES_DIRECTAS),
  NAFTA_CONSUMIDA("Nafta Consumida", Unidad.lts, Actividad.COMBUSTION_MOVIL,
      Alcance.EMISIONES_DIRECTAS),
  ELECTRICIDAD("Electricidad", Unidad.Kwh, Actividad.ELECTICIDAD_ADQUIRIDA_Y_CONSUMIDA,
      Alcance.EMISIONES_INDIRECTAS_DE_ELECTRICIDAD),
  DISTANCIA_MEDIA("Distancia Media Recorridad", Unidad.km,
      Actividad.LOGISTICA_DE_PRODUCTOS_Y_RESIDUOS, Alcance.OTRAS_MEDICIONES_INCONTROLABLES),
  SUSTENTABLE("Sustentable", Unidad.km, Actividad.LIBRE_DE_CONTAMINACION, Alcance.OTRAS_MEDICIONES_INCONTROLABLES);

  public String tipo;
  public Unidad unidad;
  public Actividad actividad;
  public Alcance alcance;

  TipoConsumo(String tipo, Unidad unidad, Actividad actividad, Alcance alcance) {
    this.tipo = tipo;
    this.unidad = unidad;
    this.actividad = actividad;
    this.alcance = alcance;
  }

  public static Optional<TipoConsumo> lookUp(String tipo) {
    return Arrays.stream(values()).filter(t -> t.tipo.equalsIgnoreCase(tipo)).findFirst();
  }

  public String getTipo() {
    return tipo;
  }

  public Unidad getUnidad() {
    return unidad;
  }

  public Actividad getActividad() {
    return actividad;
  }

  public Alcance getAlcance() {
    return alcance;
  }

}
