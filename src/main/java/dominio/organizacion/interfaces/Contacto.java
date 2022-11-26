package dominio.organizacion.interfaces;

//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Contacto {

  // @Id
  // @GeneratedValue

  private Long id;

  public abstract void enviarNotificacion(String mensaje);
}
