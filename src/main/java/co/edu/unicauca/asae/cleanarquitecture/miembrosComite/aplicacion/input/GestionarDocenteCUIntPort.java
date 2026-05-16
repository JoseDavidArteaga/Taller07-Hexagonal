package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.input;

import java.util.List;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;

public interface GestionarDocenteCUIntPort {

    Docente crear(Docente docente);

    List<Docente> listar();

    List<Docente> listarPorNombres(String nombres);

    List<Docente> listarPorGrupoYPatron(String grupo, String patron);

    List<Docente> listarMiembrosComite();
}
