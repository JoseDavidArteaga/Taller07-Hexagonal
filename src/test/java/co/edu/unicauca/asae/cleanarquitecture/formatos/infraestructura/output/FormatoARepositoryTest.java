package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.DocenteEntity;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.DocenteRepositoryInt;

@DataJpaTest
public class FormatoARepositoryTest {

    @Autowired
    private FormatoARepositoryInt formatoARepository;

    @Autowired
    private DocenteRepositoryInt docenteRepository;

    @Test
    @Transactional
    void testGuardarFormatoAConEstadoMapsId() {
        FormatoAEntity formato = new FormatoAEntity();
        formato.setTitulo("Formato de Prueba");
        formato.setFecha(new Date());
        formato.setObjetivo("Objetivo de prueba");

        EstadoEntity estado = new EstadoEntity();
        estado.setEstado("En elaboracion");
        estado.setFormatoA(formato);
        formato.setEstado(estado);

        FormatoAEntity guardado = formatoARepository.save(formato);
        assertNotNull(guardado.getIdFormatoA());
        assertNotNull(guardado.getEstado());
        assertEquals(guardado.getIdFormatoA(), guardado.getEstado().getIdEstado());
    }

    @Test
    void testExistsByTitulo() {
        FormatoAEntity formato = new FormatoAEntity();
        formato.setTitulo("Titulo Unico");
        formato.setFecha(new Date());
        formato.setObjetivo("Objetivo");
        formatoARepository.save(formato);

        assertTrue(formatoARepository.existsByTitulo("Titulo Unico"));
        assertFalse(formatoARepository.existsByTitulo("No Existe"));
    }

    @Test
    @Transactional
    void testActualizarEstadoPorQuery() {
        FormatoAEntity formato = new FormatoAEntity();
        formato.setTitulo("Formato Estado");
        formato.setFecha(new Date());
        formato.setObjetivo("Objetivo");

        EstadoEntity estado = new EstadoEntity();
        estado.setEstado("En elaboracion");
        estado.setFormatoA(formato);
        formato.setEstado(estado);

        FormatoAEntity guardado = formatoARepository.save(formato);
        Integer id = guardado.getIdFormatoA();

        int filas = formatoARepository.actualizarEstadoPorIdFormatoA(id, "Aprobado");
        assertEquals(1, filas);
    }

    @Test
    @Transactional
    void testGuardarFormatoAConDocentesManyToMany() {
        DocenteEntity d1 = new DocenteEntity();
        d1.setTipoIdentificacion("CC");
        d1.setNumeroIdentificacion("100");
        d1.setNombres("Docente 1");
        d1.setApellidos("Apellido 1");
        d1.setCorreo("d1@unicauca.edu.co");
        d1.setDepartamento("Sistemas");
        docenteRepository.save(d1);

        FormatoAEntity formato = new FormatoAEntity();
        formato.setTitulo("Formato con Docentes");
        formato.setFecha(new Date());
        formato.setObjetivo("Objetivo");
        List<DocenteEntity> docentes = new ArrayList<>();
        docentes.add(d1);
        formato.setDocentes(docentes);

        FormatoAEntity guardado = formatoARepository.save(formato);
        assertNotNull(guardado.getIdFormatoA());
        assertNotNull(guardado.getDocentes());
        assertEquals(1, guardado.getDocentes().size());
    }

    @Test
    void testCountByDocentesIdPersona() {
        DocenteEntity d1 = new DocenteEntity();
        d1.setTipoIdentificacion("CC");
        d1.setNumeroIdentificacion("200");
        d1.setNombres("Docente 2");
        d1.setApellidos("Apellido 2");
        d1.setCorreo("d2@unicauca.edu.co");
        d1.setDepartamento("Sistemas");
        DocenteEntity docenteGuardado = docenteRepository.save(d1);

        FormatoAEntity formato = new FormatoAEntity();
        formato.setTitulo("Formato Count");
        formato.setFecha(new Date());
        formato.setObjetivo("Objetivo");
        List<DocenteEntity> docentes = new ArrayList<>();
        docentes.add(docenteGuardado);
        formato.setDocentes(docentes);
        formatoARepository.save(formato);

        long count = formatoARepository.countByDocentes_IdPersona(docenteGuardado.getIdPersona());
        assertEquals(1, count);
    }
}