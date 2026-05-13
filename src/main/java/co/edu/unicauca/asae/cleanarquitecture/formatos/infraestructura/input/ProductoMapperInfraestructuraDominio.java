package co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Producto;

@Mapper(componentModel = "spring")
public interface ProductoMapperInfraestructuraDominio {
    Producto mappearDePeticionAProducto(ProductoDTOPeticion peticion);

    ProductoDTORespuesta mappearDeProductoARespuesta(Producto objProducto);

    List<ProductoDTORespuesta> mappearDeProductosARespuesta(List<Producto> productos);
}