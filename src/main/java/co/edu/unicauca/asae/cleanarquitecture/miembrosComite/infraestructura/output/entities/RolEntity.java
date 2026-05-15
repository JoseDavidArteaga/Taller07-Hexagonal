package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRol")
    private Integer idRol;

    @Column(nullable = false, length = 100)
    private String rolAsignado;

    @OneToMany(mappedBy = "rol")
    private List<HistoricoEntity> historicos = new ArrayList<>();
}
