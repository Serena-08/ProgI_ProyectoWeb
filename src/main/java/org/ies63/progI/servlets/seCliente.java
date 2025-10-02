package org.ies63.progI.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ies63.progI.dao.ClienteImpl;
import org.ies63.progI.entities.Cliente;

import java.io.IOException;

public class seCliente extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    String operacion = "nuevo";
    String nombre = "";
    String apellido = "";
    String telefono = "";
    int id = -1;
    operacion = req.getParameter("operacion");

    if (operacion.equals("editar") || operacion.equals("nuevo")) {
      nombre = req.getParameter("txtNombre");
      apellido = req.getParameter("txtApellido");
      telefono = req.getParameter("txtTelefono");
      id = Integer.parseInt(req.getParameter("txtId"));
    } else
      id = Integer.parseInt(req.getParameter("id"));


    // para guardar el cliente
    ClienteImpl clienteDAO = new ClienteImpl();

    if (operacion.equals("nuevo")) {// es nuevo
      Cliente clienteNuevo = new Cliente(id, nombre, apellido, telefono);
      clienteDAO.insert(clienteNuevo);
    }
    if (operacion.equals("editar")) {// es editar
      Cliente clienteEditar = clienteDAO.getById(id);
      clienteEditar.setNombre(nombre);
      clienteEditar.setApellido(apellido);
      clienteEditar.setTelefono(telefono);
      clienteDAO.update(clienteEditar);
    }
    if (operacion.equals( "eliminar")) {
      clienteDAO.delete(id);
    }

    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
    rd.forward(req, res);
  }

}