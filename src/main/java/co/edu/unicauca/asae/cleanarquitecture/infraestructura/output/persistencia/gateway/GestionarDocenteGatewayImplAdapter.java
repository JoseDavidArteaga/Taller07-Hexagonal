package co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.gateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.cleanarquitecture.aplicacion.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.dominio.modelos.Docente;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.entidades.DocenteEntity;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.mappers.DocenteMapper;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.repositorios.DocenteRepositoryInt;

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
        return this.objDocenteRepository.existsByCorreo(correo);
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
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(this.docenteMapper::mapDeEntityADominio)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Docente> obtenerPorId(Integer id) {
        Optional<DocenteEntity> opt = this.objDocenteRepository.findById(id);
        return opt.map(this.docenteMapper::mapDeEntityADominio);
    }
}
