/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.larian._actividad_larianluisscerbet;

import java.sql.SQLException;

/**
 *
 * @author Alumno
 */
public class PruebaAccesoDatos {
  public static void main (String[] args) throws SQLException{
      AccesoDatos AD = new AccesoDatos();
      AD.abrirConexion();
      AD.mostrarDatosCoches();
      AD.modificarCoche("BA-3333", 5000);
      AD.borrarCoche("MA");
      AD.insertarCoche("AA-0005", "Ford", 4500, "1A");
      AD.insertarPropietario("X25", "Jose", 54);
      // Apartado 4
      AD.listarPropiedades("1A");
      AD.cerrarConexion();
  }
}
