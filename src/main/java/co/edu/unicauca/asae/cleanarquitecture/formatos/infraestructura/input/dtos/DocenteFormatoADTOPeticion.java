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
public class DocenteFormatoADTOPeticion {

    private Integer idPersona;

    @Size(max = 20, message = "{formatoA.docente.tipoIdentificacion.size}")
    private String tipoIdentificacion;

    @Size(max = 20, message = "{formatoA.docente.numeroIdentificacion.size}")
    private String numeroIdentificacion;

    @Size(max = 100, message = "{formatoA.docente.nombres.size}")
    private String nombres;

    @Size(max = 100, message = "{formatoA.docente.apellidos.size}")
    private String apellidos;

    @Size(max = 150, message = "{formatoA.docente.correo.size}")
    private String correo;

    @Size(max = 100, message = "{formatoA.docente.departamento.size}")
    private String departamento;
}
