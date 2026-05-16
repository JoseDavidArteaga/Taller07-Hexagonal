package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.ProductoFormateadorResultadosIntPort;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.casosDeUso.GestionarDocenteCUAdapter;

@Configuration("miembrosComiteBeanConfigurations")
public class BeanConfigurations {

    @Bean
    public GestionarDocenteCUAdapter crearGestionarDocenteCU(
            GestionarDocenteGatewayIntPort objDocenteGateway,
            ProductoFormateadorResultadosIntPort objFormateador) {
        return new GestionarDocenteCUAdapter(objDocenteGateway, objFormateador);
    }
}
