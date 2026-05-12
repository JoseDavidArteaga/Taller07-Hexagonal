package co.edu.unicauca.asae.cleanarquitecture.dominio.modelos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Producto {
    private int id;
    private String codigo;
    private String nombre;
    private char tipo;
    private float valor;
    private Date createAt;

    public Producto() {
    }

    public boolean tipoProductoCorrespondeACodigo() {
        System.out.println("determinando si el tipo del producto corresponde con el codigo");
        boolean bandera = false;
        char primerCaracter = this.codigo.charAt(0);
        int primerDigito = Integer.parseInt(primerCaracter + "");
        if (primerDigito == 0 && tipo == 'm') {
            bandera = true;
        } else if (primerDigito == 1 && tipo == 'a') {
            bandera = true;
        } else if (primerDigito == 2 && tipo == 'p') {
            bandera = true;
        }

        return bandera;
    }
}