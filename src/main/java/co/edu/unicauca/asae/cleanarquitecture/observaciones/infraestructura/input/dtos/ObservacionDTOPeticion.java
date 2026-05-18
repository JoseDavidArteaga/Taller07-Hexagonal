package co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.input.dtos;

import java.util.List;

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
public class ObservacionDTOPeticion {

    @NotNull(message = "{observacion.descripcion.empty}")
    @Size(min = 1, max = 500, message = "{observacion.descripcion.size}")
    private String descripcion;

    @NotNull(message = "{observacion.idFormatoA.empty}")
    private Integer idFormatoA;

    private List<Integer> idsDocentes;
}
