package co.edu.unicauca.asae.cleanarquitecture.dominio.modelos;

import java.util.Date;

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
}
