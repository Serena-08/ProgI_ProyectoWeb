package org.ies63.progI.dao;

import org.ies63.progI.entities.Auto;
import org.ies63.progI.entities.Marca;
import org.ies63.progI.interfaces.AdmConnexion;
import org.ies63.progI.interfaces.DAO;

import java.sql.*;
import java.util.List;

public class AutoImpl implements DAO<Auto,Integer>, AdmConnexion {
  private Connection conn= null;

  private static final String SQL_INSERT=
      "INSERT INTO autos (patente,color,anio,kilometraje,marca,modelo,idCliente, idSeguro) " +
          "VALUES            (      ?,        ?,    ?,   ?,        ?,      ?, ?,?)";


  private static  final String  SQL_UPDATE= "UPDATE autos SET " +
      "patente = ? , color = ? , anio = ? , kilometraje = ? " +
      " , marca = ? , modelo = ? " +
      "  WHERE idAuto = ? " ;

  private static  final String  SQL_DELETE= "DELETE FROM autos  WHERE idAuto = ? " ;
  private static  final String  SQL_GETALL= "SELECT * FROM autos ORDER BY patente" ;
  private  static final String  SQL_GETBYID= "SELECT * FROM autos WHERE idAuto = ? " ;

  @Override
  public List<Auto> getAll() {
    //1 conectar
    conn = obtenerConexion();

    //2  crear consulta SQL
    String sql = "SELECT * FROM autos order by patente";

    // 3 crear  statement y resulset
    PreparedStatement pst = null;
    ResultSet rs = null;

    List<Auto> listaAutos = new java.util.ArrayList<>();

    try {
      // paso 3 crear instruccion
      pst = conn.prepareStatement(SQL_GETALL);
      // paso 4 ejecutar consulta y guarda el resultado en resultset
      rs = pst.executeQuery();

      // paso 5 recorrer el resultset y guardar los autos en una lista
      while (rs.next()) {
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

      // paso 6 cerrar el resultset y statement
      rs.close();
      pst.close();
      conn.close();


    } catch (SQLException e) {
      System.out.println("Error al crear el statement");
      throw new RuntimeException(e);
    }


    return listaAutos;

  }

  @Override
  public void insert(Auto objeto) {
    // 1 establecer conexion
    Auto auto = objeto;
    conn = obtenerConexion();
    // establecer conexion a la base de datos

    ClienteImpl clienteImpl=new ClienteImpl();
    SeguroImpl seguroImpl=new SeguroImpl();
    boolean existeCliente=clienteImpl.existsById(auto.getCliente().getId());
    boolean existeSeguro=seguroImpl.existsById(auto.getSeguro().getIdSeguro());
    // solo guardo si existe el cliente y el seguro en la base de datos
    if( existeCliente && existeSeguro) {

      // paso 3 crear instruccion
      PreparedStatement pst = null;

      try {
        // con la conexion llamo al prepareStatement pasandole la consulta SQL
        pst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

        pst.setString(1, auto.getPatente());
        pst.setString(2, auto.getColor());
        pst.setInt(3, auto.getAnio());
        pst.setInt(4, auto.getKilometraje());
        pst.setString(5, auto.getMarca().toString());
        pst.setString(6, auto.getModelo());
        pst.setInt(7, auto.getCliente().getId());
        pst.setInt(8, auto.getSeguro().getIdSeguro());

        // paso 4 ejecutar instruccion
        // executeUpdate devuelve 1 si ejecuto correctamente 0 caso contrario
        int resultado = pst.executeUpdate();
        if (resultado == 1) {
          System.out.println("Auto insertado correctamente");
        } else {
          System.out.println("No se pudo insertar el auto");
        }

        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
          auto.setIdAuto(rs.getInt(1));
          System.out.println("El id asignado es: " + auto.getIdAuto());
        }

        // paso 5 cerrar conexion
        pst.close();
        conn.close();

      } catch (SQLException e) {
        throw new RuntimeException(e);
      }

    }
    else {
      System.out.println("No se puede insertar el auto. No existe el cliente con id: " + auto.getCliente().getId());
      System.out.println("No se puede insertar el seguro. No existe el seguro con id: " + auto.getSeguro().getIdSeguro());
    }
  }

