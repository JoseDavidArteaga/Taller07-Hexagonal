package co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.input.controladores;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoADTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.aplicacion.input.GestionarObservacionCUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.dominio.modelos.Observacion;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.input.dtos.ObservacionDTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.input.dtos.ObservacionDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.input.mappers.ObservacionMapperInfraestructuraDominio;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/observaciones")
@RequiredArgsConstructor
public class ObservacionRestController {

    private final GestionarObservacionCUIntPort objGestionarObservacionCU;
    private final ObservacionMapperInfraestructuraDominio objMapeador;

    @PostMapping
    public ResponseEntity<ObservacionDTORespuesta> crear(@RequestBody @Valid ObservacionDTOPeticion peticion) {
        Observacion creada = this.objGestionarObservacionCU.crear(
                peticion.getDescripcion(),
                peticion.getIdFormatoA(),
                peticion.getIdsDocentes());
        return new ResponseEntity<>(this.objMapeador.mappearObservacionARespuesta(creada), HttpStatus.CREATED);
    }

    @GetMapping("/formatoA/{idFormatoA}")
    public ResponseEntity<FormatoADTORespuesta> listarPorFormatoA(@PathVariable Integer idFormatoA) {
        FormatoA formatoA = this.objGestionarObservacionCU.listarPorFormatoA(idFormatoA);
        return new ResponseEntity<>(this.objMapeador.mappearFormatoARespuesta(formatoA), HttpStatus.OK);
    }
}
