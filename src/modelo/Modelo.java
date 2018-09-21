/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.swing.table.DefaultTableModel;
import controlador.Controlador;
import java.util.Calendar;
import vista.frmPrincipal;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author lautalb
 */
public class Modelo {

    DefaultTableModel modelo;
    frmPrincipal frm;
    Controlador c;
    Connection cc;
    Connection cn = conexion();

    public Modelo() {

    }

    public Connection conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cc = DriverManager.getConnection("jdbc:mysql://localhost/sistemamvc", "root", "");
            System.out.println("exito");
        } catch (Exception e) {
            System.out.println(e);
        }

        return cc;
    }

    public void buscar() {
        if (frm.txtId.equals("")) {
            JOptionPane.showMessageDialog(null, "Coloque el id que quiere buscar");
        } else {
            mostrarTabla(frm.txtId.getText());
        }

    }

    public void Consultar() {
        int fila = frm.tbPrincipal.getSelectedRow();

        if (fila >= 0) {
            frm.txtId.setText(frm.tbPrincipal.getValueAt(fila, 0).toString());
            frm.txtNombre.setText(frm.tbPrincipal.getValueAt(fila, 1).toString());
            frm.txtApellido.setText(frm.tbPrincipal.getValueAt(fila, 2).toString());
            frm.txtEdad.setText(frm.tbPrincipal.getValueAt(fila, 3).toString());
            frm.cbSexo.setSelectedItem(frm.tbPrincipal.getValueAt(fila, 4).toString());
            frm.txtFecha.setText(frm.tbPrincipal.getValueAt(fila, 5).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }

    }

    public void eliminar() {
        int fila = frm.tbPrincipal.getSelectedRow();

        if (fila >= 0) {
            try {
                String id = frm.tbPrincipal.getValueAt(fila, 0).toString();
                PreparedStatement ppt = cn.prepareStatement("DELETE FROM usuario WHERE id = '" + id + "' ");

                ppt.executeUpdate();
                mostrarTabla("");
                JOptionPane.showMessageDialog(null, "Dato eliminado");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Dato no eliminado");
                System.out.println(e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }

    public void modificar() {
        String id = frm.txtId.getText();

        if (frm.txtNombre.getText().equals("") || frm.txtApellido.getText().equals("") || frm.txtEdad.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar los campos");
        } else {
            try {
                PreparedStatement ppt = cn.prepareStatement("UPDATE usuario SET "
                        + "nombre = '" + frm.txtNombre.getText() + "',"
                        + "apellido = '" + frm.txtApellido.getText() + "',"
                        + "edad = '" + frm.txtEdad.getText() + "',"
                        + "sexo = '" + frm.cbSexo.getSelectedItem() + "',"
                        + "fecha = '" + frm.txtFecha.getText() + "' "
                        + "WHERE id = '" + id + "'");

                ppt.executeUpdate();
                mostrarTabla("");
                JOptionPane.showMessageDialog(null, "Datos Modificados");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Datos no modificados!");
                System.out.println(e);
            }
        }

    }

    public void cargarMetodos() {

        this.mostrarTabla("");
    }

    public void mostrarTabla(String valor) {
        modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Edad");
        modelo.addColumn("Sexo");
        modelo.addColumn("Fecha");
        //llamo al metodo de la tabla en el frmPrincipal
        frm.tbPrincipal.setModel(modelo);

        frm.cbSexo.removeAllItems();
        frm.cbSexo.addItem("Masculino");
        frm.cbSexo.addItem("Femenino");

        String sql;
        if (valor.equals("")) {
            sql = "SELECT * FROM usuario";
        } else {
            sql = "SELECT * FROM usuario WHERE id = '" + valor + "'";
        }

        String datos[] = new String[6];

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                modelo.addRow(datos);
            }
            frm.tbPrincipal.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo cargar la tabla");
            System.out.println(e);
        }
    }

    public void enviarDatos() {
        String dia = Integer.toString(frm.dcFecha.getCalendar().get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(frm.dcFecha.getCalendar().get(Calendar.MONTH) + 1);
        String year = Integer.toString(frm.dcFecha.getCalendar().get(Calendar.YEAR));
        String fecha = (dia + "/" + mes + "/" + year);

        if (frm.txtNombre.getText().equals("") || frm.txtApellido.getText().equals("") || frm.txtEdad.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar los campos");
        } else {
            try {

                PreparedStatement ppt = cn.prepareStatement("INSERT INTO usuario (nombre, apellido, edad, sexo, fecha)"
                        + "VALUES(?,?,?,?,?)");
                ppt.setString(1, frm.txtNombre.getText());
                ppt.setString(2, frm.txtApellido.getText());
                ppt.setString(3, frm.txtEdad.getText());
                ppt.setString(4, frm.cbSexo.getSelectedItem().toString());
                ppt.setString(5, fecha);
                ppt.executeUpdate();
                this.mostrarTabla("");
                JOptionPane.showMessageDialog(null, "Datos guardados con exito!");

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Datos no guardados");
                System.out.println(e);
            }
        }

    }
}
