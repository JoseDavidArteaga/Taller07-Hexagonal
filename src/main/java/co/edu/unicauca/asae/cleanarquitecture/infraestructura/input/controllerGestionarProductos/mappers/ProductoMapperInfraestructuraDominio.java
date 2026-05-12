package co.edu.unicauca.asae.cleanarquitecture.infraestructura.input.controllerGestionarProductos.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.unicauca.asae.cleanarquitecture.dominio.modelos.Producto;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.input.controllerGestionarProductos.DTOPeticion.ProductoDTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.input.controllerGestionarProductos.DTORespuesta.ProductoDTORespuesta;

@Mapper(componentModel = "spring")
public interface ProductoMapperInfraestructuraDominio {
    Producto mappearDePeticionAProducto(ProductoDTOPeticion peticion);

    ProductoDTORespuesta mappearDeProductoARespuesta(Producto objProducto);

    List<ProductoDTORespuesta> mappearDeProductosARespuesta(List<Producto> productos);
}