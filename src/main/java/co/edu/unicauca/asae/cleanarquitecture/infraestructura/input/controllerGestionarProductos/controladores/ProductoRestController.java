package co.edu.unicauca.asae.cleanarquitecture.infraestructura.input.controllerGestionarProductos.controladores;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.asae.cleanarquitecture.aplicacion.input.GestionarProductoCUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.dominio.modelos.Producto;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.input.controllerGestionarProductos.DTOPeticion.ProductoDTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.input.controllerGestionarProductos.DTORespuesta.ProductoDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.input.controllerGestionarProductos.mappers.ProductoMapperInfraestructuraDominio;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductoRestController {

    private final GestionarProductoCUIntPort objGestionarProductosCUInt;
    private final ProductoMapperInfraestructuraDominio objMapeador;

    @PostMapping("/productos")
    public ResponseEntity<ProductoDTORespuesta> create(@RequestBody @Valid ProductoDTOPeticion objProducto) {
        Producto objProductoCrear = objMapeador.mappearDePeticionAProducto(objProducto);
        Producto objProductoCreado = objGestionarProductosCUInt.crear(objProductoCrear);
        ResponseEntity<ProductoDTORespuesta> objRespuesta = new ResponseEntity<ProductoDTORespuesta>(
                objMapeador.mappearDeProductoARespuesta(objProductoCreado),
                HttpStatus.CREATED);
        return objRespuesta;
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTORespuesta>> listar() {
        ResponseEntity<List<ProductoDTORespuesta>> objRespuesta = new ResponseEntity<List<ProductoDTORespuesta>>(
                objMapeador.mappearDeProductosARespuesta(this.objGestionarProductosCUInt.listar()),
                HttpStatus.OK);
        return objRespuesta;
    }
}