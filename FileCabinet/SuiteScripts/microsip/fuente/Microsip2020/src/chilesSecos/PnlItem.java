/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilesSecos;

import restletsmx.RestLet_Service;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
public class PnlItem extends javax.swing.JPanel {

    private javax.swing.JButton btnActualizar = null;
    private javax.swing.JPanel pnlArriba = null;
    private javax.swing.JTable tablaDatos = null;
    private javax.swing.JScrollPane jscScroll = null;
    private java.util.ArrayList<LineaItemNetsuite> arrItem = new java.util.ArrayList<LineaItemNetsuite>();
    private LineaComunicacion lineaComunicacion = null;
    private ModeloTx modelo = null;
    private char esp = 'A';
    private javax.swing.JLabel lbEsp = new javax.swing.JLabel();
    private javax.swing.JTextField txtBuscar = null;
    private boolean netsuite = false;

    public PnlItem(LineaComunicacion lineaComunicacion, char esp, boolean netsuite) {
        setNetsuite(netsuite);
        setEsp(esp);
        if (netsuite) {
            lbEsp.setText("BUSCA EL ARTICULO IGUAL ");
        } else {
            lbEsp.setText("CUALQUIER PARTE DE PALABRA ");
        }
        setLineaComunicacion(lineaComunicacion);
        setLayout(new java.awt.BorderLayout());
        add(getpnlArriba(), java.awt.BorderLayout.NORTH);
        add(getjscScroll(), java.awt.BorderLayout.CENTER);
    }

    public boolean isNetsuite() {
        return netsuite;
    }

    public void setNetsuite(boolean netsuite) {
        this.netsuite = netsuite;
    }

    public javax.swing.JTextField gettxtBuscar() {
        if (txtBuscar == null) {
            txtBuscar = new javax.swing.JTextField(10);
            txtBuscar.addKeyListener(new java.awt.event.KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                public void keyPressed(KeyEvent e) {
                }

                public void keyReleased(KeyEvent e) {
                    actualizaGeneral();
                }
            });
        }
        return txtBuscar;
    }

    public char getEsp() {
        return esp;
    }

    public void setEsp(char esp) {
        this.esp = esp;
    }

    public void actualizaFireBird() {
        setArrItem(getLineaComunicacion().getConex().getAllArticulos(gettxtBuscar().getText()));
    }

    public void actualizaGeneral() {
        if (isNetsuite()) {
            setArrItem(getLineaComunicacion().getDbo2().buscaArticuloGeneral(gettxtBuscar().getText()));
        } else {
            actualizaFireBird();
        }
        actualiza();

    }

    public void actualizaNetsuite() {
        try {
            if(getLineaComunicacion().getConex().getConnection() == null){
                Herramientas.mensageError(":( ");
            }
            JSONObject jsonTx = new JSONObject();
            jsonTx.accumulate("accion", "item");
            String res = getLineaComunicacion().getRest().procesa(jsonTx);
            System.out.println("->" + res);
            JSONObject jsonTxRES = new JSONObject(res);
            System.out.println(jsonTxRES.toString());
            JSONArray arrResuJson = jsonTxRES.getJSONArray("resultados");
            java.util.ArrayList<LineaItemNetsuite> arrLista = new java.util.ArrayList<LineaItemNetsuite>();
            getLineaComunicacion().getDbo2().borraArticulos(1);
            for (int i = 0; i < arrResuJson.length(); i++) {
                LineaItemNetsuite linea = new LineaItemNetsuite();
                JSONObject lineJson = arrResuJson.getJSONObject(i);
                int idInter = lineJson.getInt("internalid");
                linea.setIdNetsuite(idInter);
                try {
                    linea.setNombre(lineJson.getString("displayname").trim());
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
                linea.setNetsuite(true);
                linea.setOrigen(1);
                try {
                    linea.setPrecio(Float.parseFloat(lineJson.getString("baseprice")));
                } catch (Exception ee) {
                    // ee.printStackTrace();
                }
                try {
                    linea = getLineaComunicacion().getConex().controArticulos(linea);
                    if(linea != null){
                        getLineaComunicacion().getDbo2().graba(linea);
                        arrLista.add(linea);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            actualizaGeneral();
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

    public ArrayList<LineaItemNetsuite> getArrItem() {
        return arrItem;
    }

    public void setArrItem(ArrayList<LineaItemNetsuite> arrItem) {
        this.arrItem = arrItem;
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
            if (getEsp() == 'A') {
                col.add("Id Netsuite".toUpperCase());
            } else {
                col.add("Id firebird".toUpperCase());
            }
            col.add("Nombre".toUpperCase());
            col.add("Precio".toUpperCase());
            col.add("Id ARTICULOS".toUpperCase());
        }

        public String getColumnName(int column) {
            return col.get(column);
        }

        public int getRowCount() {
            return getArrItem().size();
        }

        public int getColumnCount() {
            return col.size();
        }

        public Object getValueAt(int row, int column) {
            Object value = null;
            try {
                LineaItemNetsuite linea = getArrItem().get(row);
                switch (column) {
                    case 0:
                        if (isNetsuite()) {
                            value = linea.getIdNetsuite();
                        } else {
                            value = linea.getARTICULO_ID();
                        }

                        break;
                    case 1:
                        value = Herramientas.covTF8(linea.getNombre());
                        break;
                    case 2:
                        value = linea.getPrecio();
                        break;
                    case 3:
                        value = linea.getARTICULO_ID();
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
            pnlArriba.add(new javax.swing.JLabel("Buscar"));
            pnlArriba.add(gettxtBuscar());
            pnlArriba.add(getbtnActualizar());
        }
        return pnlArriba;
    }

    public javax.swing.JButton getbtnActualizar() {
        if (btnActualizar == null) {
            btnActualizar = new javax.swing.JButton("Actualizar");
            btnActualizar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actualizaGeneral();
                }
            });
        }
        return btnActualizar;
    }

}
