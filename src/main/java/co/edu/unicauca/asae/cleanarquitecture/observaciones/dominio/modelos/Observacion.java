package co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Observacion {
    private Integer idObservacion;
    private String descripcion;
    private Date fechaRegistro;
    private Evaluacion evaluacion;
    private List<Docente> docentes;
}
