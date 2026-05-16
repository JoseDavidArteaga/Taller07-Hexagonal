package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.validaciones.VerboInfinitivo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormatoADTOPeticion {

    @NotNull(message = "{formatoA.titulo.empty}")
    @Size(min = 5, max = 200, message = "{formatoA.titulo.size}")
    private String titulo;

    @NotEmpty(message = "{formatoA.objetivos.empty}")
    @Size(min = 3, message = "{formatoA.objetivos.min}")
    private List<@VerboInfinitivo String> objetivos;

    @NotNull(message = "{formatoA.docente.empty}")
    @Valid
    private DocenteFormatoADTOPeticion docente;
}
