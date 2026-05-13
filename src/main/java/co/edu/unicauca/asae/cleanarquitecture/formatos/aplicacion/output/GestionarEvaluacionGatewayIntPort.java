package co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;

public interface GestionarEvaluacionGatewayIntPort {
    Evaluacion guardar(Evaluacion evaluacion);
    List<Evaluacion> listar();
    Optional<Evaluacion> obtenerPorId(Integer id);
    List<Evaluacion> obtenerHistoricoPorFormatoA(Integer idFormatoA);
}
