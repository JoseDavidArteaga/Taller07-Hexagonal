package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities;

import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.ObservacionEntity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Evaluaciones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvaluacion")
    private Integer idEvaluacion;

    @Column(nullable = false, length = 500)
    private String concepto;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFormatoA")
    private FormatoAEntity formatoA;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ObservacionEntity> observaciones;
}
