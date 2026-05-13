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
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.EvaluacionEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.FormatoAEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios.EvaluacionRepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios.FormatoARepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.ObservacionEntity;

@DataJpaTest
public class EvaluacionRepositoryTest {

    @Autowired
    private EvaluacionRepositoryInt evaluacionRepository;

    @Autowired
    private FormatoARepositoryInt formatoARepository;

    @Test
    @Transactional
    void testGuardarEvaluacionConObservaciones() {
        FormatoAEntity formato = crearFormato("Formato Evaluación");
        FormatoAEntity guardadoFormato = formatoARepository.save(formato);

        EvaluacionEntity evaluacion = new EvaluacionEntity();
        evaluacion.setConcepto("Aprobado");
        evaluacion.setFechaRegistro(new Date());
        evaluacion.setFormatoA(guardadoFormato);

        ObservacionEntity obs = new ObservacionEntity();
        obs.setDescripcion("Buen trabajo");
        obs.setFechaRegistro(new Date());
        obs.setEvaluacion(evaluacion);

        List<ObservacionEntity> observaciones = new ArrayList<>();
        observaciones.add(obs);
        evaluacion.setObservaciones(observaciones);

        EvaluacionEntity guardado = evaluacionRepository.save(evaluacion);
        assertNotNull(guardado.getIdEvaluacion());
        assertNotNull(guardado.getObservaciones());
        assertEquals(1, guardado.getObservaciones().size());
    }

    @Test
    @Transactional
    void testFindHistoricoEvaluacionesByIdFormatoA() {
        FormatoAEntity formato = crearFormato("Formato Histórico");
        FormatoAEntity guardadoFormato = formatoARepository.save(formato);

        EvaluacionEntity e1 = new EvaluacionEntity();
        e1.setConcepto("Primera evaluación");
        e1.setFechaRegistro(new Date());
        e1.setFormatoA(guardadoFormato);
        evaluacionRepository.save(e1);

        EvaluacionEntity e2 = new EvaluacionEntity();
        e2.setConcepto("Segunda evaluación");
        e2.setFechaRegistro(new Date());
        e2.setFormatoA(guardadoFormato);
        evaluacionRepository.save(e2);

        List<EvaluacionEntity> historico = evaluacionRepository.findHistoricoEvaluacionesByIdFormatoA(guardadoFormato.getIdFormatoA());
        assertEquals(2, historico.size());
    }

    @Test
    @Transactional
    void testCountByFormatoAIdFormatoA() {
        FormatoAEntity formato = crearFormato("Formato Count Eval");
        FormatoAEntity guardadoFormato = formatoARepository.save(formato);

        EvaluacionEntity e1 = new EvaluacionEntity();
        e1.setConcepto("Eval 1");
        e1.setFechaRegistro(new Date());
        e1.setFormatoA(guardadoFormato);
        evaluacionRepository.save(e1);

        long count = evaluacionRepository.countByFormatoA_IdFormatoA(guardadoFormato.getIdFormatoA());
        assertEquals(1, count);
    }

    @Test
    @Transactional
    void testDeleteByFormatoAIdFormatoA() {
        FormatoAEntity formato = crearFormato("Formato Delete Eval");
        FormatoAEntity guardadoFormato = formatoARepository.save(formato);

        EvaluacionEntity e1 = new EvaluacionEntity();
        e1.setConcepto("Eval para borrar");
        e1.setFechaRegistro(new Date());
        e1.setFormatoA(guardadoFormato);
        evaluacionRepository.save(e1);

        evaluacionRepository.deleteByFormatoA_IdFormatoA(guardadoFormato.getIdFormatoA());
        long count = evaluacionRepository.countByFormatoA_IdFormatoA(guardadoFormato.getIdFormatoA());
        assertEquals(0, count);
    }

    private FormatoAEntity crearFormato(String titulo) {
        FormatoAEntity formato = new FormatoAEntity();
        formato.setTitulo(titulo);
        formato.setFecha(new Date());
        formato.setObjetivo("Objetivo");
        EstadoEntity estado = new EstadoEntity();
        estado.setEstado("En elaboración");
        estado.setFormatoA(formato);
        formato.setEstado(estado);
        return formato;
    }
}
