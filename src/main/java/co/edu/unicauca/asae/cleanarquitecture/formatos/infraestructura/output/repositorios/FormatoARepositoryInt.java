package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.FormatoAEntity;


public interface FormatoARepositoryInt extends CrudRepository<FormatoAEntity, Integer> {

    boolean existsByTitulo(String titulo);

    List<FormatoAEntity> findByTituloContaining(String titulo);

    List<FormatoAEntity> findAllByFechaBetween(Date fechaInicio, Date fechaFin);

    long countByDocentes_IdPersona(Integer idPersona);

    @Modifying
    @Query("UPDATE EstadoEntity e SET e.estado = :nuevoEstado WHERE e.idEstado = :idFormatoA")
    int actualizarEstadoPorIdFormatoA(@Param("idFormatoA") Integer idFormatoA, @Param("nuevoEstado") String nuevoEstado);
}
