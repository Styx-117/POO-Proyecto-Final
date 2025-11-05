package com.banco.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un usuario del banco
 * Puede tener múltiples cuentas asociadas
 * 
 * @author Aydan
 */
public class Usuario {
    private String nombre;
    private String dni;
    private String tipoUsuario; // NATURAL o JURIDICO
    private List<Cuenta> cuentas;
    
    public Usuario(String nombre, String dni, String tipoUsuario) {
        this.nombre = nombre;
        this.dni = dni;
        this.tipoUsuario = tipoUsuario;
        this.cuentas = new ArrayList<>();
    }
    
    /**
     * Registra una nueva cuenta para el usuario
     * @param cuenta Cuenta a registrar
     */
    public void registrarCuenta(Cuenta cuenta) {
        if (cuenta != null) {
            this.cuentas.add(cuenta);
        }
    }
    
    /**
     * Busca una cuenta por número de tarjeta
     * @param numeroTarjeta Número de tarjeta a buscar
     * @return La cuenta encontrada o null
     */
    public Cuenta buscarCuentaPorNumero(int numeroTarjeta) {
        return cuentas.stream()
                     .filter(cuenta -> cuenta.getNumeroTarjeta() == numeroTarjeta)
                     .findFirst()
                     .orElse(null);
    }
    
    /**
     * Obtiene el saldo total de todas las cuentas del usuario
     * @return Saldo total
     */
    public double getSaldoTotal() {
        return cuentas.stream()
                     .mapToDouble(Cuenta::getSaldo)
                     .sum();
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getDni() { return dni; }
    public String getTipoUsuario() { return tipoUsuario; }
    public List<Cuenta> getCuentas() { return cuentas; }
    
    /**
     * Muestra información del usuario y sus cuentas
     * @return String con la información
     */
    public String mostrarInformacionCompleta() {
        StringBuilder info = new StringBuilder();
        info.append(String.format("Usuario: %s | DNI: %s | Tipo: %s\n", 
                                nombre, dni, tipoUsuario));
        info.append("Cuentas:\n");
        
        if (cuentas.isEmpty()) {
            info.append("  No tiene cuentas registradas\n");
        } else {
            for (int i = 0; i < cuentas.size(); i++) {
                info.append(String.format("  %d. %s\n", i + 1, cuentas.get(i).mostrarInformacion()));
            }
        }
        info.append(String.format("Saldo total: $%.2f", getSaldoTotal()));
        
        return info.toString();
    }
    
    @Override
    public String toString() {
        return String.format("Usuario: %s | DNI: %s | Tipo: %s | Cuentas: %d", 
                           nombre, dni, tipoUsuario, cuentas.size());
    }
}