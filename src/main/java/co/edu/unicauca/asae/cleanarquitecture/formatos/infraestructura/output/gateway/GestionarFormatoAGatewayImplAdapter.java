package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output.GestionarFormatoAGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.FormatoAEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.mappers.EvaluacionMapper;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.mappers.FormatoAMapper;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios.EvaluacionRepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios.FormatoARepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.DocenteEntity;

@Service
@Transactional
public class GestionarFormatoAGatewayImplAdapter implements GestionarFormatoAGatewayIntPort {

    private final FormatoARepositoryInt objFormatoARepository;
    private final EvaluacionRepositoryInt objEvaluacionRepository;
    private final FormatoAMapper formatoAMapper;
    private final EvaluacionMapper evaluacionMapper;

    public GestionarFormatoAGatewayImplAdapter(FormatoARepositoryInt objFormatoARepository,
                                               EvaluacionRepositoryInt objEvaluacionRepository,
                                               FormatoAMapper formatoAMapper,
                                               EvaluacionMapper evaluacionMapper) {
        this.objFormatoARepository = objFormatoARepository;
        this.objEvaluacionRepository = objEvaluacionRepository;
        this.formatoAMapper = formatoAMapper;
        this.evaluacionMapper = evaluacionMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeFormatoAPorTitulo(String titulo) {
        return this.objFormatoARepository.existsByTitulo(titulo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Integer idFormatoA) {
        return this.objFormatoARepository.existsById(idFormatoA);
    }

    @Override
    public FormatoA guardar(FormatoA formatoA) {
        FormatoAEntity entity = this.formatoAMapper.mapDeDominioAEntity(formatoA);
        FormatoAEntity guardado = this.objFormatoARepository.save(entity);
        return this.formatoAMapper.mapDeEntityADominio(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormatoA> listar() {
        Iterable<FormatoAEntity> iterable = this.objFormatoARepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(this.formatoAMapper::mapDeEntityADominio)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormatoA> obtenerPorId(Integer id) {
        Optional<FormatoAEntity> opt = this.objFormatoARepository.findById(id);
        return opt.map(this.formatoAMapper::mapDeEntityADominio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormatoA> listarPorDocente(Integer idDocente) {
        Iterable<FormatoAEntity> iterable = this.objFormatoARepository.findAll();
        List<FormatoA> resultado = new ArrayList<>();
        for (FormatoAEntity entity : iterable) {
            if (entity.getDocentes() == null) continue;
            for (DocenteEntity d : entity.getDocentes()) {
                if (d.getIdPersona() != null && d.getIdPersona().equals(idDocente)) {
                    resultado.add(this.formatoAMapper.mapDeEntityADominio(entity));
                    break;
                }
            }
        }
        return resultado;
    }

    @Override
    public int actualizarEstado(Integer idFormatoA, String estado) {
        return this.objFormatoARepository.actualizarEstadoPorIdFormatoA(idFormatoA, estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evaluacion> obtenerHistoricoEvaluaciones(Integer idFormatoA) {
        return this.evaluacionMapper.mapDeEntityADominio(
                this.objEvaluacionRepository.findHistoricoEvaluacionesByIdFormatoA(idFormatoA));
    }
}
