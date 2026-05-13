package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.input.GestionarEvaluacionCUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Evaluacion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.EvaluacionDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.mappers.FormatoAMapperInfraestructuraDominio;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionRestController {

    private final GestionarEvaluacionCUIntPort objGestionarEvaluacionCU;
    private final FormatoAMapperInfraestructuraDominio objMapeador;

    @GetMapping("/historico/{idFormatoA}")
    public ResponseEntity<List<EvaluacionDTORespuesta>> historicoPorFormatoA(@PathVariable Integer idFormatoA) {
        List<Evaluacion> historico = this.objGestionarEvaluacionCU.historicoPorFormatoA(idFormatoA);
        return new ResponseEntity<>(this.objMapeador.mappearEvaluaciones(historico), HttpStatus.OK);
    }
}
