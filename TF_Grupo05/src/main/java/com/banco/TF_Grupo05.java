package com.banco;

import com.banco.service.BancoService;
import java.util.Scanner;

/**
 * Clase principal con el menú de la aplicación
 * 
 * @author Matías
 */
public class TF_Grupo05 {
    private static BancoService bancoService = new BancoService();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("🏦 ================================");
        System.out.println("🏦    SIMULADOR BANCARIO GRUPO 05   ");
        System.out.println("🏦 ================================");
        System.out.println("Sistema iniciado. Datos cargados: " + 
                          bancoService.getTotalClientes() + " clientes, " +
                          bancoService.getTotalCuentas() + " cuentas");
        
        mostrarMenuPrincipal();
    }
    
    public static void mostrarMenuPrincipal() {
        boolean ejecutando = true;
        
        while (ejecutando) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. 👤 Gestión de Clientes");
            System.out.println("2. 💰 Operaciones de Cuentas");
            System.out.println("3. 📊 Consultas y Reportes");
            System.out.println("4. 🚪 Salir del Sistema");
            System.out.print("Seleccione una opción: ");
            
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                switch (opcion) {
                    case 1:
                        mostrarMenuClientes();
                        break;
                    case 2:
                        mostrarMenuOperaciones();
                        break;
                    case 3:
                        mostrarMenuConsultas();
                        break;
                    case 4:
                        ejecutando = false;
                        System.out.println("👋 ¡Gracias por usar el Simulador Bancario!");
                        break;
                    default:
                        System.out.println("❌ Opción no válida");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: Ingrese un número válido");
                scanner.nextLine(); // Limpiar buffer en caso de error
            }
        }
    }
    
    public static void mostrarMenuClientes() {
        boolean enMenu = true;
        
        while (enMenu) {
            System.out.println("\n=== GESTIÓN DE CLIENTES ===");
            System.out.println("1. 📝 Registrar nuevo cliente");
            System.out.println("2. 📋 Listar todos los clientes");
            System.out.println("3. 🔙 Volver al menú principal");
            System.out.print("Seleccione opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    bancoService.listarClientes();
                    break;
                case 3:
                    enMenu = false;
                    break;
                default:
                    System.out.println("❌ Opción no válida");
            }
        }
    }
    
    public static void mostrarMenuOperaciones() {
        boolean enMenu = true;
        
        while (enMenu) {
            System.out.println("\n=== OPERACIONES DE CUENTAS ===");
            System.out.println("1. 🆕 Abrir cuenta de ahorros");
            System.out.println("2. 🆕 Abrir cuenta corriente");
            System.out.println("3. 📥 Depositar dinero");
            System.out.println("4. 📤 Retirar dinero");
            System.out.println("5. 🔄 Transferir entre cuentas");
            System.out.println("6. 🔙 Volver al menú principal");
            System.out.print("Seleccione opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    abrirCuentaAhorros();
                    break;
                case 2:
                    abrirCuentaCorriente();
                    break;
                case 3:
                    realizarDeposito();
                    break;
                case 4:
                    realizarRetiro();
                    break;
                case 5:
                    realizarTransferencia();
                    break;
                case 6:
                    enMenu = false;
                    break;
                default:
                    System.out.println("❌ Opción no válida");
            }
        }
    }
    
    public static void mostrarMenuConsultas() {
        boolean enMenu = true;
        
        while (enMenu) {
            System.out.println("\n=== CONSULTAS Y REPORTES ===");
            System.out.println("1. 💳 Consultar saldo");
            System.out.println("2. 📋 Listar todas las cuentas");
            System.out.println("3. 📊 Ver historial de transacciones");
            System.out.println("4. 🔙 Volver al menú principal");
            System.out.print("Seleccione opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    consultarSaldo();
                    break;
                case 2:
                    bancoService.listarCuentas();
                    break;
                case 3:
                    verHistorial();
                    break;
                case 4:
                    enMenu = false;
                    break;
                default:
                    System.out.println("❌ Opción no válida");
            }
        }
    }
    
    // Métodos para las operaciones específicas
    public static void registrarCliente() {
        System.out.println("\n--- REGISTRAR NUEVO CLIENTE ---");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        
        System.out.print("Tipo (NATURAL/JURIDICO): ");
        String tipo = scanner.nextLine();
        
        bancoService.registrarCliente(nombre, dni, tipo);
    }
    
    public static void abrirCuentaAhorros() {
        System.out.println("\n--- ABRIR CUENTA DE AHORROS ---");
        System.out.print("DNI del titular: ");
        String dni = scanner.nextLine();
        
        System.out.print("Saldo inicial: ");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine();
        
        bancoService.abrirCuentaAhorros(dni, saldoInicial);
    }
    
    public static void abrirCuentaCorriente() {
        System.out.println("\n--- ABRIR CUENTA CORRIENTE ---");
        System.out.print("DNI del titular: ");
        String dni = scanner.nextLine();
        
        System.out.print("Saldo inicial: ");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine();
        
        bancoService.abrirCuentaCorriente(dni, saldoInicial);
    }
    
    public static void realizarDeposito() {
        System.out.println("\n--- REALIZAR DEPÓSITO ---");
        System.out.print("Número de tarjeta: ");
        int numeroTarjeta = scanner.nextInt();
        
        System.out.print("Monto a depositar: ");
        double monto = scanner.nextDouble();
        scanner.nextLine();
        
        bancoService.depositar(numeroTarjeta, monto);
    }
    
    public static void realizarRetiro() {
        System.out.println("\n--- REALIZAR RETIRO ---");
        System.out.print("Número de tarjeta: ");
        int numeroTarjeta = scanner.nextInt();
        
        System.out.print("Monto a retirar: ");
        double monto = scanner.nextDouble();
        scanner.nextLine();
        
        bancoService.retirar(numeroTarjeta, monto);
    }
    
    public static void realizarTransferencia() {
        System.out.println("\n--- REALIZAR TRANSFERENCIA ---");
        System.out.print("Número de tarjeta origen: ");
        int numeroOrigen = scanner.nextInt();
        
        System.out.print("Número de tarjeta destino: ");
        int numeroDestino = scanner.nextInt();
        
        System.out.print("Monto a transferir: ");
        double monto = scanner.nextDouble();
        scanner.nextLine();
        
        bancoService.transferir(numeroOrigen, numeroDestino, monto);
    }
    
    public static void consultarSaldo() {
        System.out.println("\n--- CONSULTAR SALDO ---");
        System.out.print("Número de tarjeta: ");
        int numeroTarjeta = scanner.nextInt();
        scanner.nextLine();
        
        bancoService.consultarSaldo(numeroTarjeta);
    }
    
    public static void verHistorial() {
        System.out.println("\n--- VER HISTORIAL ---");
        System.out.print("Número de tarjeta: ");
        int numeroTarjeta = scanner.nextInt();
        scanner.nextLine();
        
        bancoService.mostrarHistorial(numeroTarjeta);
    }
}