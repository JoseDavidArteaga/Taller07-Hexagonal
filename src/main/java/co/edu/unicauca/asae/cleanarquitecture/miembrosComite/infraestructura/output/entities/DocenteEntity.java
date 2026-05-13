package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.FormatoAEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Docentes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocenteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPersona")
    private Integer idPersona;

    @Column(nullable = false, length = 20)
    private String tipoIdentificacion;

    @Column(nullable = false, length = 20, unique = true)
    private String numeroIdentificacion;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, length = 150, unique = true)
    private String correo;

    @Column(length = 100)
    private String departamento;

    @ManyToMany(mappedBy = "docentes", fetch = FetchType.LAZY)
    private List<FormatoAEntity> formatosA;
}
