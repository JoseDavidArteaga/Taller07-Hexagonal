package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.dtos.DocenteDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.dtos.MiembroComiteDTORespuesta;

@Component
public class DocenteMapperInfraestructuraDominio {

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
        dto.setRol(null);
        dto.setFechaInicioRol(null);
        dto.setFechaFinRol(null);
        return dto;
    }

    public List<MiembroComiteDTORespuesta> mappearMiembrosComite(List<Docente> docentes) {
        if (docentes == null) return new ArrayList<>();
        return docentes.stream().map(this::mappearMiembroComite).collect(Collectors.toList());
    }
}
