package com.banco.exception;

/**
 * Excepción personalizada para cuando no se encuentra una cuenta
 * con el número de tarjeta especificado
 */
public class CuentaNoEncontradaException extends Exception {
    
    public CuentaNoEncontradaException() {
        super("Error: No se encontró la cuenta especificada");
    }
    
    public CuentaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
    
    public CuentaNoEncontradaException(int numeroTarjeta) {
        super("No se encontró cuenta con número de tarjeta: " + numeroTarjeta);
    }
}