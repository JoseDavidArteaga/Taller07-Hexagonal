package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.output;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;

public interface GestionarDocenteGatewayIntPort {
    boolean existeDocentePorCorreo(String correo);
    boolean existeDocentePorId(Integer idDocente);
    Docente guardar(Docente docente);
    List<Docente> listar();
    List<Docente> listarPorNombres(String nombres);
    Optional<Docente> obtenerPorId(Integer id);
    Optional<Docente> obtenerPorCorreo(String correo);
    List<Docente> listarPorGrupoYPatron(String grupo, String patron);
}
