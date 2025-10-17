package com.larian._actividad_larianluisscerbet;

import com.mysql.cj.xdevapi.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Larian
 */

public class AccesoDatos {
    // --- Argumentos para conectar a la base ---
    private String url = "jdbc:mysql://localhost:3306/DatosCoches";  
    private String user = "root";
    private String password = "root";
    // Objeto Connection
    private Connection cn;
    // Objeto PreparedStatement
    private PreparedStatement ps;
    // Objeto ResultSet
    private ResultSet rs;
    
  public void abrirConexion() {
    // --- Intento de conexión ---
    try {
      cn = DriverManager.getConnection(url, user, password);
      System.out.println("Conectado a la base de datos.");
    } catch (SQLException ex) {
      System.out.println("[!]: " + ex.getMessage());
    }      
  }
  
  // --- Cerrar conexión ---
  public void cerrarConexion() {
    if (cn != null) {
      try {
        cn.close();
        System.out.println("Conexión cerrada correctamente.");
      } catch (SQLException ex) {
        System.out.println("[!]: " + ex.getMessage());
      }
    }
  }
  
  // --- Mostrar coches ---
  public void mostrarDatosCoches() {
    try {
      ps = cn.prepareStatement("SELECT * FROM Coches;");
      rs = ps.executeQuery();
      
      // Bucle para mostrar resultados
      System.out.println("Datos de los coches:\n");
      while (rs.next()) {
        System.out.print(rs.getString("Matricula") + "    ");
        System.out.print(rs.getString("Marca") + "    ");
        System.out.print(rs.getInt("Precio") + "\n");
      }
    } catch (SQLException ex) {
      System.out.println("[!]: " + ex.getMessage());
    }
  }
  
  // --- Modificar coches ---
  public void modificarCoche(String matricula, int precio) {
    try {
      ps = cn.prepareStatement("UPDATE Coches SET Precio = ? WHERE Matricula = ?;");
      ps.setInt(1, precio);
      ps.setString(2, matricula);
      ps.executeUpdate();
      System.out.println("Coche modificado correctamente.");
    } catch (SQLException ex) {
      System.out.println("[!]: " + ex.getMessage());
    }
  }
  
  // --- Borrar coches ---
  public void borrarCoche(String matricula) {
    try {  
      ps = cn.prepareStatement("DELETE FROM Coches WHERE Matricula = ?;");
      ps.setString(1, matricula);
      ps.executeUpdate();
      System.out.println("Coche borrado correctamente.");
    } catch (SQLException ex) {
      System.out.println("[!]: " + ex.getMessage());
    }
  }
  
  // --- Insertar coches ---
  public void insertarCoche(String matricula, String marca, int precio, String dni) {
    try {
      
      // Verificar si existe el DNI en la base de datos
      ps = cn.prepareStatement("SELECT DNI FROM Propietarios WHERE DNI = ?;");
      ps.setString(1, dni);
      rs = ps.executeQuery();
      
      // El dni existe
      if(rs.next()) {
        ps = cn.prepareStatement("INSERT INTO Coches (Matricula, Marca, Precio, DNI) VALUES (?, ?, ?, ?);");
        ps.setString(1, matricula);
        ps.setString(2, marca);
        ps.setInt(3, precio);
        ps.setString(4, dni);
        ps.executeUpdate();      
        System.out.println("Coche insertado correctamente.");
      } else {
        // El dni no existe
        System.out.println("[!]: El DNI del propietario no existe en la base de datos.");
      }
    } catch (SQLException ex) {
      System.out.println("[!]: " + ex.getMessage());
    }    
  }
  
  // --- Insertar propietarios ---
  public void insertarPropietario(String dni, String nombre, int edad) {
    try {  
      ps = cn.prepareStatement("INSERT INTO Propietarios (DNI, Nombre, Edad) VALUES (?, ?, ?);");
      ps.setString(1, dni);
      ps.setString(2, nombre);
      ps.setInt(3, edad);
      ps.executeUpdate();
      System.out.println("Propietario insertado correctamente.");
    } catch (SQLException ex) {
      System.out.println("[!]: " + ex.getMessage());
    }    
  }
  
  // --- Listar propiedades (Ap 4) ---
  public void listarPropiedades(String dni) {
    try {  
      ps = cn.prepareStatement("SELECT P.DNI, P.Nombre, P.Edad, C.Matricula, C.Marca, C.Precio FROM Propietarios P, Coches C WHERE P.DNI = C.DNI AND P.DNI = ?;");
      ps.setString(1, dni);
      rs = ps.executeQuery();
      
      System.out.println("Lista de propiedades del propietario con dni: " + dni);
      // Bucle para mostrar resultados
      while (rs.next()) {
        System.out.print(rs.getString("DNI") + "    ");
        System.out.print(rs.getString("Nombre") + "    ");
        System.out.print(rs.getString("Edad") + "    ");
        System.out.print(rs.getString("Matricula") + "    ");
        System.out.print(rs.getString("Marca") + "    ");
        System.out.print(rs.getString("Precio") + "\n");
      }
    } catch (SQLException ex) {
      System.out.println("[!]: " + ex.getMessage());
    }  
  }
  
  // --- Eliminar propietarios (Ap 4) ---
  public void borrarPropietario(String dni) {
    try {
      ps = cn.prepareStatement("DELETE FROM Coches WHERE DNI = ?;");
      ps.setString(1, dni);
      ps.executeUpdate();
      ps = cn.prepareStatement("DELETE FROM Propietarios WHERE DNI = ?;");
      ps.setString(1, dni);
      ps.executeUpdate();
      System.out.println("Propietario y sus coches eliminados correctamente.");
    } catch (SQLException ex) {
      System.out.println("[!]: " + ex.getMessage());
    } 
  }
}