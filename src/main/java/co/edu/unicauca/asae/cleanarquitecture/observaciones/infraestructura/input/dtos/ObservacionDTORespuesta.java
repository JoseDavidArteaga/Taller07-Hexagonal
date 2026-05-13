package co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.input.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObservacionDTORespuesta {
    private Integer idObservacion;
    private String descripcion;
    private Date fechaRegistro;
}
