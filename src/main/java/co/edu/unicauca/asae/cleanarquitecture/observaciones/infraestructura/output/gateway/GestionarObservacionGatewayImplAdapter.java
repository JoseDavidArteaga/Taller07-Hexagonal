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
import co.edu.unicauca.asae.cleanarquitecture.observaciones.aplicacion.output.GestionarObservacionGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos.Observacion;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.ObservacionEntity;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.ObservacionRepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.mappers.ObservacionMapper;

@Service
@Transactional
public class GestionarObservacionGatewayImplAdapter implements GestionarObservacionGatewayIntPort {

    private final ObservacionRepositoryInt objObservacionRepository;
    private final ObservacionMapper observacionMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public GestionarObservacionGatewayImplAdapter(ObservacionRepositoryInt objObservacionRepository,
                                                  ObservacionMapper observacionMapper) {
        this.objObservacionRepository = objObservacionRepository;
        this.observacionMapper = observacionMapper;
    }

    @Override
    public Observacion registrarObservacion(String descripcion, Integer idEvaluacion, List<Integer> idsDocentes) {
        EvaluacionEntity refEvaluacion = this.entityManager.getReference(EvaluacionEntity.class, idEvaluacion);

        List<DocenteEntity> referenciasDocentes = new ArrayList<>();
        for (Integer idDocente : idsDocentes) {
            DocenteEntity refDocente = this.entityManager.getReference(DocenteEntity.class, idDocente);
            referenciasDocentes.add(refDocente);
        }

        ObservacionEntity entity = new ObservacionEntity();
        entity.setDescripcion(descripcion);
        entity.setFechaRegistro(new Date());
        entity.setEvaluacion(refEvaluacion);

        ObservacionEntity guardada = this.objObservacionRepository.save(entity);
        return this.observacionMapper.mapDeEntityADominio(guardada);
    }
}
