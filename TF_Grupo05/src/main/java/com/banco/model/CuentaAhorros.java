package com.banco.model;

import com.banco.exception.SaldoInsuficienteException;

/**
 * Clase que representa una cuenta de ahorros
 * Hereda de Cuenta y aplica tasa de interés
 * 
 * @author Jesús
 */
public class CuentaAhorros extends Cuenta {
    private double tasaInteres;
    
    public CuentaAhorros(String nombreCompleto, String dni, int numeroTarjeta, double saldoInicial) {
        super(nombreCompleto, dni, numeroTarjeta, saldoInicial);
        this.tasaInteres = 0.02; // 2% de interés anual
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
        if (monto > getSaldo()) {
            throw new SaldoInsuficienteException(getSaldo(), monto);
        }
        setSaldo(getSaldo() - monto);
    }
    
    /**
     * Aplica el interés a la cuenta de ahorros
     */
    public void aplicarInteres() {
        double interes = getSaldo() * tasaInteres;
        setSaldo(getSaldo() + interes);
    }
    
    // Getters y Setters
    public double getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(double tasaInteres) { this.tasaInteres = tasaInteres; }
    
    @Override
    public String mostrarInformacion() {
        return String.format("AHORROS | %s | Tasa interés: %.1f%%", 
                           super.mostrarInformacion(), tasaInteres * 100);
    }
}