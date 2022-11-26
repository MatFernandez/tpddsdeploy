package servicios;

import dominio.organizacion.interfaces.Contacto;

import java.time.LocalDate;
import java.util.List;

public class TareaNotificacion {

  private LocalDate fechaEjecucion;
  private List<Contacto> listaContactos;
  private String mensaje;
  private Boolean ejecutada;

  void ejecutar() {
    this.listaContactos.forEach(contacto -> contacto.enviarNotificacion(this.mensaje));
    this.ejecutada = true;
  }

  public boolean listaParaEjecutar() {
    return !ejecutada && fechaEjecucion.isBefore(LocalDate.now());
  }

  TareaNotificacion(LocalDate fechaEjecucion, List<Contacto> listaContactos, String mensaje) {
    this.fechaEjecucion = fechaEjecucion;
    this.listaContactos = listaContactos;
    this.mensaje = mensaje;
    this.ejecutada = false;
  }
}
