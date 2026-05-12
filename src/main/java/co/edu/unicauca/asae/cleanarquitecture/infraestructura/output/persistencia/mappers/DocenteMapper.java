package co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.cleanarquitecture.dominio.modelos.Docente;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.entidades.DocenteEntity;

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
        return dominio;
    }

    public List<Docente> mapDeEntityADominio(List<DocenteEntity> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::mapDeEntityADominio).collect(Collectors.toList());
    }
}
