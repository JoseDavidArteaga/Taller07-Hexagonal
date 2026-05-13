package co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output;

import java.util.List;

import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.Producto;

public interface GestionarProductoGatewayIntPort {
    public boolean existeProductoPorCodigo(String codigo);

    public Producto guardar(Producto objProducto);

    public List<Producto> listar();
}