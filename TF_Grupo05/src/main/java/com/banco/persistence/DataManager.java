package com.banco.persistence;

import com.banco.model.*;
import java.util.*;
import java.io.*;

/**
 * Maneja la persistencia de datos mediante serialización
 * 
 * @author Dario
 */
public class DataManager {
    private static final String DIR_BASE = "data/";
    private static final String DIR_CLIENTES = DIR_BASE + "clientes/";
    private static final String DIR_CUENTAS = DIR_BASE + "cuentas/";
    private static final String DIR_TRANSACCIONES = DIR_BASE + "transacciones/";
    
    private static final String ARCHIVO_CLIENTES = DIR_CLIENTES + "lista_clientes.dat";
    private static final String ARCHIVO_CUENTAS = DIR_CUENTAS + "todas_cuentas.dat";
    private static final String ARCHIVO_TRANSACCIONES = DIR_TRANSACCIONES + "historial_general.dat";
    
    public DataManager() {
        inicializarEstructura();
    }
    
    /**
     * Crea la estructura de carpetas si no existe
     */
    private void inicializarEstructura() {
        crearCarpetaSiNoExiste(DIR_CLIENTES);
        crearCarpetaSiNoExiste(DIR_CUENTAS);
        crearCarpetaSiNoExiste(DIR_TRANSACCIONES);
    }
    
    private void crearCarpetaSiNoExiste(String path) {
        File carpeta = new File(path);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
    
    /**
     * Guarda la lista de clientes en archivo
     */
    public void guardarClientes(List<Usuario> clientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
             new FileOutputStream(ARCHIVO_CLIENTES))) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            System.out.println("❌ Error guardando clientes: " + e.getMessage());
        }
    }
    
    /**
     * Carga la lista de clientes desde archivo
     */
    @SuppressWarnings("unchecked")
    public List<Usuario> cargarClientes() {
        try (ObjectInputStream ois = new ObjectInputStream(
             new FileInputStream(ARCHIVO_CLIENTES))) {
            return (List<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️  Primera ejecución - creando archivo nuevo de clientes");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error cargando clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Guarda la lista de cuentas en archivo
     */
    public void guardarCuentas(List<Cuenta> cuentas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
             new FileOutputStream(ARCHIVO_CUENTAS))) {
            oos.writeObject(cuentas);
        } catch (IOException e) {
            System.out.println("❌ Error guardando cuentas: " + e.getMessage());
        }
    }
    
    /**
     * Carga la lista de cuentas desde archivo
     */
    @SuppressWarnings("unchecked")
    public List<Cuenta> cargarCuentas() {
        try (ObjectInputStream ois = new ObjectInputStream(
             new FileInputStream(ARCHIVO_CUENTAS))) {
            return (List<Cuenta>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️  Primera ejecución - creando archivo nuevo de cuentas");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error cargando cuentas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Guarda la lista de transacciones en archivo
     */
    public void guardarTransacciones(List<Transaccion> transacciones) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
             new FileOutputStream(ARCHIVO_TRANSACCIONES))) {
            oos.writeObject(transacciones);
        } catch (IOException e) {
            System.out.println("❌ Error guardando transacciones: " + e.getMessage());
        }
    }
    
    /**
     * Carga la lista de transacciones desde archivo
     */
    @SuppressWarnings("unchecked")
    public List<Transaccion> cargarTransacciones() {
        try (ObjectInputStream ois = new ObjectInputStream(
             new FileInputStream(ARCHIVO_TRANSACCIONES))) {
            return (List<Transaccion>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️  Primera ejecución - creando archivo nuevo de transacciones");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error cargando transacciones: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Guarda todos los datos del sistema
     */
    public void guardarTodo(List<Usuario> clientes, List<Cuenta> cuentas, List<Transaccion> transacciones) {
        guardarClientes(clientes);
        guardarCuentas(cuentas);
        guardarTransacciones(transacciones);
        System.out.println("✅ Todos los datos guardados exitosamente");
    }
}