package co.edu.unicauca.asae.cleanarquitecture.infraestructura.input.controllerGestionarProductos.DTORespuesta;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductoDTORespuesta {
    private int id;
    private String codigo;
    private String nombre;
    private char tipo;
    private float valor;
    private Date createAt;

    public ProductoDTORespuesta() {

    }
}