import dominio.administrador.Administrador;
import dominio.administrador.AdministradorBuilder;
import dominio.administrador.ValidadorPassword;
import dominio.administrador.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdministradorBuilderTest {

  @Test
  public void crearAdministrador_con_clave_contenida_en_blacklist() {
    Exception exception = assertThrows(PasswordInvalida.class, () -> {
      ValidadorPassword validadorPassword = new ValidadorPassword();

      AdministradorBuilder administradorBuilder = new AdministradorBuilder(validadorPassword);
      administradorBuilder.crearAdministrador("Team15", "123456789");
    });

    String expectedMessage = "La contraseña es insegura, esta en la blacklist de passwords";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void crearAdministrador_con_password_debil() {
    Exception exception = assertThrows(PasswordInvalida.class, () -> {
      ValidadorPassword validadorPassword = new ValidadorPassword();

      AdministradorBuilder administradorBuilder = new AdministradorBuilder(validadorPassword);
      administradorBuilder.crearAdministrador("Team15", "C0rTa");
    });

    String expectedMessage = "La password es debil";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void crearAdministrador_con_usuario_default() {
    Exception exception = assertThrows(UsuarioInvalido.class, () -> {
      ValidadorPassword validadorPassword = new ValidadorPassword();

      AdministradorBuilder administradorBuilder = new AdministradorBuilder(validadorPassword);
      administradorBuilder.crearAdministrador("ADMIN", "C0rTa");
    });

    String expectedMessage = "No se pueden usar credenciales por defecto para el usuario.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void crearAdministrador_con_usuario_empty() {
    Exception exception = assertThrows(UsuarioInvalido.class, () -> {
      ValidadorPassword validadorPassword = new ValidadorPassword();

      AdministradorBuilder administradorBuilder = new AdministradorBuilder(validadorPassword);
      administradorBuilder.crearAdministrador("", "C0rTa");
    });

    String expectedMessage = "El usuario no puede ser vacio";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void crearAdministrador_genera_usuario_con_exito() {
    ValidadorPassword validadorPassword = new ValidadorPassword();

    AdministradorBuilder administradorBuilder = new AdministradorBuilder(validadorPassword);
    Administrador administrador = administradorBuilder.crearAdministrador("usuario_valido",
        "PassWord_ValiDa");

    assertEquals("usuario_valido", administrador.getUsuario());
  }
}
