package co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.entidades.ObservacionEntity;

public interface ObservacionRepositoryInt extends CrudRepository<ObservacionEntity, Integer> {

    List<ObservacionEntity> findByEvaluacion_IdEvaluacion(Integer idEvaluacion);
}
