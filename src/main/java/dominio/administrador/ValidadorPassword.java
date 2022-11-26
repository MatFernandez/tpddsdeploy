package dominio.administrador;

import dominio.administrador.interfaces.ReglaPassword;

import java.util.ArrayList;
import java.util.List;

public class ValidadorPassword {

  List<ReglaPassword> reglasValidacion;

  public ValidadorPassword() {
    List<ReglaPassword> listaReglas = new ArrayList<>();
    listaReglas.add(new ReglaDefecto());
    listaReglas.add(new ReglaDenylist());
    listaReglas.add(new ReglaLongitud());
    this.reglasValidacion = listaReglas;
  }

  public ValidadorPassword(List<ReglaPassword> reglasCustom) {
    this.reglasValidacion = reglasCustom;
  }

  public void validarPassword(String password) {
    reglasValidacion.forEach(regla -> regla.aplicarRegla(password));
  }
}
