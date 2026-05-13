package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.GestionarProductoGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.ProductoFormateadorResultadosIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.casosDeUso.GestionarProductoCUAdapter;

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