package co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.input;

import java.util.List;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;

public interface GestionarEvaluacionCUIntPort {

    List<Evaluacion> historicoPorFormatoA(Integer idFormatoA);
}
