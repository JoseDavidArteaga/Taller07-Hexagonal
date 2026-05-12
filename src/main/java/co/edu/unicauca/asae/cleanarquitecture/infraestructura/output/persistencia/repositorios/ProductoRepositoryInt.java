package co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.entidades.ProductoEntity;

public interface ProductoRepositoryInt extends CrudRepository<ProductoEntity, Integer> {

    @Query("SELECT count(*) FROM ProductoEntity p  WHERE p.codigo=?1")
    Integer existeProductoPorCodigo(String codigo);
}