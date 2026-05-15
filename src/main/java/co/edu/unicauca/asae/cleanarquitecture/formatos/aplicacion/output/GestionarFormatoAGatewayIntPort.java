package co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.dto.FormatoADetalleDTO;

public interface GestionarFormatoAGatewayIntPort {
    boolean existeFormatoAPorTitulo(String titulo);
    boolean existePorId(Integer idFormatoA);
    FormatoA guardar(FormatoA formatoA);
    List<FormatoA> listar();
    Optional<FormatoA> obtenerPorId(Integer id);
    Optional<FormatoA> obtenerPorTitulo(String titulo);
    Optional<FormatoA> obtenerDetallePorId(Integer id);
    List<FormatoA> listarPorDocente(Integer idDocente);
    int actualizarEstado(Integer idFormatoA, String estado);
    List<Evaluacion> obtenerHistoricoEvaluaciones(Integer idFormatoA);
    List<FormatoADetalleDTO> obtenerDetallePorTitulo(String titulo);
}
