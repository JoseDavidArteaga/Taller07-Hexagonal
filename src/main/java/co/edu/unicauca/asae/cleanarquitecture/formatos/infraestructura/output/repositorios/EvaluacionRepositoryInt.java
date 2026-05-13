package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.EvaluacionEntity;


public interface EvaluacionRepositoryInt extends CrudRepository<EvaluacionEntity, Integer> {

    @Query("SELECT e FROM EvaluacionEntity e WHERE e.formatoA.idFormatoA = :idFormatoA")
    List<EvaluacionEntity> findHistoricoEvaluacionesByIdFormatoA(@Param("idFormatoA") Integer idFormatoA);

    List<EvaluacionEntity> findByFormatoA_IdFormatoA(Integer idFormatoA);

    List<EvaluacionEntity> findAllByFechaRegistroAfter(Date fecha);

    long countByFormatoA_IdFormatoA(Integer idFormatoA);

    void deleteByFormatoA_IdFormatoA(Integer idFormatoA);
}
