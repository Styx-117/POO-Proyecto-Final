package com.banco.model;

import com.banco.exception.SaldoInsuficienteException;
import java.io.Serializable;

/**
 * Clase abstracta que representa una cuenta bancaria general
 * Es la base para CuentaAhorros y CuentaCorriente
 * 
 * @author Dario
 */
public abstract class Cuenta implements Serializable {
    protected String nombreCompleto;
    protected String dni;
    protected int numeroTarjeta;
    protected int cvv;
    protected double saldo;
    
    /**
     * Constructor de la clase Cuenta
     * @param nombreCompleto Nombre del titular de la cuenta
     * @param dni DNI del titular
     * @param numeroTarjeta Número único de tarjeta
     * @param saldoInicial Saldo inicial de la cuenta
     */
    public Cuenta(String nombreCompleto, String dni, int numeroTarjeta, double saldoInicial) {
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.numeroTarjeta = numeroTarjeta;
        this.cvv = generarCVV();
        this.saldo = saldoInicial;
    }
    
    // Métodos abstractos que las subclases deben implementar
    public abstract void depositar(double monto);
    public abstract void retirar(double monto) throws SaldoInsuficienteException;
    
    /**
     * Realiza una transferencia a otra cuenta
     * @param cuentaDestino Cuenta destino de la transferencia
     * @param monto Monto a transferir
     * @throws SaldoInsuficienteException Si no hay saldo suficiente
     */
    public void transferir(Cuenta cuentaDestino, double monto) throws SaldoInsuficienteException {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (monto > this.saldo) {
            throw new SaldoInsuficienteException(this.saldo, monto);
        }
        
        this.retirar(monto);
        cuentaDestino.depositar(monto);
    }
    
    /**
     * Genera un CVV aleatorio de 3 dígitos
     * @return CVV generado
     */
    private int generarCVV() {
        return (int) (Math.random() * 900) + 100; // Número entre 100-999
    }
    
    // Getters
    public String getNombreCompleto() { return nombreCompleto; }
    public String getDni() { return dni; }
    public int getNumeroTarjeta() { return numeroTarjeta; }
    public int getCvv() { return cvv; }
    public double getSaldo() { return saldo; }
    
    // Setters protegidos
    protected void setSaldo(double saldo) { this.saldo = saldo; }
    
    /**
     * Muestra la información básica de la cuenta
     * @return String con la información de la cuenta
     */
    public String mostrarInformacion() {
        return String.format("Titular: %s | DNI: %s | Tarjeta: %d | Saldo: $%.2f", 
                           nombreCompleto, dni, numeroTarjeta, saldo);
    }
    
    @Override
    public String toString() {
        return mostrarInformacion();
    }
}