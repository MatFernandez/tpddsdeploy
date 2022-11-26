package dominio.administrador;

import dominio.administrador.exceptions.PasswordInvalida;
import dominio.administrador.interfaces.ReglaPassword;

import java.util.Arrays;
import java.util.List;

public class ReglaDefecto implements ReglaPassword {

  private static final List<String> DEFAULT_PASSWORDS = Arrays.asList("admin", "root",
      "administrador", "contrasenia");

  @Override
  public void aplicarRegla(String password) {
    boolean esPasswordDefault =
        DEFAULT_PASSWORDS.stream().anyMatch(unaPassword -> unaPassword.equalsIgnoreCase(password));
    if (esPasswordDefault) {
      throw new PasswordInvalida("No se debe usar passwords por defecto");
    }
  }
}
