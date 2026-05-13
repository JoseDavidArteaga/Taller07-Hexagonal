
package co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.input;

import java.util.List;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Producto;

public interface GestionarProductoCUIntPort {
    public Producto crear(Producto objProducto);

    public List<Producto> listar();
}