package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface DocenteRepositoryInt extends CrudRepository<DocenteEntity, Integer> {

    boolean existsByCorreo(String correo);

    List<DocenteEntity> findByNombres(String nombres);

    List<DocenteEntity> findAllByDepartamento(String departamento);

    long countByDepartamento(String departamento);

    void deleteByCorreo(String correo);

    List<DocenteEntity> findAllByNombresIgnoreCase(String nombres);
}
