package servicios;

import dominio.common.DatoActividad;

import java.util.List;

//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)

public abstract class ServicioCargaDatosActividad {

 // @Id
 // @GeneratedValue
 private Long id;

 public abstract List<DatoActividad> obtenerDatosActividad();
}
