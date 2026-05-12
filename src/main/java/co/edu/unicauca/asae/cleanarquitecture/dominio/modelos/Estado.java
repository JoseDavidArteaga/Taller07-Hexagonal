package co.edu.unicauca.asae.cleanarquitecture.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estado {
    private Integer idEstado;
    private String estado;
    private FormatoA formatoA;
}
