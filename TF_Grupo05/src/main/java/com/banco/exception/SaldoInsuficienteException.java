package com.banco.exception;

/**
 * Excepción personalizada para cuando no hay saldo suficiente
 * en una cuenta para realizar un retiro o transferencia
 */
public class SaldoInsuficienteException extends Exception {
    
    public SaldoInsuficienteException() {
        super("Error: Saldo insuficiente para realizar la operación");
    }
    
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
    
    public SaldoInsuficienteException(double saldoActual, double montoRequerido) {
        super(String.format("Saldo insuficiente. Saldo actual: $%.2f, Monto requerido: $%.2f", 
                          saldoActual, montoRequerido));
    }
}