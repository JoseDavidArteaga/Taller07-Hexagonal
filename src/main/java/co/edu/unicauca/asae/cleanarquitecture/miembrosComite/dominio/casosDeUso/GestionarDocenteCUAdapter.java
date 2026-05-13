package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.casosDeUso;

import java.util.List;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;

public class GestionarDocenteCUAdapter implements GestionarDocenteCUIntPort {

    private final GestionarDocenteGatewayIntPort objDocenteGateway;

    public GestionarDocenteCUAdapter(GestionarDocenteGatewayIntPort objDocenteGateway) {
        this.objDocenteGateway = objDocenteGateway;
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
    public List<Docente> listarMiembrosComite() {
        return this.objDocenteGateway.listar();
    }
}
