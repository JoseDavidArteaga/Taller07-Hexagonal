package co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;

public interface GestionarFormatoAGatewayIntPort {
    boolean existeFormatoAPorTitulo(String titulo);
    FormatoA guardar(FormatoA formatoA);
    List<FormatoA> listar();
    Optional<FormatoA> obtenerPorId(Integer id);
    int actualizarEstado(Integer idFormatoA, String estado);
    List<Evaluacion> obtenerHistoricoEvaluaciones(Integer idFormatoA);
}
