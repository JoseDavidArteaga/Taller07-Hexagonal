package co.edu.unicauca.asae.cleanarquitecture.observaciones.aplicacion.output;

import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos.Observacion;

public interface GestionarObservacionGatewayIntPort {

    Observacion registrarObservacion(String descripcion, Integer idEvaluacion, java.util.List<Integer> idsDocentes);
}
