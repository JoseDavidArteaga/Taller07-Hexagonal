package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Estados")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstado")
    private Integer idEstado;

    @Column(nullable = false, length = 50)
    private String estado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfk_formato_a", nullable = false, unique = true)
    private FormatoAEntity formatoA;
}
