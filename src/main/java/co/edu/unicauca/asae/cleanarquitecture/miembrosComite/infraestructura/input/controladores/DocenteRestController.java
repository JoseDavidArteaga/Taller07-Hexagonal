package co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.dtos.DocenteDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.dtos.MiembroComiteDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.mappers.DocenteMapperInfraestructuraDominio;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/docentes")
@RequiredArgsConstructor
public class DocenteRestController {

    private final GestionarDocenteCUIntPort objGestionarDocenteCU;
    private final DocenteMapperInfraestructuraDominio objMapeador;

    @GetMapping
    public ResponseEntity<List<DocenteDTORespuesta>> listar(@RequestParam(value = "nombres", required = false) String nombres) {
        List<Docente> docentes = (nombres == null || nombres.isBlank())
                ? this.objGestionarDocenteCU.listar()
                : this.objGestionarDocenteCU.listarPorNombres(nombres);
        return new ResponseEntity<>(this.objMapeador.mappearDocentesARespuesta(docentes), HttpStatus.OK);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<DocenteDTORespuesta>> listarPorGrupoYPatron(
            @RequestParam String grupo,
            @RequestParam String patron) {
        List<Docente> docentes = this.objGestionarDocenteCU.listarPorGrupoYPatron(grupo, patron);
        return new ResponseEntity<>(this.objMapeador.mappearDocentesARespuesta(docentes), HttpStatus.OK);
    }

    @GetMapping("/comite")
    public ResponseEntity<List<MiembroComiteDTORespuesta>> listarMiembrosComite() {
        List<Docente> docentes = this.objGestionarDocenteCU.listarMiembrosComite();
        return new ResponseEntity<>(this.objMapeador.mappearMiembrosComite(docentes), HttpStatus.OK);
    }
}
