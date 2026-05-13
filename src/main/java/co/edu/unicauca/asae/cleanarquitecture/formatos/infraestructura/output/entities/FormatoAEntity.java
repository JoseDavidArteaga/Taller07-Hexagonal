package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.DocenteEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FormatosA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormatoAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFormatoA")
    private Integer idFormatoA;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(length = 500)
    private String objetivo;

    @OneToOne(mappedBy = "formatoA", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private EstadoEntity estado;

    @OneToMany(mappedBy = "formatoA", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EvaluacionEntity> evaluaciones;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<DocenteEntity> docentes;
}
