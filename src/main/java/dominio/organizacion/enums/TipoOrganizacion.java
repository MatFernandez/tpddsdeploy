package dominio.organizacion.enums;

public enum TipoOrganizacion {
  GUBERNAMENTAL,
  ONG,
  EMPRESA,
  INSTITUCION;

  public String getName(){
    return this.name();
  }
}
