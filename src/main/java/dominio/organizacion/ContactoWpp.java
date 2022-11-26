package dominio.organizacion;

import dominio.organizacion.interfaces.Contacto;

//@Entity
public class ContactoWpp extends Contacto {
  int numeroCelular;

  @Override
  public void enviarNotificacion(String mensaje) {
    //Implementacion de envio de mensaje a wpp.
  }

  public ContactoWpp() {
  }

  public ContactoWpp(int numeroCelular) {
    this.numeroCelular = numeroCelular;
  }
}
