/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemamvc;
import controlador.Controlador;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import modelo.Modelo;
import vista.frmPrincipal;
/**
 *
 * @author lautalb
 */
public class SistemaMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //Para abrir el sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        Modelo m = new Modelo();
        frmPrincipal v = new frmPrincipal();
        Controlador c = new Controlador(m, v);
        c.iniciar();
    }
    
}
