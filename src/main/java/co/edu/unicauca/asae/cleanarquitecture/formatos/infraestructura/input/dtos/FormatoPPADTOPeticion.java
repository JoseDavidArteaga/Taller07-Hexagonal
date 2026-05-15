package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormatoPPADTOPeticion extends FormatoADTOPeticion {

    @Size(max = 100, message = "{formatoA.nombreEstudiante.size}")
    private String nombreEstudiante;

    @Size(max = 100, message = "{formatoA.nombreAsesor.size}")
    private String nombreAsesor;

    @Size(max = 255, message = "{formatoA.linkCartaAceptacion.size}")
    private String linkCartaAceptacion;
}
