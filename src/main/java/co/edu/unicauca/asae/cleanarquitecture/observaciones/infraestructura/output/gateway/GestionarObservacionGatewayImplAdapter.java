package co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.gateway;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.EvaluacionEntity;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.DocenteEntity;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.repositorios.DocenteRepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.aplicacion.output.GestionarObservacionGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos.Observacion;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.ObservacionEntity;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.ObservacionRepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.mappers.ObservacionMapper;

@Service
@Transactional
public class GestionarObservacionGatewayImplAdapter implements GestionarObservacionGatewayIntPort {

    private final ObservacionRepositoryInt objObservacionRepository;
    private final DocenteRepositoryInt objDocenteRepository;
    private final ObservacionMapper observacionMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public GestionarObservacionGatewayImplAdapter(ObservacionRepositoryInt objObservacionRepository,
                                                  DocenteRepositoryInt objDocenteRepository,
                                                  ObservacionMapper observacionMapper) {
        this.objObservacionRepository = objObservacionRepository;
        this.objDocenteRepository = objDocenteRepository;
        this.observacionMapper = observacionMapper;
    }

    @Override
    public Observacion registrarObservacion(String descripcion, Integer idEvaluacion, List<Integer> idsDocentes) {
        EvaluacionEntity refEvaluacion = this.entityManager.getReference(EvaluacionEntity.class, idEvaluacion);

        ObservacionEntity entity = new ObservacionEntity();
        entity.setDescripcion(descripcion);
        entity.setFechaRegistro(new Date());
        entity.setEvaluacion(refEvaluacion);

        ObservacionEntity guardada = this.objObservacionRepository.save(entity);

        List<DocenteEntity> docentesAutores = new ArrayList<>();
        for (Integer idDocente : idsDocentes.stream().distinct().toList()) {
            DocenteEntity refDocente = this.entityManager.getReference(DocenteEntity.class, idDocente);
            if (refDocente.getObservaciones() == null) {
                refDocente.setObservaciones(new ArrayList<>());
            }
            refDocente.getObservaciones().add(guardada);
            docentesAutores.add(refDocente);
        }
        this.objDocenteRepository.saveAll(docentesAutores);

        guardada.setDocentes(docentesAutores);
        return this.observacionMapper.mapDeEntityADominio(guardada);
    }
}