  @Override
  public void update(Auto objeto) {
    conn=this.obtenerConexion();
    Auto auto= objeto;
    // solo si el auto existe lo modifico
    if (this.existsById(auto.getIdAuto())) {

      // Se crea un statement
      PreparedStatement pst = null;

      try {
        // ejecuto
        pst = conn.prepareStatement(SQL_UPDATE);

        pst.setString(1, auto.getPatente());
        pst.setString(2,auto.getColor());
        pst.setInt(3,auto.getAnio());
        pst.setInt(4,auto.getKilometraje());
        pst.setString(5,auto.getMarca().toString());
        pst.setString(6,auto.getModelo());
        pst.setInt(7,auto.getIdAuto());
        // paso 4 ejecutar instruccion
        // executeUpdate devuelve 1 si ejecuto correctamente 0 caso contrario
        int resultado = pst.executeUpdate();
        if (resultado == 1) {
          System.out.println("Auto actualizo correctamente");
        } else {
          System.out.println("No se pudo actualizar el auto");
        }

        pst.close();
        conn.close();

      } catch (SQLException e) {
        System.out.println("Error al crear el statement");
      }
    }

  }

  @Override
  public void delete(Integer id) {
    Connection conn = this.obtenerConexion();

    try {
      PreparedStatement pst = conn.prepareStatement(SQL_DELETE);
      pst.setInt(1,id);
      int resultado = pst.executeUpdate();
      if (resultado == 1) {
        System.out.println("Auto eliminado correctamente");
      } else {
        System.out.println("No se pudo eliminar el auto");
      }
      pst.close();
      conn.close();
    } catch (SQLException e) {
      System.out.println("No se pudo eliminar el auto. Error: " + e.getMessage());
    }

  }

  @Override
  public Auto getById(Integer id) {
    conn = obtenerConexion();
    // Se crea un statement
    PreparedStatement pst = null;
    ResultSet rs = null;
    boolean existe = false;
    Auto auto=null;


    try {
      pst = conn.prepareStatement(SQL_GETBYID); // CREO STATEMENT
      pst.setInt(1,id);
      rs = pst.executeQuery(); //EJECUTO CONSULTA
      // SI LA CONSULTA DEVUELVE AL MENOS UN REGISTRO, EXISTE
      if (rs.next()) {
        auto=new Auto();
        // asigno los datos a auto
        auto.setIdAuto(rs.getInt("idAuto"));
        auto.setPatente(rs.getString("patente"));
        auto.setColor(rs.getString("color"));
        auto.setMarca(Marca.valueOf( rs.getString("marca")));
        auto.setAnio(rs.getInt("anio"));
        auto.setKilometraje(rs.getInt("kilometraje"));
        auto.setModelo(rs.getString("modelo"));
      }

      // CIERRO RESULTSET Y STATEMENT
      rs.close();
      pst.close();
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return auto;
  }


  @Override
  public boolean existsById(Integer id) {
    conn = obtenerConexion();
    // Se crea un statement
    PreparedStatement pst = null;
    ResultSet rs = null;
    boolean existe = false;
    try {
      pst = conn.prepareStatement(SQL_GETBYID); // CREO STATEMENT
      pst.setInt(1,id);
      rs = pst.executeQuery(); //EJECUTO CONSULTA
      // SI LA CONSULTA DEVUELVE AL MENOS UN REGISTRO, EXISTE
      if (rs.next()) {
        existe = true;
      }
      // CIERRO RESULTSET Y STATEMENT
      rs.close();
      pst.close();
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return existe;
  }
}