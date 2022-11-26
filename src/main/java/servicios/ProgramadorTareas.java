package servicios;

import java.util.ArrayList;
import java.util.List;

public class ProgramadorTareas {
  List<TareaNotificacion> tareasProgramadas;

  public ProgramadorTareas(List<TareaNotificacion> tareasProgramadas) {
    this.tareasProgramadas = tareasProgramadas;
  }

  public void agregarTareasProgramadas(List<TareaNotificacion> tareasProgramadas) {
    this.tareasProgramadas = tareasProgramadas;
  }

  public void correrTareas() {
    this.tareasProgramadas.stream()
        .filter(tareaNotificacion -> tareaNotificacion.listaParaEjecutar())
        .forEach(tareaNotificacion -> tareaNotificacion.ejecutar());
  }

  //Crontab
  public static void main(String[] args) {
    List<TareaNotificacion> tareaNotificacions = new ArrayList<>();
    ProgramadorTareas programadorTareas = new ProgramadorTareas(tareaNotificacions);
    programadorTareas.correrTareas();
  }
}
