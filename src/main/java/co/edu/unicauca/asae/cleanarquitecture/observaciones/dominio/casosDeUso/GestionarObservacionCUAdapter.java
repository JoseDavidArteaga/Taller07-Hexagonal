package co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.casosDeUso;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.GestionarEvaluacionGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.GestionarFormatoAGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.ProductoFormateadorResultadosIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.aplicacion.input.GestionarObservacionCUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.aplicacion.output.GestionarObservacionGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos.Observacion;

public class GestionarObservacionCUAdapter implements GestionarObservacionCUIntPort {

    private static final String CONCEPTO_POR_CORREGIR = "Por corregir";

    private final GestionarObservacionGatewayIntPort objObservacionGateway;
    private final GestionarFormatoAGatewayIntPort objFormatoAGateway;
    private final GestionarEvaluacionGatewayIntPort objEvaluacionGateway;
    private final GestionarDocenteGatewayIntPort objDocenteGateway;
    private final ProductoFormateadorResultadosIntPort objFormateador;

    public GestionarObservacionCUAdapter(GestionarObservacionGatewayIntPort objObservacionGateway,
                                         GestionarFormatoAGatewayIntPort objFormatoAGateway,
                                         GestionarEvaluacionGatewayIntPort objEvaluacionGateway,
                                         GestionarDocenteGatewayIntPort objDocenteGateway,
                                         ProductoFormateadorResultadosIntPort objFormateador) {
        this.objObservacionGateway = objObservacionGateway;
        this.objFormatoAGateway = objFormatoAGateway;
        this.objEvaluacionGateway = objEvaluacionGateway;
        this.objDocenteGateway = objDocenteGateway;
        this.objFormateador = objFormateador;
    }

    @Override
    public Observacion crear(String descripcion, Integer idFormatoA, List<Integer> idsDocentes) {
        if (!this.objFormatoAGateway.existePorId(idFormatoA)) {
            this.objFormateador
                    .retornarRespuestaErrorEntidadNoExiste("No existe el formato A con id " + idFormatoA);
            return null;
        }
        if (idsDocentes == null || idsDocentes.isEmpty()) {
            this.objFormateador
                    .retornarRespuestaErrorReglaDeNegocio("Debe especificar al menos un docente que registra la observación");
            return null;
        }
        for (Integer idDocente : idsDocentes) {
            if (!this.objDocenteGateway.existeDocentePorId(idDocente)) {
                this.objFormateador
                        .retornarRespuestaErrorEntidadNoExiste("No existe el docente con id " + idDocente);
                return null;
            }
        }

        Integer idEvaluacionDestino = this.resolverEvaluacionDestino(idFormatoA);

        return this.objObservacionGateway.registrarObservacion(descripcion, idEvaluacionDestino, idsDocentes);
    }

    private Integer resolverEvaluacionDestino(Integer idFormatoA) {
        Optional<Evaluacion> ultima = this.objEvaluacionGateway.obtenerUltimaPorFormatoA(idFormatoA);
        if (!ultima.isPresent()) {
            Evaluacion creada = this.objEvaluacionGateway.crearEvaluacionInicialParaFormatoA(idFormatoA);
            creada.setConcepto(CONCEPTO_POR_CORREGIR);
            creada = this.objEvaluacionGateway.guardar(creada);
            return creada.getIdEvaluacion();
        }
        Evaluacion evaluacion = ultima.get();
        if (!CONCEPTO_POR_CORREGIR.equalsIgnoreCase(evaluacion.getConcepto())) {
            evaluacion.setConcepto(CONCEPTO_POR_CORREGIR);
            evaluacion = this.objEvaluacionGateway.guardar(evaluacion);
        }
        return evaluacion.getIdEvaluacion();
    }

    @Override
    public FormatoA listarPorFormatoA(Integer idFormatoA) {
        Optional<FormatoA> opt = this.objFormatoAGateway.obtenerDetallePorId(idFormatoA);
        if (!opt.isPresent()) {
            this.objFormateador
                    .retornarRespuestaErrorEntidadNoExiste("No existe el formato A con id " + idFormatoA);
            return null;
        }
        return opt.get();
    }
}
