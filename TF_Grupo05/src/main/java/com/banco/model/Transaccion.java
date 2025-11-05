package com.banco.model;

import java.util.Date;

/**
 * Clase que representa una transacción bancaria
 * Registra el historial de operaciones
 * 
 * @author Jesús
 */
public class Transaccion {
    private static int contadorId = 1;
    
    private int id;
    private Date fecha;
    private String tipo; // DEPOSITO, RETIRO, TRANSFERENCIA
    private double monto;
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino; // Solo para transferencias
    
    public Transaccion(String tipo, double monto, Cuenta cuentaOrigen) {
        this.id = contadorId++;
        this.fecha = new Date();
        this.tipo = tipo;
        this.monto = monto;
        this.cuentaOrigen = cuentaOrigen;
    }
    
    public Transaccion(String tipo, double monto, Cuenta cuentaOrigen, Cuenta cuentaDestino) {
        this(tipo, monto, cuentaOrigen);
        this.cuentaDestino = cuentaDestino;
    }
    
    // Getters
    public int getId() { return id; }
    public Date getFecha() { return fecha; }
    public String getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public Cuenta getCuentaOrigen() { return cuentaOrigen; }
    public Cuenta getCuentaDestino() { return cuentaDestino; }
    
    /**
     * Genera un reporte de la transacción
     * @return String con el reporte
     */
    public String generarReporte() {
        String reporte = String.format(
            "Transacción #%d | Fecha: %s | Tipo: %s | Monto: $%.2f\n" +
            "Cuenta origen: %s (%d)",
            id, fecha, tipo, monto, 
            cuentaOrigen.getNombreCompleto(), cuentaOrigen.getNumeroTarjeta()
        );
        
        if (cuentaDestino != null) {
            reporte += String.format("\nCuenta destino: %s (%d)", 
                                   cuentaDestino.getNombreCompleto(), 
                                   cuentaDestino.getNumeroTarjeta());
        }
        
        return reporte;
    }
    
    @Override
    public String toString() {
        return String.format("#%d | %s | %s | $%.2f", id, fecha, tipo, monto);
    }
}