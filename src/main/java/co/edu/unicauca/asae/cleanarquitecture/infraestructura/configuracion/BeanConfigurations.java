package co.edu.unicauca.asae.cleanarquitecture.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.asae.cleanarquitecture.aplicacion.output.GestionarProductoGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.aplicacion.output.ProductoFormateadorResultadosIntPort;
import co.edu.unicauca.asae.cleanarquitecture.dominio.casosDeUso.GestionarProductoCUAdapter;

@Configuration
public class BeanConfigurations {

    @Bean
    public GestionarProductoCUAdapter crearGestionarProductoCUInt(
            GestionarProductoGatewayIntPort objGestionarProductoGateway,
            ProductoFormateadorResultadosIntPort objProductoFormateadorResultados) {
        GestionarProductoCUAdapter objGestionarProductoCU = new GestionarProductoCUAdapter(objGestionarProductoGateway,
                objProductoFormateadorResultados);
        return objGestionarProductoCU;
    }
}