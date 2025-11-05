package com.banco.service;

import com.banco.model.*;
import com.banco.exception.*;
import com.banco.persistence.DataManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio principal que maneja toda la lógica del banco
 * 
 * @author Aydan
 */
public class BancoService {
    private List<Usuario> usuarios;
    private List<Cuenta> cuentas;
    private List<Transaccion> transacciones;
    private DataManager dataManager;
    
    public BancoService() {
        this.dataManager = new DataManager();
        cargarDatosIniciales();
    }
    
    /**
     * Carga los datos desde los archivos
     */
    private void cargarDatosIniciales() {
        this.usuarios = dataManager.cargarClientes();
        this.cuentas = dataManager.cargarCuentas();
        this.transacciones = dataManager.cargarTransacciones();
        
        if (this.usuarios == null) this.usuarios = new ArrayList<>();
        if (this.cuentas == null) this.cuentas = new ArrayList<>();
        if (this.transacciones == null) this.transacciones = new ArrayList<>();
    }
    
    /**
     * Registra un nuevo cliente en el sistema
     */
    public void registrarCliente(String nombre, String dni, String tipoUsuario) {
        if (buscarClientePorDni(dni) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con DNI: " + dni);
        }
        
        Usuario nuevoUsuario = new Usuario(nombre, dni, tipoUsuario);
        usuarios.add(nuevoUsuario);
        dataManager.guardarClientes(usuarios);
        System.out.println("✅ Cliente registrado: " + nombre);
    }
    
    /**
     * Abre una nueva cuenta de ahorros para un cliente
     */
    public void abrirCuentaAhorros(String dni, double saldoInicial) {
        Usuario usuario = buscarClientePorDni(dni);
        if (usuario == null) {
            throw new IllegalArgumentException("Cliente no encontrado con DNI: " + dni);
        }
        
        int numeroTarjeta = generarNumeroTarjeta();
        CuentaAhorros nuevaCuenta = new CuentaAhorros(usuario.getNombre(), dni, numeroTarjeta, saldoInicial);
        
        cuentas.add(nuevaCuenta);
        usuario.registrarCuenta(nuevaCuenta);
        
        dataManager.guardarCuentas(cuentas);
        dataManager.guardarClientes(usuarios);
        
        System.out.println("✅ Cuenta de ahorros creada: " + numeroTarjeta);
    }
    
    /**
     * Abre una nueva cuenta corriente para un cliente
     */
    public void abrirCuentaCorriente(String dni, double saldoInicial) {
        Usuario usuario = buscarClientePorDni(dni);
        if (usuario == null) {
            throw new IllegalArgumentException("Cliente no encontrado con DNI: " + dni);
        }
        
        int numeroTarjeta = generarNumeroTarjeta();
        CuentaCorriente nuevaCuenta = new CuentaCorriente(usuario.getNombre(), dni, numeroTarjeta, saldoInicial);
        
        cuentas.add(nuevaCuenta);
        usuario.registrarCuenta(nuevaCuenta);
        
        dataManager.guardarCuentas(cuentas);
        dataManager.guardarClientes(usuarios);
        
        System.out.println("✅ Cuenta corriente creada: " + numeroTarjeta);
    }
    
    /**
     * Realiza un depósito en una cuenta
     */
    public void depositar(int numeroTarjeta, double monto) {
        Cuenta cuenta = buscarCuentaPorNumero(numeroTarjeta);
        if (cuenta == null) {
            throw new IllegalArgumentException("Cuenta no encontrada: " + numeroTarjeta);
        }
        
        cuenta.depositar(monto);
        Transaccion transaccion = new Transaccion("DEPOSITO", monto, cuenta);
        transacciones.add(transaccion);
        
        dataManager.guardarCuentas(cuentas);
        dataManager.guardarTransacciones(transacciones);
        
        System.out.println("✅ Depósito exitoso: $" + monto);
    }
    
