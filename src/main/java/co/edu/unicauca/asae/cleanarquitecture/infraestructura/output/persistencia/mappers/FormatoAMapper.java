package co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.cleanarquitecture.dominio.modelos.Docente;
import co.edu.unicauca.asae.cleanarquitecture.dominio.modelos.Estado;
import co.edu.unicauca.asae.cleanarquitecture.dominio.modelos.Evaluacion;
import co.edu.unicauca.asae.cleanarquitecture.dominio.modelos.FormatoA;
import co.edu.unicauca.asae.cleanarquitecture.dominio.modelos.Observacion;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.entidades.DocenteEntity;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.entidades.EstadoEntity;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.entidades.EvaluacionEntity;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.entidades.FormatoAEntity;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.entidades.ObservacionEntity;

@Component
public class FormatoAMapper {

    public FormatoAEntity mapDeDominioAEntity(FormatoA dominio) {
        if (dominio == null) return null;
        FormatoAEntity entity = new FormatoAEntity();
        entity.setIdFormatoA(dominio.getIdFormatoA());
        entity.setTitulo(dominio.getTitulo());
        entity.setFecha(dominio.getFecha());
        entity.setObjetivo(dominio.getObjetivo());

        if (dominio.getEstado() != null) {
            EstadoEntity estadoEntity = new EstadoEntity();
            estadoEntity.setIdEstado(dominio.getIdFormatoA());
            estadoEntity.setEstado(dominio.getEstado().getEstado());
            estadoEntity.setFormatoA(entity);
            entity.setEstado(estadoEntity);
        }

        if (dominio.getEvaluaciones() != null) {
            entity.setEvaluaciones(dominio.getEvaluaciones().stream()
                .map(this::mapEvaluacionDeDominioAEntity)
                .peek(e -> e.setFormatoA(entity))
                .collect(Collectors.toList()));
        }

        if (dominio.getDocentes() != null) {
            entity.setDocentes(dominio.getDocentes().stream()
                .map(this::mapDocenteDeDominioAEntitySimple)
                .collect(Collectors.toList()));
        }

        return entity;
    }

    public FormatoA mapDeEntityADominio(FormatoAEntity entity) {
        if (entity == null) return null;
        FormatoA dominio = new FormatoA();
        dominio.setIdFormatoA(entity.getIdFormatoA());
        dominio.setTitulo(entity.getTitulo());
        dominio.setFecha(entity.getFecha());
        dominio.setObjetivo(entity.getObjetivo());

        if (entity.getEstado() != null) {
            Estado estado = new Estado();
            estado.setIdEstado(entity.getEstado().getIdEstado());
            estado.setEstado(entity.getEstado().getEstado());
            dominio.setEstado(estado);
        }

        if (entity.getEvaluaciones() != null) {
            dominio.setEvaluaciones(entity.getEvaluaciones().stream()
                .map(this::mapEvaluacionDeEntityADominio)
                .collect(Collectors.toList()));
        }

        if (entity.getDocentes() != null) {
            dominio.setDocentes(entity.getDocentes().stream()
                .map(this::mapDocenteDeEntityADominioSimple)
                .collect(Collectors.toList()));
        }

        return dominio;
    }

    public List<FormatoA> mapDeEntityADominio(List<FormatoAEntity> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::mapDeEntityADominio).collect(Collectors.toList());
    }

    private EvaluacionEntity mapEvaluacionDeDominioAEntity(Evaluacion dominio) {
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

    private Evaluacion mapEvaluacionDeEntityADominio(EvaluacionEntity entity) {
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

    private DocenteEntity mapDocenteDeDominioAEntitySimple(Docente dominio) {
        DocenteEntity entity = new DocenteEntity();
        entity.setIdPersona(dominio.getIdPersona());
        entity.setTipoIdentificacion(dominio.getTipoIdentificacion());
        entity.setNumeroIdentificacion(dominio.getNumeroIdentificacion());
        entity.setNombres(dominio.getNombres());
        entity.setApellidos(dominio.getApellidos());
        entity.setCorreo(dominio.getCorreo());
        entity.setDepartamento(dominio.getDepartamento());
        return entity;
    }

    private Docente mapDocenteDeEntityADominioSimple(DocenteEntity entity) {
        Docente dominio = new Docente();
        dominio.setIdPersona(entity.getIdPersona());
        dominio.setTipoIdentificacion(entity.getTipoIdentificacion());
        dominio.setNumeroIdentificacion(entity.getNumeroIdentificacion());
        dominio.setNombres(entity.getNombres());
        dominio.setApellidos(entity.getApellidos());
        dominio.setCorreo(entity.getCorreo());
        dominio.setDepartamento(entity.getDepartamento());
        return dominio;
    }
}
