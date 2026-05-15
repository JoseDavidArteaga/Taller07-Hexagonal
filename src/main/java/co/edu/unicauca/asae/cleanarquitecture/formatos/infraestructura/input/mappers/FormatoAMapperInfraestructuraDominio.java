package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Estado;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoPPA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoTIA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.DocenteDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.DocenteFormatoADTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.EstadoDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.EvaluacionDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoADTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoADTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoPPADTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoPPADTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoTIADTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoTIADTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.ObservacionDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos.Observacion;

@Component
public class FormatoAMapperInfraestructuraDominio {

    public FormatoA mappearDePeticionAFormatoA(FormatoADTOPeticion peticion) {
        if (peticion == null) return null;
        FormatoA dominio;
        if (peticion instanceof FormatoPPADTOPeticion) {
            FormatoPPADTOPeticion ppaPeticion = (FormatoPPADTOPeticion) peticion;
            FormatoPPA ppa = new FormatoPPA();
            ppa.setNombreEstudiante(ppaPeticion.getNombreEstudiante());
            ppa.setNombreAsesor(ppaPeticion.getNombreAsesor());
            ppa.setLinkCartaAceptacion(ppaPeticion.getLinkCartaAceptacion());
            dominio = ppa;
        } else if (peticion instanceof FormatoTIADTOPeticion) {
            FormatoTIADTOPeticion tiaPeticion = (FormatoTIADTOPeticion) peticion;
            FormatoTIA tia = new FormatoTIA();
            tia.setNombreEstudiante(tiaPeticion.getNombreEstudiante());
            tia.setNombreAsesor(tiaPeticion.getNombreAsesor());
            dominio = tia;
        } else {
            dominio = new FormatoA();
        }
        dominio.setTitulo(peticion.getTitulo());
        dominio.setObjetivo(peticion.getObjetivo());
        if (peticion.getDocente() != null) {
            dominio.setDocente(this.mappearDeDTOADocente(peticion.getDocente()));
        }
        return dominio;
    }

    public FormatoADTORespuesta mappearDeFormatoARespuesta(FormatoA dominio) {
        if (dominio == null) return null;
        FormatoADTORespuesta respuesta;
        if (dominio instanceof FormatoPPA) {
            FormatoPPADTORespuesta ppaRespuesta = new FormatoPPADTORespuesta();
            ppaRespuesta.setNombreEstudiante(((FormatoPPA) dominio).getNombreEstudiante());
            ppaRespuesta.setNombreAsesor(((FormatoPPA) dominio).getNombreAsesor());
            ppaRespuesta.setLinkCartaAceptacion(((FormatoPPA) dominio).getLinkCartaAceptacion());
            respuesta = ppaRespuesta;
        } else if (dominio instanceof FormatoTIA) {
            FormatoTIADTORespuesta tiaRespuesta = new FormatoTIADTORespuesta();
            tiaRespuesta.setNombreEstudiante(((FormatoTIA) dominio).getNombreEstudiante());
            tiaRespuesta.setNombreAsesor(((FormatoTIA) dominio).getNombreAsesor());
            respuesta = tiaRespuesta;
        } else {
            respuesta = new FormatoADTORespuesta();
        }
        respuesta.setIdFormatoA(dominio.getIdFormatoA());
        respuesta.setTitulo(dominio.getTitulo());
        respuesta.setFecha(dominio.getFecha());
        respuesta.setObjetivo(dominio.getObjetivo());
        respuesta.setEstado(mappearEstado(dominio.getEstado()));
        if (dominio.getEvaluaciones() != null) {
            respuesta.setEvaluaciones(dominio.getEvaluaciones().stream()
                    .map(this::mappearEvaluacion)
                    .collect(Collectors.toList()));
        } else {
            respuesta.setEvaluaciones(new ArrayList<>());
        }
        if (dominio.getDocente() != null) {
            respuesta.setDocente(this.mappearDocente(dominio.getDocente()));
        }
        return respuesta;
    }

    public List<FormatoADTORespuesta> mappearDeFormatosARespuesta(List<FormatoA> lista) {
        if (lista == null) return new ArrayList<>();
        return lista.stream().map(this::mappearDeFormatoARespuesta).collect(Collectors.toList());
    }

    private Docente mappearDeDTOADocente(DocenteFormatoADTOPeticion dto) {
        Docente docente = new Docente();
        docente.setIdPersona(dto.getIdPersona());
        docente.setTipoIdentificacion(dto.getTipoIdentificacion());
        docente.setNumeroIdentificacion(dto.getNumeroIdentificacion());
        docente.setNombres(dto.getNombres());
        docente.setApellidos(dto.getApellidos());
        docente.setCorreo(dto.getCorreo());
        docente.setDepartamento(dto.getDepartamento());
        return docente;
    }

    private DocenteDTORespuesta mappearDocente(Docente d) {
        DocenteDTORespuesta dto = new DocenteDTORespuesta();
        dto.setIdPersona(d.getIdPersona());
        dto.setTipoIdentificacion(d.getTipoIdentificacion());
        dto.setNumeroIdentificacion(d.getNumeroIdentificacion());
        dto.setNombres(d.getNombres());
        dto.setApellidos(d.getApellidos());
        dto.setCorreo(d.getCorreo());
        dto.setDepartamento(d.getDepartamento());
        return dto;
    }

    private EstadoDTORespuesta mappearEstado(Estado e) {
        if (e == null) return null;
        EstadoDTORespuesta dto = new EstadoDTORespuesta();
        dto.setIdEstado(e.getIdEstado());
        dto.setEstado(e.getEstado());
        return dto;
    }

    public EvaluacionDTORespuesta mappearEvaluacion(Evaluacion ev) {
        if (ev == null) return null;
        EvaluacionDTORespuesta dto = new EvaluacionDTORespuesta();
        dto.setIdEvaluacion(ev.getIdEvaluacion());
        dto.setConcepto(ev.getConcepto());
        dto.setFechaRegistro(ev.getFechaRegistro());
        if (ev.getObservaciones() != null) {
            dto.setObservaciones(ev.getObservaciones().stream()
                    .map(this::mappearObservacion)
                    .collect(Collectors.toList()));
        } else {
            dto.setObservaciones(new ArrayList<>());
        }
        return dto;
    }

    public List<EvaluacionDTORespuesta> mappearEvaluaciones(List<Evaluacion> evaluaciones) {
        if (evaluaciones == null) return new ArrayList<>();
        return evaluaciones.stream().map(this::mappearEvaluacion).collect(Collectors.toList());
    }

    private ObservacionDTORespuesta mappearObservacion(Observacion o) {
        ObservacionDTORespuesta dto = new ObservacionDTORespuesta();
        dto.setIdObservacion(o.getIdObservacion());
        dto.setDescripcion(o.getDescripcion());
        dto.setFechaRegistro(o.getFechaRegistro());
        return dto;
    }
}
