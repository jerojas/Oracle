/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jctableeditable;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jeovany
 */
public class Conexion {

    Connection conn;
    Statement sentencia;
    ResultSet resultado;

    public Conexion() {

        System.out.println("Conexión a la base de datos...");

        try { // Se carga el driver JDBC-ODBC
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Se pudo cargar el driver JDBC");
        } catch (Exception e) {
            System.out.println("No se pudo cargar el driver JDBC");
            return;
        }
        try { // Se establece la conexión con la base de datos
            conn = DriverManager.getConnection("jdbc:oracle:thin:@Jeovany-PC:1521:xe", "system", "gato");
            sentencia = conn.createStatement();
            System.out.println("Conexión a la base de datos");
        } catch (SQLException e) {
            System.out.println("No hay conexión con la base de datos.");
            return;
        }

    } //Fin del main
    
    
    

    public void cerrar() throws SQLException {
        conn.close();
    }

    public ResultSet ejecutarSelect(String sql) {

        try {
            return sentencia.executeQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    

    public ResultSet ejecutarDML(String sql) {

        try {
            sentencia.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


 public Object[][] Select_Persona()
    {
     int registros = 0;      
      String consulta = "Select codigo, nombre  FROM Departamento ";
      String consulta2 = "Select count(*) as total from Departamento ";
      //obtenemos la cantidad de registros existentes en la tabla
      try{
         PreparedStatement pstm = conn.prepareStatement( consulta2 );
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.out.println(e);
      }
    //se crea una matriz con tantas filas y columnas que necesite
    Object[][] data = new String[registros][2];
    //realizamos la consulta sql y llenamos los datos en la matriz "Object"
      try{
         PreparedStatement pstm = conn.prepareStatement(consulta);
         ResultSet res = pstm.executeQuery();
         int i = 0;
         while(res.next()){            
            data[i][0] = res.getString( "Codigo" );
            data[i][1] = res.getString( "Nombre" );
            
            i++;
         }
         res.close();
          }catch(SQLException e){
               System.out.println(e);
        }
    return data;
    }

/* Ejecuta la actualizacion de la tabla persona dado los valores de actualizacion
 * y el ID del registro a afectar
 */
    public boolean update(String valores, String id)
    {
        boolean res = false;        
        String q = " UPDATE Departamento SET " + valores + " WHERE Codigo = " + id;
        try {
            PreparedStatement pstm = conn.prepareStatement(q);
            pstm.execute();
            pstm.close();
            res=true;
         }catch(SQLException e){            
            System.out.println(e);
        }
        return res;
    }

    
}



