package dominio.administrador;

//@Entity


//Ojo revisar el dominio, creo que lo logico seria que una dominio.organizacion  tenga una lista de administradores, no
//me acuerdo en que habia quedado esto, pero asi como esta ahora esta descolgado

import dominio.organizacion.Organizacion;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
@DiscriminatorValue("A")
public class Administrador extends Rol{

  public Administrador(String usuario, String password) {
    super(usuario,password);
    this.organizacion = organizacion;
  }


  public Administrador(String usuario, String password, Organizacion organizacion) {
    super(usuario,password);
    this.organizacion = organizacion;
  }

  public Administrador() {
    super();
  }

  @OneToOne(cascade = CascadeType.ALL)
  private Organizacion organizacion;

  public Organizacion getOrganizacion() {
    return organizacion;
  }
}
