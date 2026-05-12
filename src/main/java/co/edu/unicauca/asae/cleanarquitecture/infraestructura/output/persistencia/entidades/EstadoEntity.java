package co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
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
    @Column(name = "idEstado")
    private Integer idEstado;

    @Column(nullable = false, length = 50)
    private String estado;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "idEstado")
    private FormatoAEntity formatoA;
}
