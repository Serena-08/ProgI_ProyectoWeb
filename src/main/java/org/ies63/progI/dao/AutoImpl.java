package org.ies63.progI.dao;



import org.ies63.progI.configuracion.AdministradorConexion;
import org.ies63.progI.entities.Auto;
import org.ies63.progI.entities.Marca;
import org.ies63.progI.entities.Seguro;
import org.ies63.progI.interfaces.AdmConexion;
import org.ies63.progI.interfaces.DAO;

import java.sql.*;
import java.util.List;

public class AutoImpl implements DAO<Auto,Integer>,AdmConexion{
  private Connection conn=null;

  private static final String SQL_INSERT=
      "INSERT INTO autos (patente,color,anio,kilometraje,marca,modelo) " +
          "VALUES (?, ?, ?, ?, ?, ?)";


  private static final String  SQL_UPDATE =
      "UPDATE autos SET " +
          "patente = ?" +
          "color = ?" +
          "anio = ?" +
          "kilometraje = ?" +
          "marca = ?" +
          "modelo = ?" +
          "WHERE idAuto = ?";

  private static final String  SQL_DELETE = "DELETE FROM autos WHERE idAuto = ?";

  private static final String  SQL_GETALL = "SELECT * FROM autos ORDER BY patente";

  private static final String  SQL_GETBYID = "SELECT * FROM autos WHERE idAuto = ?";


  @Override
  public List<Auto> getAll() {

    //1 conectar
    conn = AdministradorConexion.obtenerConexion();

    //2 crear consulta SQL
    String sql= "SELECT * FROM autos order by patente";

    //3 crear statement y resultset
    Statement st= null;
    ResultSet rs= null;

    List<Auto> listaAutos = new java.util.ArrayList<>();
    try {
      //paso 3 crear instruccion
      st=conn.createStatement();
      //paso 4 ejecutar consulta y guarda el resultado en resultset
      rs = st.executeQuery(sql);



      //paso 5 recorrer el resultset y guardar los autos en una lista
      while(rs.next()){
        Auto auto = new Auto();
        auto.setIdAuto(rs.getInt("idAuto"));
        auto.setAnio(rs.getInt("anio"));
        auto.setPatente(rs.getString("patente"));
        auto.setColor(rs.getString("color"));
        auto.setKilometraje(rs.getInt("kilometraje"));
        auto.setMarca(Marca.valueOf(rs.getString("marca")));
        auto.setModelo(rs.getString("modelo"));

        listaAutos.add(auto);
      }
      //paso 6 cerrar el resultset y statement
      rs.close();
      st.close();
      conn.close();


    } catch (SQLException e) {
      System.out.println("Error al crear el statement");
      throw new RuntimeException(e);
    }


    return listaAutos;
  }

  @Override
  public void insert(Auto objeto) {
    Auto auto = objeto;
    conn = obtenerConexion();
       /* //1 establecer conexion a la base de datos

        //establecer conexion a la base de datos

        //paso 2 crear String consulta SQL
        String sql="INSERT INTO autos (idAuto,patente,color,anio,kilometraje,marca,modelo) " +
                "VALUES (" + auto.getIdAuto() + "," +
                "'" + auto.getPatente() + "'," +
                "'" + auto.getColor() + "'," +
                + auto.getAnio()+ "," +
                + auto.getKilometraje() + "," +
                "'" + auto.getMarca() + "'," +
                "'" + auto.getModelo() + "')" ;
        */


    //paso 3 crear instruccion
    PreparedStatement pst=null;
    try {
      //con la conexion llamo al prepareStatement pasandole la consulta SQL
      pst= conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

      pst.setString(1,auto.getPatente());
      pst.setString(2,auto.getColor());
      pst.setInt(3,auto.getAnio());
      pst.setInt(4,auto.getKilometraje());
      pst.setString(5,auto.getMarca().toString());
      pst.setString(6,auto.getModelo());

      //paso 4 ejecutar instruccion
      //executeUpdate devuelve 1 si ejecuto correctamente, 0 en caso contrario
      int resultado = pst.executeUpdate();
      if (resultado==1){
        System.out.println("Auto insertado correctamente");
      }else{
        System.out.println("No se pudo insertar el auto");
      }

      ResultSet rs = pst.getGeneratedKeys();
      if (rs.next()){
        auto.setIdAuto(rs.getInt(1));
        System.out.println("El id asignado es: " + auto.getIdAuto());
      }



      //paso 5 cerrar conexion
      pst.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }



  }

