/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilesSecos;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import microsip2020.LineaComunicacion;

/**
 *
 * @author jorge pc
 */
public class PnlOrdenesVentas extends javax.swing.JPanel {

    private javax.swing.JPanel pnlNorte = null;
    private javax.swing.JPanel pnlCentral = null;
    private controlfechas.JDateSelector fecha = null;
    private javax.swing.JScrollPane jscPalnel = null;
    private javax.swing.JTable tablaProcesar = null;
    private javax.swing.JButton btnActualizar = null;
    private javax.swing.JButton btnValidar = null;
    private ArrayList<LineaOrdenDeVentas> arrLineaOrdenDeVentas = new ArrayList<LineaOrdenDeVentas>();
    private ModeloTx modelotx = null;
    private LineaComunicacion lineaComunicacion = null;
    private javax.swing.JButton btnProcesar = null;

    public PnlOrdenesVentas(LineaComunicacion lineaComunicacion) {
        setLineaComunicacion(lineaComunicacion);
        setLayout(new java.awt.BorderLayout());
        add(getpnlNorte(), java.awt.BorderLayout.NORTH);
        add(getjscPalnel(), java.awt.BorderLayout.CENTER);
    }

    public javax.swing.JButton getbtnProcesarMx() {
        if (btnProcesar == null) {
            btnProcesar = new javax.swing.JButton("Procesar..");
        }
        return btnProcesar;
    }

    public LineaComunicacion getLineaComunicacion() {
        return lineaComunicacion;
    }

    public void setLineaComunicacion(LineaComunicacion lineaComunicacion) {
        this.lineaComunicacion = lineaComunicacion;
    }

    public ModeloTx getmodelotx() {
        if (modelotx == null) {
            modelotx = new ModeloTx();
        }
        return modelotx;
    }

    public ArrayList<LineaOrdenDeVentas> getArrLineaOrdenDeVentas() {
        return arrLineaOrdenDeVentas;
    }

    public void setArrLineaOrdenDeVentas(ArrayList<LineaOrdenDeVentas> arrLineaOrdenDeVentas) {
        this.arrLineaOrdenDeVentas = arrLineaOrdenDeVentas;
    }

    public void actualizaCodigos() {
        setArrLineaOrdenDeVentas(getLineaComunicacion().getConex().getLineaVentas(getfecha().getlinuxFecha()));
        repinta();
    }
    
    public void repinta() {
        gettablaProcesar().repaint();
        getjscPalnel().setViewportView(gettablaProcesar());
        javax.swing.JScrollBar bar = getjscPalnel().getVerticalScrollBar();
        bar.setValue(bar.getMinimum());
    }

    class ModeloTx extends javax.swing.table.AbstractTableModel {

        java.util.ArrayList<String> col = new java.util.ArrayList<String>();

        public ModeloTx() {
            col.add("Id Firebird".toUpperCase());
            col.add("FECHA".toUpperCase());
            col.add("NOMBRE".toUpperCase());
            col.add("MONTO".toUpperCase());
            col.add("validado".toUpperCase());
            col.add("procesado".toUpperCase());
            col.add("id Netsuite".toUpperCase());
            col.add("Validado".toUpperCase());
            col.add("Procesado".toUpperCase());
            col.add("Nota".toUpperCase());

        }

        public String getColumnName(int column) {
            return col.get(column);
        }

        public int getRowCount() {
            return getArrLineaOrdenDeVentas().size();
        }

        public int getColumnCount() {
            return col.size();
        }

        public Object getValueAt(int row, int column) {
            Object value = null;
            try {
                LineaOrdenDeVentas linea = getArrLineaOrdenDeVentas().get(row);

                //PARA  NETSUITE
                switch (column) {
                    case 0:
                        value = linea.getDOCTO_PV_ID();
                        break;
                    case 1:
                        value = linea.getFecha() + " "+linea.getHora();
                        break;
                    case 2:
                        value = linea.getCliente();
                        break;
                    case 3:
                        value = linea.getMonto();
                        break;
                    case 4:
                        value = linea.isValidado();
                        break;
                    case 5:
                        value = linea.isProcesado();
                        break;
                    case 6:
                        value = linea.getIdInternoNetsuite();
                        break;
                    case 7:
                        value = linea.getValidadoText();
                        break;
                    case 8:
                        value = linea.getProcesadoText();
                        break;
                    case 9:
                        value = linea.getNota();
                        break;

                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            return value;
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }

    }

    public javax.swing.JTable gettablaProcesar() {
        if (tablaProcesar == null) {
            tablaProcesar = new javax.swing.JTable(getmodelotx());
        }
        return tablaProcesar;
    }

    public javax.swing.JScrollPane getjscPalnel() {
        if (jscPalnel == null) {
            jscPalnel = new javax.swing.JScrollPane(gettablaProcesar());
        }
        return jscPalnel;
    }

    public controlfechas.JDateSelector getfecha() {
        if (fecha == null) {
            fecha = new controlfechas.JDateSelector();
        }
        return fecha;
    }

    public javax.swing.JPanel getpnlNorte() {
        if (pnlNorte == null) {
            pnlNorte = new javax.swing.JPanel(new java.awt.FlowLayout());
            pnlNorte.add(new javax.swing.JLabel("Rango de Fechas"));
            pnlNorte.add(getfecha());
            pnlNorte.add(getbtnActualizar());
            pnlNorte.add(getbtnValidar());
            pnlNorte.add(getbtnProcesarMx());
        }
        return pnlNorte;
    }

    public javax.swing.JButton getbtnValidar() {
        if (btnValidar == null) {
            btnValidar = new javax.swing.JButton("Validar");
        }
        return btnValidar;
    }

    public javax.swing.JButton getbtnActualizar() {
        if (btnActualizar == null) {
            btnActualizar = new javax.swing.JButton("Actualizar");
            btnActualizar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actualizaCodigos();
                }
            });
        }
        return btnActualizar;
    }

}
