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
public class FormatoADTORespuesta {
    private Integer idFormatoA;
    private String titulo;
    private Date fecha;
    private List<String> objetivos;
    private EstadoDTORespuesta estado;
    private List<EvaluacionDTORespuesta> evaluaciones;
    private DocenteDTORespuesta docente;
}