    /**
     * Realiza un retiro de una cuenta
     */
    public void retirar(int numeroTarjeta, double monto) {
        Cuenta cuenta = buscarCuentaPorNumero(numeroTarjeta);
        if (cuenta == null) {
            throw new IllegalArgumentException("Cuenta no encontrada: " + numeroTarjeta);
        }
        
        try {
            cuenta.retirar(monto);
            Transaccion transaccion = new Transaccion("RETIRO", monto, cuenta);
            transacciones.add(transaccion);
            
            dataManager.guardarCuentas(cuentas);
            dataManager.guardarTransacciones(transacciones);
            
            System.out.println("✅ Retiro exitoso: $" + monto);
        } catch (SaldoInsuficienteException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    /**
     * Realiza una transferencia entre cuentas
     */
    public void transferir(int numeroTarjetaOrigen, int numeroTarjetaDestino, double monto) {
        Cuenta cuentaOrigen = buscarCuentaPorNumero(numeroTarjetaOrigen);
        Cuenta cuentaDestino = buscarCuentaPorNumero(numeroTarjetaDestino);
        
        if (cuentaOrigen == null || cuentaDestino == null) {
            throw new IllegalArgumentException("Una o ambas cuentas no existen");
        }
        
        try {
            cuentaOrigen.transferir(cuentaDestino, monto);
            Transaccion transaccion = new Transaccion("TRANSFERENCIA", monto, cuentaOrigen, cuentaDestino);
            transacciones.add(transaccion);
            
            dataManager.guardarCuentas(cuentas);
            dataManager.guardarTransacciones(transacciones);
            
            System.out.println("✅ Transferencia exitosa: $" + monto);
        } catch (SaldoInsuficienteException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    /**
     * Consulta el saldo de una cuenta
     */
    public void consultarSaldo(int numeroTarjeta) {
        Cuenta cuenta = buscarCuentaPorNumero(numeroTarjeta);
        if (cuenta == null) {
            throw new IllegalArgumentException("Cuenta no encontrada: " + numeroTarjeta);
        }
        
        System.out.println("💳 Saldo actual: $" + cuenta.getSaldo());
    }
    
    /**
     * Busca un cliente por DNI
     */
    public Usuario buscarClientePorDni(String dni) {
        return usuarios.stream()
                      .filter(usuario -> usuario.getDni().equals(dni))
                      .findFirst()
                      .orElse(null);
    }
    
    /**
     * Busca una cuenta por número de tarjeta
     */
    public Cuenta buscarCuentaPorNumero(int numeroTarjeta) {
        return cuentas.stream()
                     .filter(cuenta -> cuenta.getNumeroTarjeta() == numeroTarjeta)
                     .findFirst()
                     .orElse(null);
    }
    
    /**
     * Lista todos los clientes
     */
    public void listarClientes() {
        if (usuarios.isEmpty()) {
            System.out.println("📝 No hay clientes registrados");
            return;
        }
        
        System.out.println("📋 LISTA DE CLIENTES:");
        usuarios.forEach(usuario -> {
            System.out.println("👤 " + usuario.mostrarInformacionCompleta());
            System.out.println("---");
        });
    }
    
    /**
     * Lista todas las cuentas
     */
    public void listarCuentas() {
        if (cuentas.isEmpty()) {
            System.out.println("📝 No hay cuentas registradas");
            return;
        }
        
        System.out.println("📋 LISTA DE CUENTAS:");
        cuentas.forEach(cuenta -> System.out.println("💳 " + cuenta.mostrarInformacion()));
    }
    
    /**
     * Muestra el historial de transacciones de una cuenta
     */
    public void mostrarHistorial(int numeroTarjeta) {
        List<Transaccion> historial = transacciones.stream()
                                                  .filter(t -> t.getCuentaOrigen().getNumeroTarjeta() == numeroTarjeta)
                                                  .collect(Collectors.toList());
        
        if (historial.isEmpty()) {
            System.out.println("📝 No hay transacciones para esta cuenta");
            return;
        }
        
        System.out.println("📊 HISTORIAL DE TRANSACCIONES:");
        historial.forEach(t -> System.out.println("🕒 " + t));
    }
    
    /**
     * Genera un número de tarjeta único
     */
    private int generarNumeroTarjeta() {
        Random random = new Random();
        int numero;
        do {
            numero = 100000000 + random.nextInt(900000000);
        } while (buscarCuentaPorNumero(numero) != null);
        
        return numero;
    }
    
    // Getters para estadísticas
    public int getTotalClientes() { return usuarios.size(); }
    public int getTotalCuentas() { return cuentas.size(); }
    public int getTotalTransacciones() { return transacciones.size(); }
}