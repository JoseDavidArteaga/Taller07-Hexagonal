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
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.dto.FormatoADetalleDTO;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.FormatoAEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.mappers.EvaluacionMapper;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.mappers.FormatoAMapper;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios.EvaluacionRepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios.FormatoARepositoryInt;

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
    public Optional<FormatoA> obtenerPorTitulo(String titulo) {
        Optional<FormatoAEntity> opt = this.objFormatoARepository.findByTitulo(titulo);
        return opt.map(this.formatoAMapper::mapDeEntityADominio);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormatoA> obtenerDetallePorId(Integer id) {
        Optional<FormatoAEntity> opt = this.objFormatoARepository.findDetalleByIdFormatoA(id);
        return opt.map(this.formatoAMapper::mapDeEntityADominio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormatoA> listarPorDocente(Integer idDocente) {
        List<FormatoAEntity> entities = this.objFormatoARepository.findByDocente_IdPersona(idDocente);
        return entities.stream()
                .map(this.formatoAMapper::mapDeEntityADominio)
                .collect(Collectors.toList());
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

    @Override
    @Transactional(readOnly = true)
    public List<FormatoADetalleDTO> obtenerDetallePorTitulo(String titulo) {
        return this.objFormatoARepository.findFormatoADetalladoPorTitulo(titulo);
    }
}
