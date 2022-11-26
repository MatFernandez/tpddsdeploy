package dominio.administrador.exceptions;

public class UsuarioInvalido extends RuntimeException {
  public UsuarioInvalido(String message) {
    super(message);
  }
}
