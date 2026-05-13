package co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.mappers;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos.Observacion;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.ObservacionEntity;

@Component
public class ObservacionMapper {

    public ObservacionEntity mapDeDominioAEntity(Observacion dominio) {
        if (dominio == null) return null;
        ObservacionEntity entity = new ObservacionEntity();
        entity.setIdObservacion(dominio.getIdObservacion());
        entity.setDescripcion(dominio.getDescripcion());
        entity.setFechaRegistro(dominio.getFechaRegistro());
        return entity;
    }

    public Observacion mapDeEntityADominio(ObservacionEntity entity) {
        if (entity == null) return null;
        Observacion dominio = new Observacion();
        dominio.setIdObservacion(entity.getIdObservacion());
        dominio.setDescripcion(entity.getDescripcion());
        dominio.setFechaRegistro(entity.getFechaRegistro());
        return dominio;
    }
}
