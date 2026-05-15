package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Historicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistorico")
    private Integer idHistorico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfk_docente", nullable = false)
    private DocenteEntity docente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfk_rol", nullable = false)
    private RolEntity rol;

    @Column(nullable = false)
    private Boolean activo;

    @Column
    private LocalDate fechaInicio;

    @Column
    private LocalDate fechaFin;
}
