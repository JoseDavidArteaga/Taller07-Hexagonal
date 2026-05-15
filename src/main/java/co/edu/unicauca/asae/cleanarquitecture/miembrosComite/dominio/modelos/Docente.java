package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos.Observacion;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Docente {
    private Integer idPersona;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombres;
    private String apellidos;
    private String correo;
    private String departamento;
    private List<FormatoA> formatosA;
    private List<Historico> historicos;
    private List<Observacion> observaciones;
}
