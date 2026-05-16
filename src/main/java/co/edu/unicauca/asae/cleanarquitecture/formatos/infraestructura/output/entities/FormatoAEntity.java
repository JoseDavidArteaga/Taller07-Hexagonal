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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
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
@Inheritance(strategy = InheritanceType.JOINED)
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

    @javax.persistence.ElementCollection
    @javax.persistence.CollectionTable(name = "FormatoAObjetivos", joinColumns = @javax.persistence.JoinColumn(name = "idFormatoA"))
    @javax.persistence.Column(name = "objetivo", length = 500)
    private List<String> objetivos;

    @OneToOne(mappedBy = "formatoA", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private EstadoEntity estado;

    @OneToMany(mappedBy = "formatoA", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EvaluacionEntity> evaluaciones;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idfk_docente", nullable = false)
    private DocenteEntity docente;

    @PrePersist
    private void registrarEstadoInicial() {
        if (estado == null) {
            EstadoEntity estadoInicial = new EstadoEntity();
            estadoInicial.setEstado("En formulacion");
            estadoInicial.setFormatoA(this);
            estado = estadoInicial;
        }
    }
}
