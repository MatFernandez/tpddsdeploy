import dominio.administrador.ReglaDefecto;
import dominio.administrador.ReglaDenylist;
import dominio.administrador.ReglaLongitud;
import dominio.administrador.ValidadorPassword;
import dominio.administrador.exceptions.PasswordInvalida;
import org.junit.jupiter.api.Test;
import dominio.administrador.interfaces.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidadorPasswordTest {
  @Test
  public void laPasswordEsCorta() {
    String password = "c0rt4";
    assertThrows(PasswordInvalida.class, () -> {
      crearValidador().validarPassword(password);
    });
  }

  @Test
  public void laPasswordEsPorDefecto() {
    String password = "admin";
    assertThrows(PasswordInvalida.class, () -> {
      crearValidador().validarPassword(password);
    });
  }

  @Test
  public void laPasswordEstaEnDenyList() {
    String password = "password1";
    assertThrows(PasswordInvalida.class, () -> {
      crearValidador().validarPassword(password);
    });
  }

  @Test
  public void laPasswordEsCorrecta() {
    String password = "C0rr3ct4";
    assertDoesNotThrow(() -> {
      crearValidador().validarPassword(password);
    });
  }

  private ValidadorPassword crearValidador() {
    List<ReglaPassword> listaReglas = new ArrayList<>();
    listaReglas.add(new ReglaDefecto());
    listaReglas.add(new ReglaDenylist());
    listaReglas.add(new ReglaLongitud());
    return new ValidadorPassword(listaReglas);
  }
}
