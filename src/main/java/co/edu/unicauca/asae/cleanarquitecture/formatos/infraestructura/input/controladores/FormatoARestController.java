package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.controladores;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.input.GestionarFormatoACUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.ActualizarEstadoFormatoADTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoADTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoADTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.mappers.FormatoAMapperInfraestructuraDominio;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/formatosA")
@RequiredArgsConstructor
public class FormatoARestController {

    private final GestionarFormatoACUIntPort objGestionarFormatoACU;
    private final FormatoAMapperInfraestructuraDominio objMapeador;

    @PostMapping
    public ResponseEntity<FormatoADTORespuesta> crear(@RequestBody @Valid FormatoADTOPeticion peticion) {
        FormatoA aDominio = this.objMapeador.mappearDePeticionAFormatoA(peticion);
        FormatoA creado = this.objGestionarFormatoACU.crear(aDominio);
        return new ResponseEntity<>(this.objMapeador.mappearDeFormatoARespuesta(creado), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FormatoADTORespuesta>> listar() {
        List<FormatoA> lista = this.objGestionarFormatoACU.listar();
        return new ResponseEntity<>(this.objMapeador.mappearDeFormatosARespuesta(lista), HttpStatus.OK);
    }

    @GetMapping("/{idFormatoA}")
    public ResponseEntity<FormatoADTORespuesta> consultar(@PathVariable Integer idFormatoA) {
        FormatoA formato = this.objGestionarFormatoACU.consultarPorId(idFormatoA);
        return new ResponseEntity<>(this.objMapeador.mappearDeFormatoARespuesta(formato), HttpStatus.OK);
    }

    @GetMapping("/docente/{idDocente}")
    public ResponseEntity<List<FormatoADTORespuesta>> consultarPorDocente(@PathVariable Integer idDocente) {
        List<FormatoA> formatos = this.objGestionarFormatoACU.consultarPorDocente(idDocente);
        return new ResponseEntity<>(this.objMapeador.mappearDeFormatosARespuesta(formatos), HttpStatus.OK);
    }

    @PutMapping("/{idFormatoA}/estado")
    public ResponseEntity<FormatoADTORespuesta> actualizarEstado(@PathVariable Integer idFormatoA,
                                                                 @RequestBody @Valid ActualizarEstadoFormatoADTOPeticion peticion) {
        FormatoA actualizado = this.objGestionarFormatoACU.actualizarEstado(idFormatoA, peticion.getEstado());
        return new ResponseEntity<>(this.objMapeador.mappearDeFormatoARespuesta(actualizado), HttpStatus.OK);
    }
}
