package co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;

public interface GestionarFormatoAGatewayIntPort {
    boolean existeFormatoAPorTitulo(String titulo);
    boolean existePorId(Integer idFormatoA);
    FormatoA guardar(FormatoA formatoA);
    List<FormatoA> listar();
    Optional<FormatoA> obtenerPorId(Integer id);
    List<FormatoA> listarPorDocente(Integer idDocente);
    int actualizarEstado(Integer idFormatoA, String estado);
    List<Evaluacion> obtenerHistoricoEvaluaciones(Integer idFormatoA);
}
