/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package funcionBD;

import ConexionBaseDtos.ConfigMySql;
import jakarta.jms.Connection;
import ConexionBaseDtos.ConfigMySql;
import datos.Cliente;
import datos.Doctor;
import datos.Especialidad;
import datos.Horario;
import interfaces.InterfacesHorario;
import jakarta.jms.JMSException;
import jakarta.resource.cci.ResultSet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andri
 */
public class MetodosHorario implements interfaces.InterfacesHorario {

    Connection con = null;
    ConfigMySql cn = new ConfigMySql();
    java.sql.ResultSet resultado = null;
    PreparedStatement st = null;
    PreparedStatement ps;
    java.sql.ResultSet rs;

    @Override
    public void insertarHorario(int idPaciente, int idDoctor, int idEspecialidad, String fecha, String hora) {
        String sql = "INSERT INTO horarios (IDPaciente, IDDoctor, IDEspecialidad, Fecha, Hora) VALUES (?, ?, ?, STR_TO_DATE(?, '%Y-%m-%d %H:%i'), ?)";

        try {

            ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1, idPaciente);
            ps.setInt(2, idDoctor);
            ps.setInt(3, idEspecialidad);
            ps.setString(4, fecha);
            ps.setString(5, hora);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo apropiado de la excepción en tu aplicación
        }

    }

    @Override
    public boolean citaExistente(Date fechaHora, int idDoctor) {
        String sql = "SELECT COUNT(*) FROM horarios WHERE Fecha = ? AND Hora = ? AND IDdoctor = ?";

        try {
            ps = cn.conectar().prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(fechaHora.getTime()));
            ps.setTime(2, new java.sql.Time(fechaHora.getTime()));
            ps.setInt(3, idDoctor);

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // Retorna true si ya existe al menos una cita en la misma fecha, hora e IDdoctor
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public List<Horario> obtenerHorariosDoctor(int doctorID) {
        String sql = "SELECT U.Nombre, U.Apellidos, U.Identificacion, H.Fecha, H.Hora "
                + "FROM paciente U "
                + "INNER JOIN horarios H ON U.IDPaciente = H.IDPaciente "
                + "WHERE H.IDdoctor = ?";

        List<Horario> horarios = new ArrayList<>();

        try (PreparedStatement ps = cn.conectar().prepareStatement(sql)) {
            ps.setInt(1, doctorID);

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Horario horario = new Horario();
                    // Configurar el objeto Horario con los datos del ResultSet

                    horario.setCliente(new Cliente());
                    horario.getCliente().setNombre(rs.getString("Nombre"));

                    horario.getCliente().setApellidos(rs.getString("Apellidos"));
                    horario.getCliente().setIdentificacion(rs.getString("Identificacion"));
                    horario.setFecha(rs.getDate("Fecha"));
                    horario.setHora(rs.getTime("Hora"));

                    // Agregar el objeto Horario a la lista
                    horarios.add(horario);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones (lanzar o registrar, según sea necesario)
        }

        return horarios;
    }

    @Override
    public List<Horario> listHorario(int id) {
        List<Horario> horario = new ArrayList<>();
        String query = "select *from horarios   WHERE IDPaciente= ?";
        try (PreparedStatement ps = cn.conectar().prepareStatement(query)) {
            ps.setInt(1, id);

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Horario horarioC = new Horario();
                    // Configurar el objeto Horario con los datos del ResultSet

                    horarioC.setIDHorario(rs.getInt("IDHorario"));
                    horarioC.setCliente(new Cliente());
                    horarioC.getCliente().setIDPaciente(rs.getInt("IDPaciente"));
                    horarioC.setDoctor(new Doctor());
                    horarioC.getDoctor().setIDdoctor(rs.getInt("IDdoctor"));
                    horarioC.setEspecialidad(new Especialidad());
                    horarioC.getEspecialidad().setCodigo(rs.getInt("IDEspecialidad"));
                    horarioC.setFecha(rs.getDate("Fecha"));
                    horarioC.setHora(rs.getTime("Hora"));

                    horario.add(horarioC);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones (lanzar o registrar, según sea necesario)
        }

       

        return horario;

    }

}
