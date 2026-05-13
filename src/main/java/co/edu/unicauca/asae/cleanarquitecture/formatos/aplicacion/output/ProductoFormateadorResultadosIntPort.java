package co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.output;

public interface ProductoFormateadorResultadosIntPort {

    public void retornarRespuestaErrorEntidadExiste(String mensaje);

    public void retornarRespuestaErrorReglaDeNegocio(String mensaje);

    public void retornarRespuestaErrorEntidadNoExiste(String mensaje);
}