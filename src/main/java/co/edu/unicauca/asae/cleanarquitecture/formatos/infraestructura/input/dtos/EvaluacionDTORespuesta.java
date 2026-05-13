package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionDTORespuesta {
    private Integer idEvaluacion;
    private String concepto;
    private Date fechaRegistro;
    private List<ObservacionDTORespuesta> observaciones;
}
