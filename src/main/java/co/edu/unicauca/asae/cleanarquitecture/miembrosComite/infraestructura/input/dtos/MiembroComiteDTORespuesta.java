package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MiembroComiteDTORespuesta {
    private Integer idPersona;
    private String nombres;
    private String apellidos;
    private String correo;
    private String departamento;
    private String rol;
    private java.util.Date fechaInicioRol;
    private java.util.Date fechaFinRol;
}
