package dominio.administrador;

import dominio.administrador.exceptions.UsuarioInvalido;

import java.util.Arrays;
import java.util.List;

public class AdministradorBuilder {

  private static final List<String> DEFAULTS_ADMIN_USER = Arrays.asList("ADMIN", "ADMINISTRADOR",
      "ROOT");

  private final ValidadorPassword validadorPassword;

  public AdministradorBuilder(ValidadorPassword validadorPassword) {
    this.validadorPassword = validadorPassword;
  }

  public Administrador crearAdministrador(String usuario, String password) {
    if (DEFAULTS_ADMIN_USER.stream().anyMatch(username -> username.equalsIgnoreCase(usuario))) {
      throw new UsuarioInvalido("No se pueden usar credenciales por defecto para el usuario.");
    }
    if (usuario.isEmpty()) {
      throw new UsuarioInvalido("El usuario no puede ser vacio");
    }
    validadorPassword.validarPassword(password);
    return new Administrador(usuario, password);
  }

}
