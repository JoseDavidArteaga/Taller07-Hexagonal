package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos;

import javax.validation.constraints.Email;
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
public class DocenteFormatoADTOPeticion {

    private Integer idPersona;

    @NotNull(message = "{formatoA.docente.tipoIdentificacion.empty}")
    @NotEmpty(message = "{formatoA.docente.tipoIdentificacion.empty}")
    @Size(max = 20, message = "{formatoA.docente.tipoIdentificacion.size}")
    private String tipoIdentificacion;

    @NotNull(message = "{formatoA.docente.numeroIdentificacion.empty}")
    @NotEmpty(message = "{formatoA.docente.numeroIdentificacion.empty}")
    @Size(max = 20, message = "{formatoA.docente.numeroIdentificacion.size}")
    private String numeroIdentificacion;

    @NotNull(message = "{formatoA.docente.nombres.empty}")
    @NotEmpty(message = "{formatoA.docente.nombres.empty}")
    @Size(max = 100, message = "{formatoA.docente.nombres.size}")
    private String nombres;

    @NotNull(message = "{formatoA.docente.apellidos.empty}")
    @NotEmpty(message = "{formatoA.docente.apellidos.empty}")
    @Size(max = 100, message = "{formatoA.docente.apellidos.size}")
    private String apellidos;

    @NotNull(message = "{formatoA.docente.correo.empty}")
    @NotEmpty(message = "{formatoA.docente.correo.empty}")
    @Email(message = "{formatoA.docente.correo.email}")
    @Size(max = 150, message = "{formatoA.docente.correo.size}")
    private String correo;

    @NotNull(message = "{formatoA.docente.departamento.empty}")
    @NotEmpty(message = "{formatoA.docente.departamento.empty}")
    @Size(max = 100, message = "{formatoA.docente.departamento.size}")
    private String departamento;
}
