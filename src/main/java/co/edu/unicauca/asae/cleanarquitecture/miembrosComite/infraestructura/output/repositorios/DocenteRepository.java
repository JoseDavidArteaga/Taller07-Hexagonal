package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.DocenteEntity;

public interface DocenteRepository extends CrudRepository<DocenteEntity, Integer> {

    List<DocenteEntity> findByDepartamentoIgnoreCaseAndApellidosStartingWithIgnoreCaseOrderByApellidosAsc(
            String nombreGrupo,
            String patronBusqueda);

    @Query(value = "SELECT COUNT(1) > 0 FROM Docentes d WHERE d.correo = :correo", nativeQuery = true)
    Integer existeConCorreo(@Param("correo") String correo);
}
