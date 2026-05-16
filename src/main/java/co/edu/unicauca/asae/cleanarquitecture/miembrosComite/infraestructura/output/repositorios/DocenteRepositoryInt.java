package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.DocenteEntity;


public interface DocenteRepositoryInt extends CrudRepository<DocenteEntity, Integer> {

    boolean existsByCorreo(String correo);

    Optional<DocenteEntity> findByCorreo(String correo);

    List<DocenteEntity> findByNombres(String nombres);

    List<DocenteEntity> findAllByDepartamento(String departamento);

    long countByDepartamento(String departamento);

    void deleteByCorreo(String correo);

    List<DocenteEntity> findAllByNombresIgnoreCase(String nombres);

    List<DocenteEntity> findByDepartamentoIgnoreCaseAndApellidosStartingWithIgnoreCaseOrderByApellidosAsc(
            String nombreGrupo, String patronBusqueda);

    @EntityGraph(attributePaths = {"formatosA"})
    Optional<DocenteEntity> findConFormatosByIdPersona(Integer idPersona);
}
