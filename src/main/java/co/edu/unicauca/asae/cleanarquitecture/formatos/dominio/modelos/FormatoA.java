package co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormatoA {
    private Integer idFormatoA;
    private String titulo;
    private Date fecha;
    private String objetivo;
    private Estado estado;
    private List<Evaluacion> evaluaciones;
    private List<Docente> docentes;
}
