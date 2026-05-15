package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.gateway;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.GestionarEvaluacionGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.EvaluacionEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.FormatoAEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.mappers.EvaluacionMapper;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios.EvaluacionRepositoryInt;

@Service
@Transactional
public class GestionarEvaluacionGatewayImplAdapter implements GestionarEvaluacionGatewayIntPort {

    private static final String CONCEPTO_INICIAL = "Por establecer";

    private final EvaluacionRepositoryInt objEvaluacionRepository;
    private final EvaluacionMapper evaluacionMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public GestionarEvaluacionGatewayImplAdapter(EvaluacionRepositoryInt objEvaluacionRepository,
                                                 EvaluacionMapper evaluacionMapper) {
        this.objEvaluacionRepository = objEvaluacionRepository;
        this.evaluacionMapper = evaluacionMapper;
    }

    @Override
    public Evaluacion guardar(Evaluacion evaluacion) {
        EvaluacionEntity entity = this.evaluacionMapper.mapDeDominioAEntity(evaluacion);
        EvaluacionEntity guardado = this.objEvaluacionRepository.save(entity);
        return this.evaluacionMapper.mapDeEntityADominio(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evaluacion> listar() {
        Iterable<EvaluacionEntity> iterable = this.objEvaluacionRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(this.evaluacionMapper::mapDeEntityADominio)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Evaluacion> obtenerPorId(Integer id) {
        Optional<EvaluacionEntity> opt = this.objEvaluacionRepository.findById(id);
        return opt.map(this.evaluacionMapper::mapDeEntityADominio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evaluacion> obtenerHistoricoPorFormatoA(Integer idFormatoA) {
        return this.evaluacionMapper.mapDeEntityADominio(
                this.objEvaluacionRepository.findHistoricoEvaluacionesByIdFormatoA(idFormatoA));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Evaluacion> obtenerUltimaPorFormatoA(Integer idFormatoA) {
        Optional<EvaluacionEntity> opt = this.objEvaluacionRepository
                .findFirstByFormatoA_IdFormatoAOrderByFechaRegistroDescIdEvaluacionDesc(idFormatoA);
        return opt.map(this.evaluacionMapper::mapDeEntityADominio);
    }

    @Override
    public Evaluacion crearEvaluacionInicialParaFormatoA(Integer idFormatoA) {
        FormatoAEntity referenciaFormatoA = this.entityManager.getReference(FormatoAEntity.class, idFormatoA);
        EvaluacionEntity nueva = new EvaluacionEntity();
        nueva.setConcepto(CONCEPTO_INICIAL);
        nueva.setFechaRegistro(new Date());
        nueva.setFormatoA(referenciaFormatoA);
        EvaluacionEntity guardada = this.objEvaluacionRepository.save(nueva);
        return this.evaluacionMapper.mapDeEntityADominio(guardada);
    }
}
