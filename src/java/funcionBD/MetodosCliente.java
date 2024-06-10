/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package funcionBD;

import ConexionBaseDtos.ConfigMySql;
import datos.Admin;
import datos.Cliente;
import datos.Doctor;
import datos.Especialidad;
import datos.cargo;
import interfaces.Usuario;
import interfaces.clienteInterfaz;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author andri
 */
public class MetodosCliente implements clienteInterfaz {

    ConfigMySql cn = new ConfigMySql();
    Connection con = null;
    ResultSet resultado = null;
    PreparedStatement st = null;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public List<Cliente> getPersonas() {
        List<Cliente> personas = new ArrayList<>();
        String sql = "SELECT U.IDPaciente,U.Nombre,U.Apellidos,U.Direccion,U.Correo,U.FechaNac,"
                + " U.Identificacion,U.Contrasena,U.Genero,U.NumeroTelf,C.IDCargo,C.Nombrecargo,"
                + " C.Estado FROM paciente U"
                + " INNER JOIN cargo C ON U.IDCargo=C.IDCargo";
        try {
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente p = new Cliente();
                p.setIDPaciente(rs.getInt(1));

                p.setNombre(rs.getString(2));
                p.setApellidos(rs.getString(3));
                p.setDireccion(rs.getString(4));
                p.setCorreo(rs.getString(5));
                p.setFechaNac(rs.getString(6));
                p.setIdentificacion(rs.getString(7));
                p.setContrasena(rs.getString(8));
                p.setGenero(rs.getString(9));
                p.setNumeroTelf(rs.getInt(10));

                p.setCargo(new cargo());
                p.getCargo().setCodigo(rs.getInt("IDCargo"));
                p.getCargo().setNombreCargo(rs.getString("Nombrecargo"));

                p.getCargo().setEstado(true);
                personas.add(p);

            }

        } catch (Exception e) {
            System.out.println("ERR" + e);
        }
        return personas;
    }

    @Override
    public boolean add(Cliente clie) {
        try (Connection conexion = ConfigMySql.conectar()) {

            // Query de inserción
            String query = "INSERT INTO paciente (IDCargo, Nombre, Apellidos, Direccion, Correo, FechaNac, Identificacion, Contrasena, Genero, NumeroTelf, Estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Crear la declaración preparada
            try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {

                // Setear los valores para la inserción
                preparedStatement.setInt(1, clie.getCargo().getCodigo());
                preparedStatement.setString(2, clie.getNombre());
                preparedStatement.setString(3, clie.getApellidos());
                preparedStatement.setString(4, clie.getDireccion());
                preparedStatement.setString(5, clie.getCorreo());
                preparedStatement.setString(6, clie.getFechaNac());
                preparedStatement.setString(7, clie.getIdentificacion());
                preparedStatement.setString(8, clie.getContrasena());
                preparedStatement.setString(9, clie.getGenero());
                preparedStatement.setInt(10, clie.getNumeroTelf());
                preparedStatement.setBoolean(11, clie.isEstado());

                // Ejecutar la inserción
                int filasAfectadas = preparedStatement.executeUpdate();

                // Verificar si la inserción fue exitosa
                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean buscarCorreoRepetido(String Correo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object identificar(Usuario usuario) {
        Object objetoUsuario = null;
        Connection con = null;
        ResultSet resultado = null;
        PreparedStatement st = null;

        try {
            String consulta = "SELECT U.IDPaciente, C.Nombrecargo FROM paciente U INNER JOIN cargo C ON U.IDCargo=C.IDCargo "
                    + "WHERE U.Correo=? AND U.Contrasena=? "
                    + "UNION "
                    + "SELECT J.IDadmin, H.Nombrecargo FROM admin J INNER JOIN cargo H ON J.IDCargo=H.IDCargo "
                    + "WHERE J.Correo=? AND J.Contrasena=? "
                    + "UNION "
                    + "SELECT F.IDdoctor, K.Nombrecargo FROM doctor F INNER JOIN cargo K ON F.IDCargo=K.IDCargo "
                    + "WHERE F.Correo=? AND F.Contrasena=?";

            con = cn.conectar();
            st = con.prepareStatement(consulta);

            st.setString(1, usuario.getCorreo());
            st.setString(2, usuario.getContrasena());
            st.setString(3, usuario.getCorreo());
            st.setString(4, usuario.getContrasena());
            st.setString(5, usuario.getCorreo());
            st.setString(6, usuario.getContrasena());

            resultado = st.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt(1);  // La columna que contiene el ID
                String nombreCargo = resultado.getString(2);

                switch (nombreCargo) {
                    case "Paciente":
                        Cliente paciente = new Cliente();
                        paciente.setIDPaciente(id);
                        paciente.setCorreo(usuario.getCorreo());
                        paciente.setCargo(new cargo());
                        paciente.getCargo().setNombreCargo(nombreCargo);
                        paciente.setEstado(true);
                        usuario = paciente;
                        break;

                    case "Admin":
                        Admin admin = new Admin();
                        admin.setIDadmin(id);
                        admin.setCorreo(usuario.getCorreo());
                        admin.setCargo(new cargo());
                        admin.getCargo().setNombreCargo(nombreCargo);
                        admin.setEstado(true);
                        usuario = admin;
                        break;

                    case "Doctor":
                        Doctor doctor = new Doctor();
                        doctor.setIDdoctor(id);
                        doctor.setCorreo(usuario.getCorreo());
                        doctor.setCargo(new cargo());
                        doctor.getCargo().setNombreCargo(nombreCargo);
                        doctor.setEstado(true);
                        usuario = doctor;
                        break;

                    default:
                        // Manejar otro tipo de cargo si es necesario
                        break;
                }
            }

        } catch (SQLException ex) {
            System.out.println("ERROR" + ex.getMessage());
        } finally {
            // ... (código de cierre de recursos)
        }

        return usuario;
    }

    @Override
    public boolean add(Doctor doc) {
        String sql = "INSERT INTO doctor (IDCargo, Nombre, Apellidos, Direccion, Correo, FechaNac, Identificacion, Contrasena, Genero, NumeroTelf, IDEspecialidad, img) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = cn.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Convertir los códigos a String si es necesario
            String codigoCargo = String.valueOf(doc.getCargo().getCodigo());
            String codigoEspecialidad = String.valueOf(doc.getEspecialidad().getCodigo());

            ps.setString(1, codigoCargo);
            ps.setString(2, doc.getNombre());
            ps.setString(3, doc.getApellidos());
            ps.setString(4, doc.getDireccion());
            ps.setString(5, doc.getCorreo());
            ps.setString(6, doc.getFechaNac());
            ps.setString(7, doc.getIdentificacion());
            ps.setString(8, doc.getContrasena());
            ps.setString(9, doc.getGenero());
            ps.setInt(10, doc.getNumeroTelf());

            ps.setString(11, codigoEspecialidad);
            ps.setBlob(12, doc.getImagen());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar doctor: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Doctor> getDoctores() {
        List<Doctor> doctores = new ArrayList<>();
        String sql = "SELECT U.IDdoctor,U.Nombre,U.Apellidos,U.Direccion,U.Correo,U.FechaNac,U.Identificacion,U.Contrasena,U.Genero,U.NumeroTelf,U.IDEspecialidad,J.Nombre,U.img,C.IDCargo,C.Nombrecargo,C.Estado FROM doctor U INNER JOIN cargo C ON U.IDCargo=C.IDCargo INNER JOIN especialidad J ON U.IDEspecialidad=J.IDEspecialidad";
        try {
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Doctor p = new Doctor();
                p.setIDdoctor(rs.getInt(1));

                p.setNombre(rs.getString(2));
                p.setApellidos(rs.getString(3));
                p.setDireccion(rs.getString(4));
                p.setCorreo(rs.getString(5));
                p.setFechaNac(rs.getString(6));
                p.setIdentificacion(rs.getString(7));
                p.setContrasena(rs.getString(8));
                p.setGenero(rs.getString(9));
                p.setNumeroTelf(rs.getInt(10));
                p.setEspecialidad(new Especialidad());
                p.getEspecialidad().setCodigo(rs.getInt(11));
                p.getEspecialidad().setNombreEspecialidad(rs.getString(12));
                p.setImagen(rs.getBinaryStream(13));
                p.setCargo(new cargo());
                p.getCargo().setCodigo(rs.getInt(14));
                p.getCargo().setNombreCargo(rs.getString(15));
                p.getCargo().setEstado(true);

                doctores.add(p);

            }

        } catch (Exception e) {
            System.out.println("ERR" + e);
        }
        return doctores;

    }

    @Override
    public void listarIMG(int id, HttpServletResponse response) {
        String sql = "SELECT * FROM doctor WHERE IDdoctor=" + id;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            outputStream = response.getOutputStream();

            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                inputStream = rs.getBinaryStream("img");
            }
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);

            int i = 0;
            while ((i = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(i);

            }
        } catch (Exception e) {
        }

    }

    @Override
    public List<Cliente> list(int id) {
         List<Cliente> cliente = new ArrayList<>();
         String query = "SELECT * FROM paciente WHERE IDPaciente= ?";
         try (Connection con = ConfigMySql.conectar(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cliente doctor = new Cliente();
                    // Configura los atributos del doctor según tu esquema
                    doctor.setIDPaciente(rs.getInt("IDPaciente"));
                    doctor.setCargo(new cargo());
                    doctor.getCargo().setCodigo(rs.getInt("IDCargo"));
                    doctor.setNombre(rs.getString("Nombre"));
                    doctor.setApellidos(rs.getString("Apellidos"));
                    doctor.setDireccion(rs.getString("Direccion"));
                    doctor.setCorreo(rs.getString("Correo"));
                    doctor.setFechaNac(rs.getString("FechaNac"));
                    doctor.setIdentificacion(rs.getString("Identificacion"));
                    doctor.setContrasena(rs.getString("Contrasena"));

                    doctor.setGenero(rs.getString("Genero"));
                    doctor.setNumeroTelf(rs.getInt("NumeroTelf"));
                    doctor.setEstado(rs.getBoolean("Estado"));

                    cliente.add(doctor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Maneja la excepción de manera apropiada en tu aplicación
        }

        return cliente;
    }

}
