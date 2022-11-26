package dominio.administrador.exceptions;

public class PasswordInvalida extends RuntimeException {
  public PasswordInvalida(String message) {
    super(message);
  }
}
