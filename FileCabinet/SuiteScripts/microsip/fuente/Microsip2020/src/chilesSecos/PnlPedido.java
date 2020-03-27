/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilesSecos;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author jorge pc
 */
public class PnlPedido  extends JPanel{
    private PnlPedidosSuperio pnlSuperio =  null;
    private javax.swing.JScrollPane jscPanel =  null;
    private javax.swing.JTable tablaDatos =  null;
    private java.util.ArrayList<LineaDetalleVentas> arrOrdenes =  new java.util.ArrayList<LineaDetalleVentas>();
    private PnlTotales pnlTotal =  null;  
    private ModeloTx  modeloTx =  null;        
            
    public  PnlPedido(){
        setLayout(new java.awt.BorderLayout());
        add(getpnlSuperio(), java.awt.BorderLayout.NORTH);
        add(getjscPanel(), java.awt.BorderLayout.CENTER);
        add(getpnlTotal(), java.awt.BorderLayout.SOUTH);
    }

    public ModeloTx  getmodeloTx(){
        if(modeloTx ==  null){
            modeloTx =  new ModeloTx ();
        }
        return modeloTx;
    } 
    
    public PnlTotales getpnlTotal(){
        if(pnlTotal ==  null){
            pnlTotal =  new PnlTotales(); 
        }
        return pnlTotal;
    }        
            
    public ArrayList<LineaDetalleVentas> getArrOrdenes() {
        return arrOrdenes;
    }

    public void setArrOrdenes(ArrayList<LineaDetalleVentas> arrOrdenes) {
        this.arrOrdenes = arrOrdenes;
    }
    
    
    public javax.swing.JScrollPane getjscPanel(){
        if(jscPanel ==  null){
            jscPanel =  new javax.swing.JScrollPane(gettablaDatos()); 
        }
        return jscPanel;
    }
    
    public javax.swing.JTable gettablaDatos(){
        if(tablaDatos ==  null){
            tablaDatos =  new javax.swing.JTable(getmodeloTx()); 
            
        }
        return tablaDatos;
    }
    
    public void  actualiza(){
        gettablaDatos().repaint();
        getjscPanel().setViewportView(gettablaDatos());
        javax.swing.JScrollBar  bar =  getjscPanel().getVerticalScrollBar();
        bar.setValue(bar.getMinimum());
    }
    
    
    class ModeloTx extends javax.swing.table.AbstractTableModel {

        java.util.ArrayList<String> col = new java.util.ArrayList<String>();

        public ModeloTx() {
            col.add("QTY".toUpperCase());
            col.add("DESCRI".toUpperCase());
            col.add("MONTO".toUpperCase());
            col.add("Id Net".toUpperCase());
            col.add("Alarma".toUpperCase());
        }

        public String getColumnName(int column) {
            return col.get(column);
        }

        public int getRowCount() {
            return getArrOrdenes().size();
        }

        public int getColumnCount() {
            return col.size();
        }

        public Object getValueAt(int row, int column) {
            Object value = null;
            try {
                LineaDetalleVentas linea = getArrOrdenes().get(row);
                
                    //PARA  NETSUITE
                    switch (column) {
                        case 0:
                        value = linea.getQty();
                        break;    
                        case 1:
                        value = linea.getARTICULO();
                        break;   
                      case 2:
                        value = linea.getPRECIO_TOTAL_NETO();
                        break;
                        
                        case 3:
                        value = linea.getIdNetsuite();
                        break;
                        case 4:
                        value = linea.isAlarma();
                        break;
                    }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            return value;
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }

    }
    
    public PnlPedidosSuperio getpnlSuperio(){
        if(pnlSuperio ==  null){
            pnlSuperio =  new PnlPedidosSuperio(); 
        
        }
        return pnlSuperio;
    }
}
