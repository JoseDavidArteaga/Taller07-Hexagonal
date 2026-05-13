package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos;

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
public class ActualizarEstadoFormatoADTOPeticion {

    @NotNull(message = "{formatoA.estado.empty}")
    @Size(min = 1, max = 50, message = "{formatoA.estado.size}")
    private String estado;
}
