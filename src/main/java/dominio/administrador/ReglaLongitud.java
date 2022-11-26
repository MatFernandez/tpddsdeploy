package dominio.administrador;

import dominio.administrador.exceptions.PasswordInvalida;
import dominio.administrador.interfaces.ReglaPassword;

public class ReglaLongitud implements ReglaPassword {

  @Override
  public void aplicarRegla(String password) {
    //TODO: Agregar regex
    if (password.length() < 8) {
      throw new PasswordInvalida("La password es debil");
    }
  }
}
