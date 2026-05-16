package co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormatoPPA extends FormatoA {

    private String nombreEstudiante;
    private String nombreAsesor;
    private String linkCartaAceptacion;
    private String codigoEstudiante;
}
