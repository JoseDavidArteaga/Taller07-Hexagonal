package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.EvaluacionEntity;


public interface EvaluacionRepositoryInt extends CrudRepository<EvaluacionEntity, Integer> {

    @Query("SELECT e FROM EvaluacionEntity e WHERE e.formatoA.idFormatoA = :idFormatoA")
    List<EvaluacionEntity> findHistoricoEvaluacionesByIdFormatoA(@Param("idFormatoA") Integer idFormatoA);

    List<EvaluacionEntity> findByFormatoA_IdFormatoA(Integer idFormatoA);

    Optional<EvaluacionEntity> findFirstByFormatoA_IdFormatoAOrderByFechaRegistroDescIdEvaluacionDesc(Integer idFormatoA);

    List<EvaluacionEntity> findAllByFechaRegistroAfter(Date fecha);

    List<EvaluacionEntity> findByFechaRegistroBetweenAndFormatoA_Docente_NombresContainingIgnoreCase(
            Date fechaInicio, Date fechaFin, String nombreDocente);

    long countByFormatoA_IdFormatoA(Integer idFormatoA);

    void deleteByFormatoA_IdFormatoA(Integer idFormatoA);
}
