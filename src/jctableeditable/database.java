package jctableeditable;
import java.sql.*;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class database {
 /* DATOS PARA LA CONEXION */
  private String bd = "";//BASE DE DATOS
  private String login = "root"; //USUARIO
  private String password = ""; //CONTRASEÑA
  private String url = "jdbc:mysql://localhost/"+bd;
  private Connection conn = null;

  /* Constructor de clase: Se conecta a la base de datos
  */
   public database(){
      try{
         //obtenemos el driver de para mysql
         Class.forName("com.mysql.jdbc.Driver");
         //obtenemos la conexión
         conn = DriverManager.getConnection(url,login,password);
         if (conn!=null){
            System.out.println("OK base de datos "+bd+" listo");
         }
      }catch(SQLException e){
         System.out.println(e);
      }catch(ClassNotFoundException e){
         System.out.println(e);
      }
    }

    public Connection getConnection()
    {
        return this.conn;
    }
/* Realiza una consulta a la base de datos, retorna un Object[][] con los
 * datos de la tabla persona
 */
    public Object[][] Select_Persona()
    {
     int registros = 0;      
      String consulta = "Select p_id,p_nombre,p_apellido,p_edad FROM persona ";
      String consulta2 = "Select count(*) as total from persona ";
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
    Object[][] data = new String[registros][4];
    //realizamos la consulta sql y llenamos los datos en la matriz "Object"
      try{
         PreparedStatement pstm = conn.prepareStatement(consulta);
         ResultSet res = pstm.executeQuery();
         int i = 0;
         while(res.next()){            
            data[i][0] = res.getString( "p_id" );
            data[i][1] = res.getString( "p_nombre" );
            data[i][2] = res.getString( "p_apellido" );
            data[i][3] = res.getString( "p_edad" );
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
        String q = " UPDATE persona SET " + valores + " WHERE p_id= " + id;
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
