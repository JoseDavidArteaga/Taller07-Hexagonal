package co.edu.unicauca.asae.cleanarquitecture.aplicacion.output;

public interface ProductoFormateadorResultadosIntPort {

    public void retornarRespuestaErrorEntidadExiste(String mensaje);

    public void retornarRespuestaErrorReglaDeNegocio(String mensaje);
}