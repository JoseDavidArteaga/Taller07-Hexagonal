package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Historico;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Rol;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.DocenteEntity;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.HistoricoEntity;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.RolEntity;

@Component
public class DocenteMapper {

    public DocenteEntity mapDeDominioAEntity(Docente dominio) {
        if (dominio == null) return null;
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

    public Docente mapDeEntityADominio(DocenteEntity entity) {
        if (entity == null) return null;
        Docente dominio = new Docente();
        dominio.setIdPersona(entity.getIdPersona());
        dominio.setTipoIdentificacion(entity.getTipoIdentificacion());
        dominio.setNumeroIdentificacion(entity.getNumeroIdentificacion());
        dominio.setNombres(entity.getNombres());
        dominio.setApellidos(entity.getApellidos());
        dominio.setCorreo(entity.getCorreo());
        dominio.setDepartamento(entity.getDepartamento());
        if (entity.getHistoricos() != null) {
            dominio.setHistoricos(entity.getHistoricos().stream()
                    .map(this::mapHistoricoDeEntityADominio)
                    .collect(Collectors.toList()));
        }
        return dominio;
    }

    public List<Docente> mapDeEntityADominio(List<DocenteEntity> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::mapDeEntityADominio).collect(Collectors.toList());
    }

    private Historico mapHistoricoDeEntityADominio(HistoricoEntity entity) {
        if (entity == null) return null;
        Historico dominio = new Historico();
        dominio.setIdHistorico(entity.getIdHistorico());
        dominio.setActivo(entity.getActivo());
        dominio.setFechaInicio(entity.getFechaInicio());
        dominio.setFechaFin(entity.getFechaFin());
        if (entity.getRol() != null) {
            Rol rol = new Rol();
            rol.setIdRol(entity.getRol().getIdRol());
            rol.setRolAsignado(entity.getRol().getRolAsignado());
            dominio.setRol(rol);
        }
        return dominio;
    }
}