  @Override
  public void update(Auto objeto) {
    Auto auto = objeto;
    //Paso 1 establecer conexion


    //solo si el auto existe lo modifico
    if (this.existsById(auto.getIdAuto())){
      //
      String sql="UPDATE autos SET " +
          "patente = '" + auto.getPatente() + "', " +
          "color = '" + auto.getColor() + "', " +
          "anio = " + auto.getAnio() + ", " +
          "kilometraje = " + auto.getKilometraje() + ", " +
          "marca = '" + auto.getMarca() + "', " +
          "modelo = '" + auto.getModelo() + "' " +
          "WHERE idAuto = " + auto.getIdAuto();
      conn=AdministradorConexion.obtenerConexion();

      //Se crea el Statement
      Statement st = null;
      try {
        //Ejecuto
        st= conn.createStatement();//Creo el statement
        st.execute(sql);

        //Cierro
        st.close();
        conn.close();
      } catch (SQLException e) {
        System.out.println("Error al crear el statement");
        throw new RuntimeException(e);
      }

    }

  }

  @Override
  public void delete(Integer id) {
    int idauto = id;
    conn= AdministradorConexion.obtenerConexion();
    String sql= "DELETE FROM autos WHERE idAuto = " + idauto;
    Statement st = null;

    try {
      st= conn.createStatement();//creo el statement
      st.execute(sql);//ejecuto la consulta
      st.close();//cierro statement
      conn.close();//cierro conexion
    } catch (SQLException e) {
      System.out.println("Error al crear el statement");
      throw new RuntimeException(e);
    }
  }

  @Override
  public Seguro getById(Integer id) {
    //Establecer conexion
    conn=AdministradorConexion.obtenerConexion();
    String sql="SELECT * FROM autos WHERE idAuto= " + id;
    //Se crea un statement
    Statement st= null;
    ResultSet rs = null;
    Auto auto=new Auto();

    try {
      st=conn.createStatement(); //Creo Statement
      rs = st.executeQuery(sql); //Ejecuta consulta
      //Si la consulta devuelve al menos un registro, existe

      if (rs.next()) {
        //asigno los datos a auto
        auto.setIdAuto(rs.getInt("idAuto"));
        auto.setPatente(rs.getString("patente"));
        auto.setColor(rs.getString("color"));
        auto.setMarca(Marca.valueOf(rs.getString("marca")));
        auto.setAnio(rs.getInt("anio"));
        auto.setKilometraje(rs.getInt("kilometraje"));
        auto.setModelo(rs.getString("modelo"));

      }

      //Cierro resultset y statement
      rs.close();
      st.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);

    }


    return null;
  }

  @Override
  public boolean existsById(Integer id) {
    //establecer conexion
    conn=AdministradorConexion.obtenerConexion();
    String sql="SELECT * FROM autos WHERE idAuto= " + id;
    //Se crea un statement
    Statement st= null;
    ResultSet rs = null;
    boolean existe = false;

    try {
      st=conn.createStatement(); //Creo Statement
      rs = st.executeQuery(sql); //Ejecuta consulta
      //Si la consulta devuelve al menos un registro, existe
      if (rs.next()) {
        existe = true;
      }

      //Cierro resultset y statement
      rs.close();
      st.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return existe;
  }

  @Override
  public Connection obtenerConexion() {
    return AdmConexion.super.obtenerConexion();
  }




}
