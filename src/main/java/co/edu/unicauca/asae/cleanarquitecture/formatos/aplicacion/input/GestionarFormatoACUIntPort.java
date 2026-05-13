package co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.input;

import java.util.List;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;

public interface GestionarFormatoACUIntPort {

    FormatoA crear(FormatoA objFormatoA);

    List<FormatoA> listar();

    FormatoA consultarPorId(Integer idFormatoA);

    List<FormatoA> consultarPorDocente(Integer idDocente);

    FormatoA actualizarEstado(Integer idFormatoA, String nuevoEstado);
}
