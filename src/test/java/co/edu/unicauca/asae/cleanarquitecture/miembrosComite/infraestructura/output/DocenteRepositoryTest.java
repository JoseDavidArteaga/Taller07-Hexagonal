package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.DocenteEntity;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.repositorios.DocenteRepositoryInt;


@DataJpaTest
public class DocenteRepositoryTest {

    @Autowired
    private DocenteRepositoryInt repository;

    @Test
    void testGuardarYListarDocentes() {
        DocenteEntity d = new DocenteEntity();
        d.setTipoIdentificacion("CC");
        d.setNumeroIdentificacion("12345");
        d.setNombres("Juan");
        d.setApellidos("Pérez");
        d.setCorreo("juan.perez@unicauca.edu.co");
        d.setDepartamento("Sistemas");

        DocenteEntity guardado = repository.save(d);
        assertNotNull(guardado.getIdPersona());

        List<DocenteEntity> docentes = repository.findByNombres("Juan");
        assertFalse(docentes.isEmpty());
    }

    @Test
    void testExistsByCorreo() {
        DocenteEntity d = new DocenteEntity();
        d.setTipoIdentificacion("CC");
        d.setNumeroIdentificacion("67890");
        d.setNombres("Ana");
        d.setApellidos("Gómez");
        d.setCorreo("ana.gomez@unicauca.edu.co");
        d.setDepartamento("Electrónica");
        repository.save(d);

        assertTrue(repository.existsByCorreo("ana.gomez@unicauca.edu.co"));
        assertFalse(repository.existsByCorreo("noexiste@unicauca.edu.co"));
    }

    @Test
    void testFindAllByDepartamento() {
        DocenteEntity d = new DocenteEntity();
        d.setTipoIdentificacion("CC");
        d.setNumeroIdentificacion("11111");
        d.setNombres("Luis");
        d.setApellidos("Ruiz");
        d.setCorreo("luis.ruiz@unicauca.edu.co");
        d.setDepartamento("Matemáticas");
        repository.save(d);

        List<DocenteEntity> resultado = repository.findAllByDepartamento("Matemáticas");
        assertEquals(1, resultado.size());
    }

    @Test
    void testCountByDepartamento() {
        DocenteEntity d1 = new DocenteEntity();
        d1.setTipoIdentificacion("CC");
        d1.setNumeroIdentificacion("22222");
        d1.setNombres("Carlos");
        d1.setApellidos("López");
        d1.setCorreo("carlos@unicauca.edu.co");
        d1.setDepartamento("Física");
        repository.save(d1);

        assertEquals(1, repository.countByDepartamento("Física"));
    }

    @Test
    void testDeleteByCorreo() {
        DocenteEntity d = new DocenteEntity();
        d.setTipoIdentificacion("CC");
        d.setNumeroIdentificacion("33333");
        d.setNombres("María");
        d.setApellidos("Torres");
        d.setCorreo("maria@unicauca.edu.co");
        d.setDepartamento("Química");
        repository.save(d);

        repository.deleteByCorreo("maria@unicauca.edu.co");
        assertFalse(repository.existsByCorreo("maria@unicauca.edu.co"));
    }

    @Test
    void testFindAllByNombresIgnoreCase() {
        DocenteEntity d = new DocenteEntity();
        d.setTipoIdentificacion("CC");
        d.setNumeroIdentificacion("44444");
        d.setNombres("Pedro");
        d.setApellidos("Mesa");
        d.setCorreo("pedro@unicauca.edu.co");
        d.setDepartamento("Sistemas");
        repository.save(d);

        List<DocenteEntity> resultado = repository.findAllByNombresIgnoreCase("pedro");
        assertEquals(1, resultado.size());
    }
}
