package co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.casosDeUso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.input.GestionarFormatoACUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.GestionarFormatoAGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.ProductoFormateadorResultadosIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Estado;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;

public class GestionarFormatoACUAdapter implements GestionarFormatoACUIntPort {

    private static final String ESTADO_INICIAL = "En formulación";

    private final GestionarFormatoAGatewayIntPort objFormatoAGateway;
    private final GestionarDocenteGatewayIntPort objDocenteGateway;
    private final ProductoFormateadorResultadosIntPort objFormateador;

    public GestionarFormatoACUAdapter(GestionarFormatoAGatewayIntPort objFormatoAGateway,
                                      GestionarDocenteGatewayIntPort objDocenteGateway,
                                      ProductoFormateadorResultadosIntPort objFormateador) {
        this.objFormatoAGateway = objFormatoAGateway;
        this.objDocenteGateway = objDocenteGateway;
        this.objFormateador = objFormateador;
    }

    @Override
    public FormatoA crear(FormatoA objFormatoA) {
        if (this.objFormatoAGateway.existeFormatoAPorTitulo(objFormatoA.getTitulo())) {
            this.objFormateador
                    .retornarRespuestaErrorEntidadExiste("Ya existe un formato A con el titulo " + objFormatoA.getTitulo());
            return null;
        }

        if (objFormatoA.getDocentes() == null || objFormatoA.getDocentes().isEmpty()) {
            this.objFormateador
                    .retornarRespuestaErrorReglaDeNegocio("Un formato A debe tener al menos un docente director");
            return null;
        }

        Date fechaActual = new Date();
        if (objFormatoA.getFecha() == null) {
            objFormatoA.setFecha(fechaActual);
        }

        List<Docente> docentesResueltos = new ArrayList<>();
        for (Docente docenteEntrada : objFormatoA.getDocentes()) {
            Docente resuelto = resolverDocente(docenteEntrada);
            docentesResueltos.add(resuelto);
        }
        objFormatoA.setDocentes(docentesResueltos);

        Estado estadoInicial = new Estado();
        estadoInicial.setEstado(ESTADO_INICIAL);
        objFormatoA.setEstado(estadoInicial);

        return this.objFormatoAGateway.guardar(objFormatoA);
    }

    private Docente resolverDocente(Docente docenteEntrada) {
        if (docenteEntrada.getIdPersona() != null) {
            Optional<Docente> existente = this.objDocenteGateway.obtenerPorId(docenteEntrada.getIdPersona());
            if (existente.isPresent()) {
                return existente.get();
            }
            this.objFormateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el docente con id " + docenteEntrada.getIdPersona());
            return null;
        }
        if (docenteEntrada.getCorreo() != null
                && this.objDocenteGateway.existeDocentePorCorreo(docenteEntrada.getCorreo())) {
            return this.objDocenteGateway.listar().stream()
                    .filter(d -> docenteEntrada.getCorreo().equalsIgnoreCase(d.getCorreo()))
                    .findFirst()
                    .orElseGet(() -> this.objDocenteGateway.guardar(docenteEntrada));
        }
        return this.objDocenteGateway.guardar(docenteEntrada);
    }

    @Override
    public List<FormatoA> listar() {
        return this.objFormatoAGateway.listar();
    }

    @Override
    public FormatoA consultarPorId(Integer idFormatoA) {
        Optional<FormatoA> opt = this.objFormatoAGateway.obtenerPorId(idFormatoA);
        if (!opt.isPresent()) {
            this.objFormateador
                    .retornarRespuestaErrorEntidadNoExiste("No existe el formato A con id " + idFormatoA);
            return null;
        }
        return opt.get();
    }

    @Override
    public List<FormatoA> consultarPorDocente(Integer idDocente) {
        if (!this.objDocenteGateway.existeDocentePorId(idDocente)) {
            this.objFormateador
                    .retornarRespuestaErrorEntidadNoExiste("No existe el docente con id " + idDocente);
            return new ArrayList<>();
        }
        return this.objFormatoAGateway.listarPorDocente(idDocente);
    }

    @Override
    public FormatoA actualizarEstado(Integer idFormatoA, String nuevoEstado) {
        if (!this.objFormatoAGateway.existePorId(idFormatoA)) {
            this.objFormateador
                    .retornarRespuestaErrorEntidadNoExiste("No existe el formato A con id " + idFormatoA);
            return null;
        }
        int filasActualizadas = this.objFormatoAGateway.actualizarEstado(idFormatoA, nuevoEstado);
        if (filasActualizadas == 0) {
            this.objFormateador
                    .retornarRespuestaErrorReglaDeNegocio("No se pudo actualizar el estado del formato A " + idFormatoA);
            return null;
        }
        return this.objFormatoAGateway.obtenerPorId(idFormatoA).orElse(null);
    }
}
