package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.dtos.DocenteDTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.dtos.DocenteDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.dtos.MiembroComiteDTORespuesta;

@Component
public class DocenteMapperInfraestructuraDominio {

    public Docente mappearDePeticionADocente(DocenteDTOPeticion peticion) {
        if (peticion == null) return null;
        Docente docente = new Docente();
        docente.setTipoIdentificacion(peticion.getTipoIdentificacion());
        docente.setNumeroIdentificacion(peticion.getNumeroIdentificacion());
        docente.setNombres(peticion.getNombres());
        docente.setApellidos(peticion.getApellidos());
        docente.setCorreo(peticion.getCorreo());
        docente.setDepartamento(peticion.getDepartamento());
        return docente;
    }

    public DocenteDTORespuesta mappearDocenteARespuesta(Docente d) {
        if (d == null) return null;
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

    public List<DocenteDTORespuesta> mappearDocentesARespuesta(List<Docente> docentes) {
        if (docentes == null) return new ArrayList<>();
        return docentes.stream().map(this::mappearDocenteARespuesta).collect(Collectors.toList());
    }

    public MiembroComiteDTORespuesta mappearMiembroComite(Docente d) {
        if (d == null) return null;
        MiembroComiteDTORespuesta dto = new MiembroComiteDTORespuesta();
        dto.setIdPersona(d.getIdPersona());
        dto.setNombres(d.getNombres());
        dto.setApellidos(d.getApellidos());
        dto.setCorreo(d.getCorreo());
        dto.setDepartamento(d.getDepartamento());
        // Extraer rol y fechas del historico activo o el primero disponible
        if (d.getHistoricos() != null && !d.getHistoricos().isEmpty()) {
            var historicoOpt = d.getHistoricos().stream()
                    .filter(h -> h.getActivo() != null && h.getActivo())
                    .findFirst()
                    .or(() -> d.getHistoricos().stream().findFirst());
            if (historicoOpt.isPresent()) {
                var historico = historicoOpt.get();
                dto.setRol(historico.getRol() != null ? historico.getRol().getRolAsignado() : null);
                dto.setFechaInicioRol(historico.getFechaInicio() != null
                        ? java.sql.Date.valueOf(historico.getFechaInicio()) : null);
                dto.setFechaFinRol(historico.getFechaFin() != null
                        ? java.sql.Date.valueOf(historico.getFechaFin()) : null);
            }
        }
        return dto;
    }

    public List<MiembroComiteDTORespuesta> mappearMiembrosComite(List<Docente> docentes) {
        if (docentes == null) return new ArrayList<>();
        return docentes.stream().map(this::mappearMiembroComite).collect(Collectors.toList());
    }
}
