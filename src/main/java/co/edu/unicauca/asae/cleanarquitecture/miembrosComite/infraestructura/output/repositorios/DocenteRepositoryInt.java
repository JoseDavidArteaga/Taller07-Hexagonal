package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.DocenteEntity;


public interface DocenteRepositoryInt extends CrudRepository<DocenteEntity, Integer> {

    boolean existsByCorreo(String correo);

    List<DocenteEntity> findByNombres(String nombres);

    List<DocenteEntity> findAllByDepartamento(String departamento);

    long countByDepartamento(String departamento);

    void deleteByCorreo(String correo);

    List<DocenteEntity> findAllByNombresIgnoreCase(String nombres);
}
