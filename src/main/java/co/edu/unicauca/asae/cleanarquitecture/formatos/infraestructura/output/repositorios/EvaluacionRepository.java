package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.EvaluacionEntity;

public interface EvaluacionRepository extends CrudRepository<EvaluacionEntity, Integer> {

    List<EvaluacionEntity> findByFechaRegistroBetweenAndFormatoA_Docente_NombresContainingIgnoreCaseOrFormatoA_Docente_ApellidosContainingIgnoreCase(
            Date fechaInicio,
            Date fechaFin,
            String nombre,
            String apellido);
}
