package co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface ObservacionRepositoryInt extends CrudRepository<ObservacionEntity, Integer> {

    List<ObservacionEntity> findByEvaluacion_IdEvaluacion(Integer idEvaluacion);
}
