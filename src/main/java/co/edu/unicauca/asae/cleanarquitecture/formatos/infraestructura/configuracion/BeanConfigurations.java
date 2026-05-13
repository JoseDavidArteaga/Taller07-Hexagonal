package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.GestionarEvaluacionGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.GestionarFormatoAGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.GestionarProductoGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.ProductoFormateadorResultadosIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.casosDeUso.GestionarEvaluacionCUAdapter;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.casosDeUso.GestionarFormatoACUAdapter;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.casosDeUso.GestionarProductoCUAdapter;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.output.GestionarDocenteGatewayIntPort;

@Configuration("formatosBeanConfigurations")
public class BeanConfigurations {

    @Bean
    public GestionarProductoCUAdapter crearGestionarProductoCUInt(
            GestionarProductoGatewayIntPort objGestionarProductoGateway,
            ProductoFormateadorResultadosIntPort objProductoFormateadorResultados) {
        return new GestionarProductoCUAdapter(objGestionarProductoGateway, objProductoFormateadorResultados);
    }

    @Bean
    public GestionarFormatoACUAdapter crearGestionarFormatoACU(
            GestionarFormatoAGatewayIntPort objFormatoAGateway,
            GestionarDocenteGatewayIntPort objDocenteGateway,
            ProductoFormateadorResultadosIntPort objFormateador) {
        return new GestionarFormatoACUAdapter(objFormatoAGateway, objDocenteGateway, objFormateador);
    }

    @Bean
    public GestionarEvaluacionCUAdapter crearGestionarEvaluacionCU(
            GestionarFormatoAGatewayIntPort objFormatoAGateway,
            ProductoFormateadorResultadosIntPort objFormateador) {
        return new GestionarEvaluacionCUAdapter(objFormatoAGateway, objFormateador);
    }
}
