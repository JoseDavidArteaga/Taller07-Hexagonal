package co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.casosDeUso;

import java.util.List;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.input.GestionarEvaluacionCUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.GestionarFormatoAGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.ProductoFormateadorResultadosIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;

public class GestionarEvaluacionCUAdapter implements GestionarEvaluacionCUIntPort {

    private final GestionarFormatoAGatewayIntPort objFormatoAGateway;
    private final ProductoFormateadorResultadosIntPort objFormateador;

    public GestionarEvaluacionCUAdapter(GestionarFormatoAGatewayIntPort objFormatoAGateway,
                                        ProductoFormateadorResultadosIntPort objFormateador) {
        this.objFormatoAGateway = objFormatoAGateway;
        this.objFormateador = objFormateador;
    }

    @Override
    public List<Evaluacion> historicoPorFormatoA(Integer idFormatoA) {
        if (!this.objFormatoAGateway.existePorId(idFormatoA)) {
            this.objFormateador
                    .retornarRespuestaErrorEntidadNoExiste("No existe el formato A con id " + idFormatoA);
        }
        return this.objFormatoAGateway.obtenerHistoricoEvaluaciones(idFormatoA);
    }
}
