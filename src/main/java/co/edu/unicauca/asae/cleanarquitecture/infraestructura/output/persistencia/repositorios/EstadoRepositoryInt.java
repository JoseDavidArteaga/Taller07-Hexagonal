package co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.entidades.EstadoEntity;

public interface EstadoRepositoryInt extends CrudRepository<EstadoEntity, Integer> {

    List<EstadoEntity> findByEstado(String estado);
}
