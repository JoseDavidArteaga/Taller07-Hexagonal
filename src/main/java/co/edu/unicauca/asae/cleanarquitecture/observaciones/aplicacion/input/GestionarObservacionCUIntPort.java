package co.edu.unicauca.asae.cleanarquitecture.observaciones.aplicacion.input;

import java.util.List;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos.Observacion;

public interface GestionarObservacionCUIntPort {

    Observacion crear(String descripcion, Integer idFormatoA, List<Integer> idsDocentes);

    FormatoA listarPorFormatoA(Integer idFormatoA);
}
