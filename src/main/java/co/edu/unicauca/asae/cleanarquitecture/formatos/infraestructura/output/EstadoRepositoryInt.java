package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface EstadoRepositoryInt extends CrudRepository<EstadoEntity, Integer> {

    List<EstadoEntity> findByEstado(String estado);
}
