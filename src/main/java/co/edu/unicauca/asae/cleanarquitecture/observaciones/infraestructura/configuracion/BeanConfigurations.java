package co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.GestionarEvaluacionGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.GestionarFormatoAGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.ProductoFormateadorResultadosIntPort;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.aplicacion.output.GestionarObservacionGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.casosDeUso.GestionarObservacionCUAdapter;

@Configuration("observacionesBeanConfigurations")
public class BeanConfigurations {

    @Bean
    public GestionarObservacionCUAdapter crearGestionarObservacionCU(
            GestionarObservacionGatewayIntPort objObservacionGateway,
            GestionarFormatoAGatewayIntPort objFormatoAGateway,
            GestionarEvaluacionGatewayIntPort objEvaluacionGateway,
            GestionarDocenteGatewayIntPort objDocenteGateway,
            ProductoFormateadorResultadosIntPort objFormateador) {
        return new GestionarObservacionCUAdapter(
                objObservacionGateway,
                objFormatoAGateway,
                objEvaluacionGateway,
                objDocenteGateway,
                objFormateador);
    }
}
