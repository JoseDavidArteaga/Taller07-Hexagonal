package co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.input.mappers;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoADTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.mappers.FormatoAMapperInfraestructuraDominio;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos.Observacion;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.input.dtos.ObservacionDTORespuesta;

@Component
public class ObservacionMapperInfraestructuraDominio {

    private final FormatoAMapperInfraestructuraDominio formatoAMapper;

    public ObservacionMapperInfraestructuraDominio(FormatoAMapperInfraestructuraDominio formatoAMapper) {
        this.formatoAMapper = formatoAMapper;
    }

    public ObservacionDTORespuesta mappearObservacionARespuesta(Observacion observacion) {
        if (observacion == null) return null;
        ObservacionDTORespuesta dto = new ObservacionDTORespuesta();
        dto.setIdObservacion(observacion.getIdObservacion());
        dto.setDescripcion(observacion.getDescripcion());
        dto.setFechaRegistro(observacion.getFechaRegistro());
        return dto;
    }

    public FormatoADTORespuesta mappearFormatoARespuesta(FormatoA formatoA) {
        return this.formatoAMapper.mappearDeFormatoARespuesta(formatoA);
    }
}
