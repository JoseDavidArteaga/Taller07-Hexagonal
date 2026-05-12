package co.edu.unicauca.asae.cleanarquitecture.dominio.casosDeUso;

import java.util.Date;
import java.util.List;

import co.edu.unicauca.asae.cleanarquitecture.aplicacion.input.GestionarProductoCUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.aplicacion.output.GestionarProductoGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.aplicacion.output.ProductoFormateadorResultadosIntPort;
import co.edu.unicauca.asae.cleanarquitecture.dominio.modelos.Producto;

public class GestionarProductoCUAdapter implements GestionarProductoCUIntPort {

    private final GestionarProductoGatewayIntPort objGestionarProductoGateway;
    private final ProductoFormateadorResultadosIntPort objProductoFormateadorResultados;

    public GestionarProductoCUAdapter(GestionarProductoGatewayIntPort objRegistrarProductoGateway,
            ProductoFormateadorResultadosIntPort objProductoFormateadorResultados) {
        this.objGestionarProductoGateway = objRegistrarProductoGateway;
        this.objProductoFormateadorResultados = objProductoFormateadorResultados;
    }

    @Override
    public Producto crear(Producto objProducto) {
        Producto objProductoCreado = null;
        if (this.objGestionarProductoGateway.existeProductoPorCodigo(objProducto.getCodigo())) {
            this.objProductoFormateadorResultados
                    .retornarRespuestaErrorEntidadExiste("Error, se encuentra en el sistema un producto con el codigo");
        } else {

            if (!objProducto.tipoProductoCorrespondeACodigo()) {
                this.objProductoFormateadorResultados
                        .retornarRespuestaErrorReglaDeNegocio(
                                "Error, el codigo no corresponde con el tipo de producto");
            } else {
                objProducto.setCreateAt(new Date());
                objProductoCreado = this.objGestionarProductoGateway.guardar(objProducto);
            }
        }
        return objProductoCreado;
    }

    @Override
    public List<Producto> listar() {
        List<Producto> listaObtenida = objGestionarProductoGateway.listar();
        return listaObtenida;
    }

}