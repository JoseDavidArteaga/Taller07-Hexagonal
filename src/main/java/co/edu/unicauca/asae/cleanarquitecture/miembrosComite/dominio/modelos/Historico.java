package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Historico {
    private Integer idHistorico;
    private Docente docente;
    private Rol rol;
    private Boolean activo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
