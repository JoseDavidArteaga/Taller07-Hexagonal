package co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos;

import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos.Observacion;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluacion {
    private Integer idEvaluacion;
    private String concepto;
    private Date fechaRegistro;
    private FormatoA formatoA;
    private List<Observacion> observaciones;
}
