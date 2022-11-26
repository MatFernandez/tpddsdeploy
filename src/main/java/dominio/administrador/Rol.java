package dominio.administrador;

//@Entity


//Ojo revisar el dominio, creo que lo logico seria que una dominio.organizacion  tenga una lista de administradores, no
//me acuerdo en que habia quedado esto, pero asi como esta ahora esta descolgado

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import servicios.SHA1;

@Entity
@DiscriminatorColumn(name = "tipo", length = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Rol {

  @Id
  @GeneratedValue
  private Long id;

  private String usuario;
  private String password;

  public Long getId() {
    return id;
  }


  public Rol(String usuario, String password) {
      this.usuario = usuario;
      this.password = SHA1.getInstance().convertirConHash(password);
  }

  public String getUsuario() {
    return usuario;
  }

  public Rol() {
  }

  @Transient
  public String getDecriminatorValue() {
    return this.getClass().getAnnotation(DiscriminatorValue.class).value();
  }

  public String getPassword() {
    return password;
  }

  public void validarContraseniaHash(String contrasenia){
    if(!this.password.equals(contrasenia)){
      throw  new ContraseniaInvalidaException("Contraseña inválida");
    }
  }

}
