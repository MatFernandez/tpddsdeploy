package dominio.mediotransporte.enums;

import dominio.common.TipoConsumo;

public enum TipoCombustible {
  GASOIL(TipoConsumo.DIESEL),
  NAFTA(TipoConsumo.NAFTA),
  SUSTENTABLE(TipoConsumo.SUSTENTABLE);

  TipoConsumo tipoConsumo;

  TipoCombustible(TipoConsumo tipoConsumo) {
    this.tipoConsumo = tipoConsumo;
  }

  public TipoConsumo getTipoConsumo() {
    return this.tipoConsumo;
  }
}
