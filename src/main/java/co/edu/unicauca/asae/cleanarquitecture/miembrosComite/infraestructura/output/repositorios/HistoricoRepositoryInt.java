package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.HistoricoEntity;

public interface HistoricoRepositoryInt extends CrudRepository<HistoricoEntity, Integer> {

    List<HistoricoEntity> findByActivoTrue();
}
