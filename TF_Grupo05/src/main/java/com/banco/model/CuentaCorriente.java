package com.banco.model;

import com.banco.exception.SaldoInsuficienteException;

/**
 * Clase que representa una cuenta corriente
 * Hereda de Cuenta y permite descubierto (sobregiro)
 * 
 * @author Jesús
 */
public class CuentaCorriente extends Cuenta {
    private double limiteDescubierto;
    
    public CuentaCorriente(String nombreCompleto, String dni, int numeroTarjeta, double saldoInicial) {
        super(nombreCompleto, dni, numeroTarjeta, saldoInicial);
        this.limiteDescubierto = 500.0; // Límite de $500 de descubierto
    }
    
    @Override
    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        setSaldo(getSaldo() + monto);
    }
    
    @Override
    public void retirar(double monto) throws SaldoInsuficienteException {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        
        double saldoDisponible = getSaldo() + limiteDescubierto;
        if (monto > saldoDisponible) {
            // ✅ CORREGIDO: Usamos el constructor con mensaje personalizado
            throw new SaldoInsuficienteException(
                String.format("Saldo insuficiente. Saldo actual: $%.2f, Monto requerido: $%.2f, Límite descubierto: $%.2f", 
                             getSaldo(), monto, limiteDescubierto)
            );
        }
        setSaldo(getSaldo() - monto);
    }
    
    // Getters y Setters
    public double getLimiteDescubierto() { return limiteDescubierto; }
    public void setLimiteDescubierto(double limiteDescubierto) { 
        this.limiteDescubierto = limiteDescubierto; 
    }
    
    @Override
    public String mostrarInformacion() {
        return String.format("CORRIENTE | %s | Límite: $%.2f", 
                           super.mostrarInformacion(), limiteDescubierto);
    }
}