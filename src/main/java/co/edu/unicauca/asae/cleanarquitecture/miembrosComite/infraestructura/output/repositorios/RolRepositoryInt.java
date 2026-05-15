package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.repositorios;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.RolEntity;

public interface RolRepositoryInt extends CrudRepository<RolEntity, Integer> {

    Optional<RolEntity> findByRolAsignado(String rolAsignado);
}
