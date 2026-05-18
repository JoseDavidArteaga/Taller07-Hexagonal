package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.gateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.DocenteEntity;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.mappers.DocenteMapper;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.repositorios.DocenteRepositoryInt;

@Service
@Transactional
public class GestionarDocenteGatewayImplAdapter implements GestionarDocenteGatewayIntPort {

    private final DocenteRepositoryInt objDocenteRepository;
    private final DocenteMapper docenteMapper;

    public GestionarDocenteGatewayImplAdapter(DocenteRepositoryInt objDocenteRepository, DocenteMapper docenteMapper) {
        this.objDocenteRepository = objDocenteRepository;
        this.docenteMapper = docenteMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeDocentePorCorreo(String correo) {
        Integer resultado = this.objDocenteRepository.existsByCorreoNative(correo);
        return resultado != null && resultado > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeDocentePorId(Integer idDocente) {
        return this.objDocenteRepository.existsById(idDocente);
    }

    @Override
    public Docente guardar(Docente docente) {
        DocenteEntity entity = this.docenteMapper.mapDeDominioAEntity(docente);
        DocenteEntity guardado = this.objDocenteRepository.save(entity);
        return this.docenteMapper.mapDeEntityADominio(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Docente> listar() {
        Iterable<DocenteEntity> iterable = this.objDocenteRepository.findAll();
        List<DocenteEntity> entities = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        // Forzar carga lazy de historicos y roles para listar miembros del comite
        entities.forEach(e -> {
            if (e.getHistoricos() != null) {
                e.getHistoricos().forEach(h -> {
                    if (h.getRol() != null) {
                        h.getRol().getRolAsignado();
                    }
                });
            }
        });
        return entities.stream()
                .map(this.docenteMapper::mapDeEntityADominio)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Docente> listarPorNombres(String nombres) {
        return this.docenteMapper.mapDeEntityADominio(
                this.objDocenteRepository.findAllByNombresIgnoreCase(nombres));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Docente> obtenerPorId(Integer id) {
        Optional<DocenteEntity> opt = this.objDocenteRepository.findById(id);
        return opt.map(this.docenteMapper::mapDeEntityADominio);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Docente> obtenerPorCorreo(String correo) {
        Optional<DocenteEntity> opt = this.objDocenteRepository.findByCorreo(correo);
        return opt.map(this.docenteMapper::mapDeEntityADominio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Docente> listarPorGrupoYPatron(String grupo, String patron) {
        return this.docenteMapper.mapDeEntityADominio(
                this.objDocenteRepository.findByDepartamentoIgnoreCaseAndApellidosStartingWithIgnoreCaseOrderByApellidosAsc(grupo, patron));
    }
}
