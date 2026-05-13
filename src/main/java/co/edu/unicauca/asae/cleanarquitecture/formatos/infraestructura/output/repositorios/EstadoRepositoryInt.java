package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.EstadoEntity;


public interface EstadoRepositoryInt extends CrudRepository<EstadoEntity, Integer> {

    List<EstadoEntity> findByEstado(String estado);
}
