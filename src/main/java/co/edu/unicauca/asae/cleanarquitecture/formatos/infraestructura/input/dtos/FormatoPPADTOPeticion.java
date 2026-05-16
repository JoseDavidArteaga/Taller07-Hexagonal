package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @NotNull(message = "{formatoA.nombreEstudiante.empty}")
    @Size(max = 100, message = "{formatoA.nombreEstudiante.size}")
    private String nombreEstudiante;

    @NotNull(message = "{formatoA.nombreAsesor.empty}")
    @Size(max = 100, message = "{formatoA.nombreAsesor.size}")
    private String nombreAsesor;

    @Size(max = 255, message = "{formatoA.linkCartaAceptacion.size}")
    private String linkCartaAceptacion;

    @NotNull(message = "{formatoA.codigoEstudiante.empty}")
    @Pattern(regexp = "^\\d{2,4}IS\\d{3}$", message = "{formatoA.codigoEstudiante.pattern}")
    private String codigoEstudiante;
}
