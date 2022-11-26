package dominio.organizacion;

import dominio.organizacion.interfaces.Contacto;

//@Entity
public class ContactoEmail extends Contacto {
  String email;

  @Override
  public void enviarNotificacion(String mensaje) {
    //Implementacion de envio de mensaje al email.
  }

  public ContactoEmail() {
  }

  public ContactoEmail(String email) {
    this.email = email;
  }
}
