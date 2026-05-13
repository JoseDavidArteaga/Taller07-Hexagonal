package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @Size(max = 500, message = "{formatoA.objetivo.size}")
    private String objetivo;

    @NotEmpty(message = "{formatoA.docentes.empty}")
    @Valid
    private List<DocenteFormatoADTOPeticion> docentes;
}
