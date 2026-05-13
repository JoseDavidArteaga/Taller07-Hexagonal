package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.GestionarEvaluacionGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;

@Service
@Transactional
public class GestionarEvaluacionGatewayImplAdapter implements GestionarEvaluacionGatewayIntPort {

    private final EvaluacionRepositoryInt objEvaluacionRepository;
    private final EvaluacionMapper evaluacionMapper;

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
}
