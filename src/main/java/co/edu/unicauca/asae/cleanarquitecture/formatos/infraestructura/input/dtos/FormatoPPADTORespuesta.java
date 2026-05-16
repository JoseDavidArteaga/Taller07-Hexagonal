package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormatoPPADTORespuesta extends FormatoADTORespuesta {

    private String nombreEstudiante;
    private String nombreAsesor;
    private String linkCartaAceptacion;
    private String codigoEstudiante;
}
