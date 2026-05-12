package co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.gateway;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.asae.cleanarquitecture.aplicacion.output.GestionarProductoGatewayIntPort;
import co.edu.unicauca.asae.cleanarquitecture.dominio.modelos.Producto;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.entidades.ProductoEntity;
import co.edu.unicauca.asae.cleanarquitecture.infraestructura.output.persistencia.repositorios.ProductoRepositoryInt;

@Service
public class GestionarProductoGatewayImplAdapter implements GestionarProductoGatewayIntPort {

    private final ProductoRepositoryInt objProductoRepository;
    private final ModelMapper productoModelMapper;

    public GestionarProductoGatewayImplAdapter(ProductoRepositoryInt objProductoRepository,
            ModelMapper productoModelMapper) {
        this.objProductoRepository = objProductoRepository;
        this.productoModelMapper = productoModelMapper;
    }

    @Override
    public boolean existeProductoPorCodigo(String codigo) {
        return this.objProductoRepository.existeProductoPorCodigo(codigo) == 1;
    }

    @Override
    public Producto guardar(Producto objProducto) {
        ProductoEntity objProductoEntity = this.productoModelMapper.map(objProducto, ProductoEntity.class);
        ProductoEntity objProductoEntityRegistrado = this.objProductoRepository.save(objProductoEntity);
        Producto objProductoRespuesta = this.productoModelMapper.map(objProductoEntityRegistrado, Producto.class);
        return objProductoRespuesta;
    }

    @Override
    public List<Producto> listar() {
        Iterable<ProductoEntity> lista = this.objProductoRepository.findAll();
        List<Producto> listaObtenida = this.productoModelMapper.map(lista, new TypeToken<List<Producto>>() {
        }.getType());
        return listaObtenida;
    }

}