/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilesSecos;

import java.awt.Font;

/**
 *
 * @author jorge
 */
public class PnlVerDetalles extends javax.swing.JFrame{
    private  String  mensaje =  "";
    private  javax.swing.JScrollPane  jscPanel =  null;
    private  javax.swing.JPanel  pnlSalida =  null;
    private  javax.swing.JButton btnSalida =  null;
    private  javax.swing.JTextArea txtArea =  null;
    
    public PnlVerDetalles(String  txt){
        gettxtArea().setText(txt);
        setLayout(new  java.awt.BorderLayout());
        add(getjscPanel(), java.awt.BorderLayout.CENTER);    
        add(getbtnSalida(), java.awt.BorderLayout.SOUTH);
    }
    
    public  javax.swing.JTextArea gettxtArea(){
        if(txtArea ==  null){
            txtArea =  new javax.swing.JTextArea();
            txtArea.setWrapStyleWord(true);
            txtArea.setLineWrap(true);
            txtArea.setFont(new java.awt.Font(Font.MONOSPACED, Font.PLAIN, 15) );
        }
        return txtArea;
    } 
    
    public  javax.swing.JButton getbtnSalida(){
        if(btnSalida ==  null){
            btnSalida =  new javax.swing.JButton("Salida");
        }
        return btnSalida;
    }
    
    public  javax.swing.JPanel  getpnlSalida(){
       if(pnlSalida==  null){
           pnlSalida = new javax.swing.JPanel();
           pnlSalida.add(getbtnSalida());
       }
       return pnlSalida;
    }
    
    public  javax.swing.JScrollPane  getjscPanel(){
        if(jscPanel ==  null){
            jscPanel =  new javax.swing.JScrollPane(gettxtArea());
        }
       return      jscPanel;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    
    
}
