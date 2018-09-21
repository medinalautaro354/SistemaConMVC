/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import vista.frmPrincipal;
import modelo.Modelo;
import vista.frmLogin;

/**
 *
 * @author lautalb
 */
public class Controlador implements ActionListener{

    private Modelo m;
    private frmPrincipal v;
    private frmLogin l;

    public Controlador(Modelo m, frmPrincipal v) {
        this.m = m;
        this.v = v;
        //para verificar que presionaron el boton
        this.v.btnGuardar.addActionListener(this);
        this.v.btnConsultar.addActionListener(this);
        this.v.btnModificar.addActionListener(this);
        this.v.btnEliminar.addActionListener(this);
        this.v.btnBuscar.addActionListener(this);
    }
    
    public void iniciar(){
        v.setTitle("Sistema MVC");
        v.pack();
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setLocationRelativeTo(null);
        v.setVisible(true);
        m.cargarMetodos();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        //verificamos si el boton guardar es presionados
        if(v.btnGuardar == e.getSource()){
            try {
                m.enviarDatos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo Enviar datos");
            }
        }
        else  if(v.btnConsultar == e.getSource()){
            try {
                m.Consultar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo consultar");
            }
        }
        else  if(v.btnModificar == e.getSource()){
            try {
                m.modificar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo modificar");
            }
        }
        else  if(v.btnEliminar == e.getSource()){
            try {
                m.eliminar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar");
            }
        }
        else  if(v.btnBuscar == e.getSource()){
            try {
                m.buscar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo buscar");
            }
        }
    }
}
