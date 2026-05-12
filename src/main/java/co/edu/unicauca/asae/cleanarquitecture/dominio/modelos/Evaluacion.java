package co.edu.unicauca.asae.cleanarquitecture.dominio.modelos;

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
