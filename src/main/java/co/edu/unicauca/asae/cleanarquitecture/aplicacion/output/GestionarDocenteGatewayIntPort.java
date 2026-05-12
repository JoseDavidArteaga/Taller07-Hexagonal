package co.edu.unicauca.asae.cleanarquitecture.aplicacion.output;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.asae.cleanarquitecture.dominio.modelos.Docente;

public interface GestionarDocenteGatewayIntPort {
    boolean existeDocentePorCorreo(String correo);
    Docente guardar(Docente docente);
    List<Docente> listar();
    Optional<Docente> obtenerPorId(Integer id);
}
