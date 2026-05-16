package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.EstadoEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.FormatoAEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios.FormatoARepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.DocenteEntity;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.repositorios.DocenteRepositoryInt;

@DataJpaTest
public class FormatoARepositoryTest {

    @Autowired
    private FormatoARepositoryInt formatoARepository;

    @Autowired
    private DocenteRepositoryInt docenteRepository;

    private DocenteEntity crearDocentePrueba(String correo) {
        DocenteEntity d = new DocenteEntity();
        d.setTipoIdentificacion("CC");
        d.setNumeroIdentificacion(correo);
        d.setNombres("Docente");
        d.setApellidos("Prueba");
        d.setCorreo(correo);
        d.setDepartamento("Sistemas");
        return docenteRepository.save(d);
    }

    @Test
    @Transactional
    void testGuardarFormatoAConEstado() {
        DocenteEntity docente = crearDocentePrueba("doc1@unicauca.edu.co");
        FormatoAEntity formato = new FormatoAEntity();
        formato.setTitulo("Formato de Prueba");
        formato.setFecha(new Date());
        formato.setObjetivos(List.of("Analizar el problema", "Diseñar la solucion", "Implementar el sistema"));
        formato.setDocente(docente);

        EstadoEntity estado = new EstadoEntity();
        estado.setEstado("En elaboracion");
        estado.setFormatoA(formato);
        formato.setEstado(estado);

        FormatoAEntity guardado = formatoARepository.save(formato);
        assertNotNull(guardado.getIdFormatoA());
        assertNotNull(guardado.getEstado());
        assertNotNull(guardado.getEstado().getIdEstado());
    }

    @Test
    void testExistsByTitulo() {
        DocenteEntity docente = crearDocentePrueba("doc2@unicauca.edu.co");
        FormatoAEntity formato = new FormatoAEntity();
        formato.setTitulo("Titulo Unico");
        formato.setFecha(new Date());
        formato.setObjetivos(List.of("Analizar el problema", "Diseñar la solucion", "Implementar el sistema"));
        formato.setDocente(docente);
        formatoARepository.save(formato);

        assertTrue(formatoARepository.existsByTitulo("Titulo Unico"));
        assertFalse(formatoARepository.existsByTitulo("No Existe"));
    }

    @Test
    @Transactional
    void testActualizarEstadoPorQuery() {
        DocenteEntity docente = crearDocentePrueba("doc3@unicauca.edu.co");
        FormatoAEntity formato = new FormatoAEntity();
        formato.setTitulo("Formato Estado");
        formato.setFecha(new Date());
        formato.setObjetivos(List.of("Analizar el problema", "Diseñar la solucion", "Implementar el sistema"));
        formato.setDocente(docente);

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
    void testGuardarFormatoAConDocente() {
        DocenteEntity d1 = new DocenteEntity();
        d1.setTipoIdentificacion("CC");
        d1.setNumeroIdentificacion("100");
        d1.setNombres("Docente 1");
        d1.setApellidos("Apellido 1");
        d1.setCorreo("d1@unicauca.edu.co");
        d1.setDepartamento("Sistemas");
        docenteRepository.save(d1);

        FormatoAEntity formato = new FormatoAEntity();
        formato.setTitulo("Formato con Docente");
        formato.setFecha(new Date());
        formato.setObjetivos(List.of("Analizar el problema", "Diseñar la solucion", "Implementar el sistema"));
        formato.setDocente(d1);

        FormatoAEntity guardado = formatoARepository.save(formato);
        assertNotNull(guardado.getIdFormatoA());
        assertNotNull(guardado.getDocente());
        assertEquals("Docente 1", guardado.getDocente().getNombres());
    }

    @Test
    void testFindByDocenteIdPersona() {
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
        formato.setObjetivos(List.of("Analizar el problema", "Diseñar la solucion", "Implementar el sistema"));
        formato.setDocente(docenteGuardado);
        formatoARepository.save(formato);

        List<FormatoAEntity> resultado = formatoARepository.findByDocente_IdPersona(docenteGuardado.getIdPersona());
        assertEquals(1, resultado.size());
    }
}