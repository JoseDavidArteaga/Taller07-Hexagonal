package co.edu.unicauca.asae.cleanarquitecture.infraestructura.input.controllerGestionarProductos.DTOPeticion;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductoDTOPeticion {    
    @NotNull(message = "{producto.codigo.emply}")
    @Size(min = 5, max = 7, message = "{producto.codigo.size}")
    private String codigo;
    @NotNull(message = "{producto.nombre.emply}")
    @Size(min = 5, max = 45, message = "{producto.nombre.size}")
    private String nombre;
    @NotNull(message = "{producto.tipo.emply}") 
    private char tipo;
    @PositiveOrZero(message = "{producto.valor.positive}")
    private float valor; 
}