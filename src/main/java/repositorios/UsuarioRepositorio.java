package repositorios;

import dominio.administrador.Administrador;
import dominio.administrador.MiembroRol;
import dominio.administrador.Rol;
import java.util.List;
import javax.persistence.Query;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class UsuarioRepositorio implements WithGlobalEntityManager, TransactionalOps {

  private static UsuarioRepositorio usuarioRepositorio;
  private List<Rol> tramos;

  private UsuarioRepositorio() {

    Query query = entityManager().createQuery("select a from Rol a"); //get Tramos
    tramos = query.getResultList();

  }

  public static UsuarioRepositorio getInstance() {
    if (usuarioRepositorio == null) {
      usuarioRepositorio = new UsuarioRepositorio();
    }
    return usuarioRepositorio;
  }

  public List<Rol> getAdministradores() {
    Query query = entityManager().createQuery(
        "select a from Rol a");
    return query.getResultList();
  }

  public Administrador getAdministrador(Long id) {
    Query query = entityManager().createQuery(
        "select a from Administrador a where id =:id", Administrador.class);
    query.setParameter("id", id);
    return (Administrador) query.getSingleResult();
  }

  public MiembroRol getMiembroRol(Long id) {
    Query query = entityManager().createQuery(
        "select a from MiembroRol a where id =:id", MiembroRol.class);
    query.setParameter("id", id);
    return (MiembroRol) query.getSingleResult();
  }

}
