
package co.edu.unicauca.asae.cleanarquitecture.aplicacion.input;

import java.util.List;

import co.edu.unicauca.asae.cleanarquitecture.dominio.modelos.Producto;

public interface GestionarProductoCUIntPort {
    public Producto crear(Producto objProducto);

    public List<Producto> listar();
}