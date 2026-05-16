package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.casosDeUso;

import java.util.List;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.ProductoFormateadorResultadosIntPort;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;

public class GestionarDocenteCUAdapter implements GestionarDocenteCUIntPort {

    private final GestionarDocenteGatewayIntPort objDocenteGateway;
    private final ProductoFormateadorResultadosIntPort objFormateador;

    public GestionarDocenteCUAdapter(GestionarDocenteGatewayIntPort objDocenteGateway,
                                      ProductoFormateadorResultadosIntPort objFormateador) {
        this.objDocenteGateway = objDocenteGateway;
        this.objFormateador = objFormateador;
    }

    @Override
    public Docente crear(Docente docente) {
        if (this.objDocenteGateway.existeDocentePorCorreo(docente.getCorreo())) {
            this.objFormateador
                    .retornarRespuestaErrorEntidadExiste("Ya existe un docente con el correo " + docente.getCorreo());
            return null;
        }
        return this.objDocenteGateway.guardar(docente);
    }

    @Override
    public List<Docente> listar() {
        return this.objDocenteGateway.listar();
    }

    @Override
    public List<Docente> listarPorNombres(String nombres) {
        return this.objDocenteGateway.listarPorNombres(nombres);
    }

    @Override
    public List<Docente> listarPorGrupoYPatron(String grupo, String patron) {
        return this.objDocenteGateway.listarPorGrupoYPatron(grupo, patron);
    }

    @Override
    public List<Docente> listarMiembrosComite() {
        return this.objDocenteGateway.listar();
    }
}
