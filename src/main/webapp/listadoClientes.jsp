<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>



<%@ include file="header.jsp" %>

<%@ page import="org.ies63.progI.dao.ClienteImpl" %>
<%@ page import="org.ies63.progI.entities.Cliente" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<!-- abro bloque de declaracion java -->
<%!
    ClienteImpl clienteDao = new ClienteImpl();
    Cliente cliente = new Cliente();
    List<Cliente> listaClientes = clienteDao.getAll();
 %>


<h2>Listado de Clientes</h2>
<table >
    <thead>
        <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Apellido</th>
        <th>Telefono</th>
        <th>Editar</th>
        <th>Borrar</th>
        </tr>
    </thead>
    <tbody>

    <% for(Cliente c : listaClientes) { %>
        <tr>
       <td> <%=c.getId() %> </td>
        <td><%=c.getNombre() %> </td>
        <td><%=c.getApellido() %> </td>
        <td><%=c.getTelefono() %> </td>
        <td><a href="formCliente.jsp?operacion=editar&id=<%=c.getId() %>">Editar </a></td>
         <td><a href="seCliente?operacion=eliminar&id=<%=c.getId() %>">Borrar</a></td>
        </tr>
    <% }  %>

    </tbody>
</table>