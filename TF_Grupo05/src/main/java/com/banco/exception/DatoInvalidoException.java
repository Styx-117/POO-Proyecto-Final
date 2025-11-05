package com.banco.exception;

/**
 * Excepción personalizada para cuando se ingresan datos inválidos
 * como montos negativos, DNIs incorrectos, etc.
 */
public class DatoInvalidoException extends Exception {
    
    public DatoInvalidoException() {
        super("Error: Dato ingresado no es válido");
    }
    
    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
    
    public DatoInvalidoException(String campo, String valor) {
        super(String.format("Valor inválido para %s: %s", campo, valor));
    }
}