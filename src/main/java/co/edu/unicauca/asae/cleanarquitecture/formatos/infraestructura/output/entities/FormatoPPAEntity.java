package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FormatosPPA")
@PrimaryKeyJoinColumn(name = "idFormatoA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormatoPPAEntity extends FormatoAEntity {

    @Column(length = 100)
    private String nombreEstudiante;

    @Column(length = 100)
    private String nombreAsesor;

    @Column(length = 255)
    private String linkCartaAceptacion;
}
