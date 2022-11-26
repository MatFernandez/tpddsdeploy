package dominio.administrador;

//@Entity


//Ojo revisar el dominio, creo que lo logico seria que una dominio.organizacion  tenga una lista de administradores, no
//me acuerdo en que habia quedado esto, pero asi como esta ahora esta descolgado

import dominio.organizacion.Miembro;
import dominio.organizacion.enums.TipoDocumento;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Cascade;

@Entity
@DiscriminatorValue("M")
public class MiembroRol extends Rol {


  public MiembroRol(String usuario, String password, String nombre, String apellido, String numeroDoc) {
    super(usuario, password);
    this.miembro = new Miembro(nombre,apellido,numeroDoc, TipoDocumento.DNI);
  }

  public MiembroRol(String usuario, String password, Miembro miembro){
    super(usuario, password);
    this.miembro = miembro;
  }

  public MiembroRol() {
    super();
  }

  @OneToOne(cascade = CascadeType.ALL)
  private Miembro miembro;

  public Miembro getMiembro() {
    return miembro;
  }
}
