package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos.Observacion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.EvaluacionEntity;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.ObservacionEntity;

@Component
public class EvaluacionMapper {

    public EvaluacionEntity mapDeDominioAEntity(Evaluacion dominio) {
        if (dominio == null) return null;
        EvaluacionEntity entity = new EvaluacionEntity();
        entity.setIdEvaluacion(dominio.getIdEvaluacion());
        entity.setConcepto(dominio.getConcepto());
        entity.setFechaRegistro(dominio.getFechaRegistro());
        if (dominio.getObservaciones() != null) {
            entity.setObservaciones(dominio.getObservaciones().stream()
                .map(this::mapObservacionDeDominioAEntity)
                .peek(o -> o.setEvaluacion(entity))
                .collect(Collectors.toList()));
        }
        return entity;
    }

    public Evaluacion mapDeEntityADominio(EvaluacionEntity entity) {
        if (entity == null) return null;
        Evaluacion dominio = new Evaluacion();
        dominio.setIdEvaluacion(entity.getIdEvaluacion());
        dominio.setConcepto(entity.getConcepto());
        dominio.setFechaRegistro(entity.getFechaRegistro());
        if (entity.getObservaciones() != null) {
            dominio.setObservaciones(entity.getObservaciones().stream()
                .map(this::mapObservacionDeEntityADominio)
                .collect(Collectors.toList()));
        }
        return dominio;
    }

    public List<Evaluacion> mapDeEntityADominio(List<EvaluacionEntity> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::mapDeEntityADominio).collect(Collectors.toList());
    }

    private ObservacionEntity mapObservacionDeDominioAEntity(Observacion dominio) {
        ObservacionEntity entity = new ObservacionEntity();
        entity.setIdObservacion(dominio.getIdObservacion());
        entity.setDescripcion(dominio.getDescripcion());
        entity.setFechaRegistro(dominio.getFechaRegistro());
        return entity;
    }

    private Observacion mapObservacionDeEntityADominio(ObservacionEntity entity) {
        Observacion dominio = new Observacion();
        dominio.setIdObservacion(entity.getIdObservacion());
        dominio.setDescripcion(entity.getDescripcion());
        dominio.setFechaRegistro(entity.getFechaRegistro());
        return dominio;
    }
}
