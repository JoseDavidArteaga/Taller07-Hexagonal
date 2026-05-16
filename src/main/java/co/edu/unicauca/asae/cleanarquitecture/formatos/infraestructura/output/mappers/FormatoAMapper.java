package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.DocenteEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Estado;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoPPA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoTIA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.EstadoEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.EvaluacionEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.FormatoAEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.FormatoPPAEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.FormatoTIAEntity;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos.Observacion;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.ObservacionEntity;

@Component
public class FormatoAMapper {

    public FormatoAEntity mapDeDominioAEntity(FormatoA dominio) {
        if (dominio == null) return null;
        FormatoAEntity entity;
        if (dominio instanceof FormatoPPA) {
            FormatoPPAEntity ppa = new FormatoPPAEntity();
            ppa.setNombreEstudiante(((FormatoPPA) dominio).getNombreEstudiante());
            ppa.setNombreAsesor(((FormatoPPA) dominio).getNombreAsesor());
            ppa.setLinkCartaAceptacion(((FormatoPPA) dominio).getLinkCartaAceptacion());
            ppa.setCodigoEstudiante(((FormatoPPA) dominio).getCodigoEstudiante());
            entity = ppa;
        } else if (dominio instanceof FormatoTIA) {
            FormatoTIAEntity tia = new FormatoTIAEntity();
            tia.setNombreEstudiante(((FormatoTIA) dominio).getNombreEstudiante());
            tia.setNombreAsesor(((FormatoTIA) dominio).getNombreAsesor());
            tia.setCodigoEstudiante(((FormatoTIA) dominio).getCodigoEstudiante());
            entity = tia;
        } else {
            entity = new FormatoAEntity();
        }
        entity.setIdFormatoA(dominio.getIdFormatoA());
        entity.setTitulo(dominio.getTitulo());
        entity.setFecha(dominio.getFecha());
        entity.setObjetivos(dominio.getObjetivos());

        if (dominio.getEstado() != null) {
            EstadoEntity estadoEntity = new EstadoEntity();
            estadoEntity.setIdEstado(dominio.getEstado().getIdEstado());
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

        if (dominio.getDocente() != null) {
            entity.setDocente(this.mapDocenteDeDominioAEntitySimple(dominio.getDocente()));
        }

        return entity;
    }

    public FormatoA mapDeEntityADominio(FormatoAEntity entity) {
        if (entity == null) return null;
        FormatoA dominio;
        if (entity instanceof FormatoPPAEntity) {
            FormatoPPA ppa = new FormatoPPA();
            ppa.setNombreEstudiante(((FormatoPPAEntity) entity).getNombreEstudiante());
            ppa.setNombreAsesor(((FormatoPPAEntity) entity).getNombreAsesor());
            ppa.setLinkCartaAceptacion(((FormatoPPAEntity) entity).getLinkCartaAceptacion());
            ppa.setCodigoEstudiante(((FormatoPPAEntity) entity).getCodigoEstudiante());
            dominio = ppa;
        } else if (entity instanceof FormatoTIAEntity) {
            FormatoTIA tia = new FormatoTIA();
            tia.setNombreEstudiante(((FormatoTIAEntity) entity).getNombreEstudiante());
            tia.setNombreAsesor(((FormatoTIAEntity) entity).getNombreAsesor());
            tia.setCodigoEstudiante(((FormatoTIAEntity) entity).getCodigoEstudiante());
            dominio = tia;
        } else {
            dominio = new FormatoA();
        }
        dominio.setIdFormatoA(entity.getIdFormatoA());
        dominio.setTitulo(entity.getTitulo());
        dominio.setFecha(entity.getFecha());
        dominio.setObjetivos(entity.getObjetivos());

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

        if (entity.getDocente() != null) {
            dominio.setDocente(this.mapDocenteDeEntityADominioSimple(entity.getDocente()));
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
