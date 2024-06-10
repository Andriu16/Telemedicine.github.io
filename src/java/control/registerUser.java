/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control;

import datos.Cliente;
import datos.cargo;
import funcionBD.MetodosCliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author andri
 */
@WebServlet(name = "registerUser", urlPatterns = {"/registerUser"})
@MultipartConfig
public class registerUser extends HttpServlet {

    String inicio = "index.jsp";
    Cliente cl = new Cliente();
    cargo ca = new cargo();
    MetodosCliente dao = new MetodosCliente();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener parámetros del formulario
        int IDCargo = Integer.parseInt(request.getParameter("txtIDCargo"));
        String Nombre = request.getParameter("txtNombre");
        String Apellidos = request.getParameter("txtApellido");
        String Direccion = request.getParameter("txtDireccion");
        String Correo = request.getParameter("txtCorreo");
        String FechaNac = request.getParameter("txtFechaNac");
        String Identificacion = request.getParameter("txtIdentificacion");
        String Contrasena = request.getParameter("txtContrasena");
        String Genero = request.getParameter("txtGenero");
        int Numerotelf = Integer.parseInt(request.getParameter("txtTelf"));
        boolean estado = Boolean.parseBoolean(request.getParameter("txtEstado"));
        // Obtener otros parámetros del formulario

        // Crear un nuevo cliente y establecer sus atributos
        cl.setCargo(new cargo());
        cl.getCargo().setCodigo(IDCargo);
        cl.setNombre(Nombre);
        cl.setApellidos(Apellidos);
        cl.setDireccion(Direccion);
        cl.setCorreo(Correo);
        cl.setFechaNac(FechaNac);
        cl.setIdentificacion(Identificacion);
        cl.setContrasena(Contrasena);
        cl.setGenero(Genero);
        cl.setNumeroTelf(Numerotelf);
        cl.setEstado(estado);
        // Establecer otros atributos del cliente

        // Llamar al método add para insertar el nuevo cliente en la base de datos
        boolean insercionExitosa = dao.add(cl);

        if (insercionExitosa) {
            response.getWriter().println("Inserción exitosa");
            System.out.println("good");
            response.sendRedirect("index.jsp");
        } else {
            response.getWriter().println("Error al insertar el cliente");
            System.out.println("err de no inseto");
            response.sendRedirect("index.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
