package org.ies63.progI.dao;

import org.example.entities.Seguro;
import org.example.interfaces.AdmConexion;
import org.example.interfaces.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguroImpl implements AdmConexion, DAO<Seguro, Integer> {

  private Connection conn = null;

  private static String SQL_INSERT =
      "INSERT INTO seguros (tipo, costoMensual, compañia) " +
          "VALUES (?, ?, ?)";
  private static String SQL_UPDATE =
      "UPDATE seguros SET" +
          "tipo = ?," +
          "costoMensual = ?," +
          "compañia = ?" +
          "WHERE id = ?";
  private static String SQL_DELETE = "DELETE FROM seguros WHERE id = ?";
  private static String SQL_GETALL = "SELECT * FROM seguros ORDER BY tipo";
  private static String SQL_GETBYID = "SELECT * FROM seguros WHERE id = ?";
  private static String SQL_EXISTSBYID = "SELECT * FROM seguros WHERE id = ?";


  @Override
  public List<Seguro> getAll() {
    conn = obtenerConexion();

    PreparedStatement pst = null;
    ResultSet rs = null;

    List<Seguro> lista = new ArrayList<>();

    try {
      pst = conn.prepareStatement(SQL_GETALL);
      rs = pst.executeQuery();

      while (rs.next()) {
        Seguro seguro = new Seguro();
        seguro.setIdSeguro(rs.getInt("idSeguro"));
        seguro.setTipo(rs.getString("tipo"));
        seguro.setCostoMensual(rs.getDouble("costoMensual"));
        seguro.setCompañia(rs.getString("compañia"));

        lista.add(seguro);
      }

      pst.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      System.out.println("Error al crear el statement.");
      throw new RuntimeException(e);
    }
    return lista;
  }

  @Override
  public void insert(Seguro objeto) {
    conn = obtenerConexion();
    PreparedStatement pst = null;
    ResultSet rs = null;
    Seguro seguro = objeto;

    try {
      pst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

      pst.setString(1, seguro.getTipo());
      pst.setDouble(2, seguro.getCostoMensual());
      pst.setString(3, seguro.getCompañia());

      int resultado = pst.executeUpdate();
      if (resultado == 1) {
        System.out.println("Seguro agregado correctamente.");
      } else {
        System.out.println("No se pudo agregar el seguro.");
      }

      rs = pst.getGeneratedKeys();

      if (rs.next()) {
        seguro.setIdSeguro(rs.getInt(1));
        System.out.println("El id asignado es: " + seguro.getIdSeguro());
      }

      pst.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(Seguro objeto) {
    conn = obtenerConexion();
    if (this.existsById(objeto.getIdSeguro())) {
      try {
        PreparedStatement pst = conn.prepareStatement(SQL_UPDATE);

        pst.setString(1, objeto.getTipo());
        pst.setDouble(2, objeto.getCostoMensual());
        pst.setString(3, objeto.getCompañia());
        pst.setInt(4, objeto.getIdSeguro());

        int resultado = pst.executeUpdate();
        if (resultado == 1) {
          System.out.println("Seguro actualizado correctamente");
        } else {
          System.out.println("No se pudo actualizar el seguro");
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
    conn = obtenerConexion();
    try {
      PreparedStatement pst = conn.prepareStatement(SQL_DELETE);
      pst.setInt(1, id);
      int resultado = pst.executeUpdate();

      if (resultado == 1) {
        System.out.println("Seguro eliminado correctamente");
      } else {
        System.out.println("No se pudo eliminar el seguro");
      }

      pst.close();
      conn.close();
    } catch (SQLException e) {
      System.out.println("No se pudo eliminar el seguro. Error: " + e.getMessage());
    }
  }

  @Override
  public Seguro getById(Integer id) {
    conn = obtenerConexion();
    PreparedStatement pst = null;
    ResultSet rs = null;
    Seguro seguro = null;

    try {
      pst = conn.prepareStatement(SQL_EXISTSBYID);
      pst.setInt(1, id);
      rs = pst.executeQuery(); //ejecuto la consulta
      //Si la consulta devuelve al menos 1 regristo, existe
      if (rs.next()) {
        seguro = new Seguro();
        seguro.setIdSeguro(rs.getInt("idSeguro"));
        seguro.setTipo((rs.getString("tipo")));
        seguro.setCostoMensual(rs.getDouble("costoMensual"));
        seguro.setCompañia(rs.getString("compañia"));
      }

      pst.close();
      rs.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return seguro;
  }

  @Override
  public boolean existsById(Integer id) {
    conn = obtenerConexion();
    // Se crea un statement
    PreparedStatement pst = null;
    ResultSet rs = null;
    boolean existe = false;

    try {
      pst = conn.prepareStatement(SQL_GETBYID);
      pst.setInt(1, id);
      rs = pst.executeQuery(); // Ejecuto la consulta
      //Si la consulta devuelve al menos 1 regristo, existe
      if (rs.next()) {
        existe = true;
      }

      rs.close();
      pst.close();
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return existe;
  }


}