/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilesSecos;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import restletsmx.RestLet_Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import microsip2020.Dbo4;
import microsip2020.Herramientas;
import microsip2020.LineaComunicacion;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jorge pc
 */
public class PnlClientes extends javax.swing.JPanel {

    private javax.swing.JButton btnActualizar = null;
    private javax.swing.JPanel pnlArriba = null;
    private javax.swing.JTable tablaDatos = null;
    private javax.swing.JScrollPane jscScroll = null;
    private java.util.ArrayList<LineaClientes> arrClientes = new java.util.ArrayList<LineaClientes>();
    private LineaComunicacion lineaComunicacion = null;
    private ModeloTx modelo = null;
    private javax.swing.JLabel lbEsp = new javax.swing.JLabel();
    private boolean netsuite = false;
    private javax.swing.JTextField txtBuscaCliente = null;

    public PnlClientes(LineaComunicacion lineaComunicacion, boolean netsuite) {
        setNetsuite(netsuite);
        if (netsuite) {
            lbEsp.setText("Netsuite");
        } else {
            lbEsp.setText("Microsip");

        }
        setLineaComunicacion(lineaComunicacion);
        setLayout(new java.awt.BorderLayout());
        add(getpnlArriba(), java.awt.BorderLayout.NORTH);
        add(getjscScroll(), java.awt.BorderLayout.CENTER);
    }

    public javax.swing.JTextField gettxtBuscaCliente() {
        if (txtBuscaCliente == null) {
            txtBuscaCliente = new javax.swing.JTextField("");
            txtBuscaCliente.setColumns(10);
            txtBuscaCliente.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (isNetsuite() == false) {
                        actualizaFireBird();
                    } else {
                        
                    }

                }
            });
        }
        return txtBuscaCliente;
    }

    public boolean isNetsuite() {
        return netsuite;
    }

    public void setNetsuite(boolean netsuite) {
        this.netsuite = netsuite;
    }

    public void actualizaFireBird() {
        setArrClientes(getLineaComunicacion().getConex().getAllClientes(gettxtBuscaCliente().getText()));
        actualiza();
    }

    public void actualizaNetsuite() {
        try {
            int  numErrores = 0;
            getLineaComunicacion().getDbo2().borraClientes();
            JSONObject jsonTx = new JSONObject();
            jsonTx.accumulate("accion", "customer");
            String res = getLineaComunicacion().getRest().procesa(jsonTx);
            System.out.println("->" + res);
            JSONObject jsonTxRES = new JSONObject(res);
            System.out.println(jsonTxRES.toString());
            JSONArray arrResuJson = jsonTxRES.getJSONArray("resultados");
            java.util.ArrayList<LineaClientes> arrLista = new java.util.ArrayList<LineaClientes>();

            for (int i = 0; i < arrResuJson.length(); i++) {
                LineaClientes linea = new LineaClientes();
                JSONObject lineJson = arrResuJson.getJSONObject(i);
                linea.setIdinternoNetsuite(Integer.parseInt(lineJson.getString("internalid")));
                try {
                    linea.setNOMBRE(lineJson.getString("companyname"));
                } catch (Exception ee) {
                    System.out.println("--->" + lineJson.getString("companyname"));
                    ee.printStackTrace();
                }
                linea= getLineaComunicacion().getConex().validaCliente(linea);
                if(linea.getCLIENTE_ID() > 0){
                    arrLista.add(linea);
                    getLineaComunicacion().getDbo2().graba(linea);
                }else{
                    numErrores++;
                }
            }
            if(numErrores > 0 ){
                Herramientas.mensageError("Se  presentaron errores "+ numErrores);
            }
            setArrClientes(arrLista);
            actualiza();
        } catch (IOException ex) {
            Logger.getLogger(PnlItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        System.out.println("Compilado");
    }

    public void actualiza() {
        gettablaDatos().repaint();
        getjscScroll().setViewportView(gettablaDatos());
        javax.swing.JScrollBar bar = getjscScroll().getVerticalScrollBar();
        bar.setValue(bar.getMinimum());
    }

    public ModeloTx getmodelo() {
        if (modelo == null) {
            modelo = new ModeloTx();
        }
        return modelo;
    }

    public LineaComunicacion getLineaComunicacion() {
        return lineaComunicacion;
    }

    public void setLineaComunicacion(LineaComunicacion lineaComunicacion) {
        this.lineaComunicacion = lineaComunicacion;
    }

    public ArrayList<LineaClientes> getArrClientes() {
        return arrClientes;
    }

    public void setArrClientes(ArrayList<LineaClientes> arrClientes) {
        this.arrClientes = arrClientes;
    }

    public javax.swing.JScrollPane getjscScroll() {
        if (jscScroll == null) {
            jscScroll = new javax.swing.JScrollPane(gettablaDatos());
        }
        return jscScroll;
    }

    public javax.swing.JTable gettablaDatos() {
        if (tablaDatos == null) {
            tablaDatos = new javax.swing.JTable(getmodelo());
        }
        return tablaDatos;
    }

    class ModeloTx extends javax.swing.table.AbstractTableModel {

        java.util.ArrayList<String> col = new java.util.ArrayList<String>();

        public ModeloTx() {
            col.add("Id".toUpperCase());
            col.add("NOMBRE".toUpperCase());
            if(isNetsuite()){
                col.add("CLIENTE_ID".toUpperCase());
            }
        }

        public String getColumnName(int column) {
            return col.get(column);
        }

        public int getRowCount() {
            return getArrClientes().size();
        }

        public int getColumnCount() {
            return col.size();
        }

        public Object getValueAt(int row, int column) {
            Object value = null;
            try {
                LineaClientes linea = getArrClientes().get(row);

                //PARA  FIREBRID
                switch (column) {
                    case 0:
                        value = linea.getIdinterno();
                        break;
                    case 1:
                        if(isNetsuite()){
                            value = linea.getNOMBRE();
                        }else{
                            value = Herramientas.covTF8(linea.getNOMBRE());
                        }
                        break;
                     case 2:
                        value = linea.getCLIENTE_ID();
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

    public javax.swing.JPanel getpnlArriba() {
        if (pnlArriba == null) {
            pnlArriba = new javax.swing.JPanel();
            pnlArriba.add(lbEsp);
            pnlArriba.add(gettxtBuscaCliente());
            pnlArriba.add(getbtnActualizar());
        }
        return pnlArriba;
    }

    public javax.swing.JButton getbtnActualizar() {
        if (btnActualizar == null) {
            btnActualizar = new javax.swing.JButton("Actualizar");
            btnActualizar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(isNetsuite()){
                        actualizaNetsuite();
                    }else{
                        actualizaFireBird();
                    }
                
                  }
            });
        }
        return btnActualizar;
    }

}
