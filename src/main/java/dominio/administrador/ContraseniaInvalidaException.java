package dominio.administrador;

public class ContraseniaInvalidaException extends RuntimeException {

  public ContraseniaInvalidaException(String contraseña_inválida) {
    super(contraseña_inválida);
  }
}
