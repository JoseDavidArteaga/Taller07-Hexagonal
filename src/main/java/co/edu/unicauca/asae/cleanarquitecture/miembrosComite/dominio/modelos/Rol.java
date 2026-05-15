package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    private Integer idRol;
    private String rolAsignado;
    private List<Historico> historicos;
}
