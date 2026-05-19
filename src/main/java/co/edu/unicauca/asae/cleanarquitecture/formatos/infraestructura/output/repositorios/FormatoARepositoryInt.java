package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.dto.FormatoADetalleDTO;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.FormatoAEntity;


public interface FormatoARepositoryInt extends CrudRepository<FormatoAEntity, Integer> {

    boolean existsByTitulo(String titulo);

    Optional<FormatoAEntity> findByTitulo(String titulo);

    List<FormatoAEntity> findByTituloContaining(String titulo);

    List<FormatoAEntity> findAllByFechaBetween(Date fechaInicio, Date fechaFin);

    List<FormatoAEntity> findByDocente_IdPersona(Integer idPersona);

    @EntityGraph(attributePaths = {"docente", "estado"})
    List<FormatoAEntity> findFormatosConDocenteByDocente_IdPersona(Integer idPersona);

    List<FormatoAEntity> findByDocente_NombresIgnoreCase(String nombreDocente);

    // Solo una bag (evaluaciones) para evitar MultipleBagFetchException.
    // observaciones y sus docentes se cargan LAZY dentro de la transaccion del gateway al mapear.
    @EntityGraph(attributePaths = {"docente", "estado", "evaluaciones"})
    Optional<FormatoAEntity> findDetalleByIdFormatoA(Integer idFormatoA);

    @Query("SELECT new co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.dto.FormatoADetalleDTO(" +
            "f.idFormatoA, f.titulo, d.nombres, d.apellidos, es.estado, " +
            "e.idEvaluacion, e.concepto, o.idObservacion, o.descripcion, '', '') " +
            "FROM FormatoAEntity f " +
            "JOIN f.docente d " +
            "LEFT JOIN f.estado es " +
            "LEFT JOIN f.evaluaciones e " +
            "LEFT JOIN e.observaciones o " +
            "WHERE f.titulo = :titulo " +
            "ORDER BY e.idEvaluacion, o.idObservacion")
    List<FormatoADetalleDTO> findFormatoADetalladoPorTitulo(@Param("titulo") String titulo);

    @Query(value = "SELECT COUNT(*) > 0 FROM FormatosA WHERE titulo = :titulo", nativeQuery = true)
    Integer existsByTituloNative(@Param("titulo") String titulo);

    @Modifying
    @Query("UPDATE EstadoEntity e SET e.estado = :nuevoEstado WHERE e.formatoA.idFormatoA = :idFormatoA")
    int actualizarEstadoPorIdFormatoA(@Param("idFormatoA") Integer idFormatoA, @Param("nuevoEstado") String nuevoEstado);
}
