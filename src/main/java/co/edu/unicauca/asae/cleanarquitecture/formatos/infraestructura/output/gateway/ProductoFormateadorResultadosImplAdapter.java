package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.gateway;

import org.springframework.stereotype.Service;
import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.ProductoFormateadorResultadosIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.controladorExcepciones.EntidadYaExisteException;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.controladorExcepciones.ReglaNegocioExcepcion;

@Service
public class ProductoFormateadorResultadosImplAdapter implements ProductoFormateadorResultadosIntPort {

    @Override
    public void retornarRespuestaErrorEntidadExiste(String mensaje) {
        EntidadYaExisteException objException = new EntidadYaExisteException(mensaje);
        throw objException;
    }

    @Override
    public void retornarRespuestaErrorReglaDeNegocio(String mensaje) {
        ReglaNegocioExcepcion objException = new ReglaNegocioExcepcion(mensaje);
        throw objException;
    }

}