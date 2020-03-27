/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilesSecos;

import Restlet.Base64Trans;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import microsip2020.Coneccion;
import microsip2020.Dbo4;
import microsip2020.Herramientas;
import microsip2020.LineaComunicacion;
import org.json.JSONObject;
import restletsmx.LineaSecret;
import restletsmx.RestLet_Service;

/**
 *
 * @author jorge pc
 */
public class Consola extends javax.swing.JFrame {

    private javax.swing.JTabbedPane tabProcesos = null;
    private javax.swing.JPanel pnlMaster = null;
    private PnlItem pnlItem = null;
    private PnlItem pnlItemCompurativo = null;
    private javax.swing.JPanel pnlAbajo = null;
    private javax.swing.JButton btnIniciaProceso = null;
    private javax.swing.JPanel pnlSetup = null;
    private javax.swing.JPanel pnlBotones = null;
    private LineaComunicacion lineaComunicacion = null;
    private PnlClientes pnlClienteNetsuite = null;
    private PnlClientes pnlClienteFirebird = null;
    private javax.swing.JLabel lbProceso = null;
    private PnlOrdenesVentas pnlOrdenesVentas = null;
    private javax.swing.JPanel pnlCardPedido = null;

    public Consola(LineaComunicacion lineaComunicacion, boolean test) throws HeadlessException {
        setLineaComunicacion(lineaComunicacion);
        setLayout(new java.awt.BorderLayout());
        add(getpnlCardPedido(), java.awt.BorderLayout.CENTER);
        add(getpnlAbajo(), java.awt.BorderLayout.SOUTH);
        addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                cierra();
            }

            public void windowClosed(WindowEvent e) {
                cierra();
            }
        });
        if (test) {
            getbtnIniciaProceso().doClick();
        }
    }

    public javax.swing.JPanel getpnlCardPedido() {
        if (pnlCardPedido == null) {
            pnlCardPedido = new javax.swing.JPanel(new java.awt.CardLayout());
            pnlCardPedido.add(getpnlMaster(), "main");
            pnlCardPedido.add(getpnlPedido(), "detalle");
        }
        return pnlCardPedido;
    }
    private PnlPedido pnlPedido = null;

    public static void main(String[] args) {
        try {
            LineaComunicacion lineaComunicacion = new LineaComunicacion();
            Dbo4 dbo4 = new Dbo4();
            lineaComunicacion.setDbo2(dbo4);
            LineaSecret lineaSct = dbo4.parametosGenerales(0);
            RestLet_Service rest = new RestLet_Service(lineaSct);
            lineaComunicacion.setRest(rest);
            Coneccion conx = new Coneccion();
            conx.conectar();
            lineaComunicacion.setConex(conx);

            Consola con = new Consola(lineaComunicacion, false);
            con.valicacion(141);

            lineaComunicacion.getDbo2().closeConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Consola.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Consola.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LineaOrdenDeVentas valicacion(int DOCTO_PV_ID) {
        LineaOrdenDeVentas lineaMaster = getLineaComunicacion().getConex().getLineaVentas2(DOCTO_PV_ID);
        String cliente = lineaMaster.getCliente();
        LineaClientes lineaCliens = getLineaComunicacion().getDbo2().buscaCliente(cliente);
        if (lineaCliens != null) {
            System.out.println("Cliente " + lineaCliens.getIdinterno());
            lineaMaster.setId_ClienteNet(lineaCliens.getIdinterno());
            lineaMaster.setNota("Cliente " + cliente + "\n");
        }
        for (int i = 0; i < lineaMaster.getArrDetalleVentas().size(); i++) {
            LineaDetalleVentas lineas = lineaMaster.getArrDetalleVentas().get(i);
            String articuloBusc = lineas.getARTICULO();
            LineaItemNetsuite lineaItenmNet = getLineaComunicacion().getDbo2().buscaArticulo(articuloBusc);

// System.out.println(articuloBusc + " "+ lineaItenmNet.getIdNetsuite());
            lineas.setIdNetsuite(lineaItenmNet.getIdNetsuite());
            if (lineaItenmNet.getIdNetsuite() == 0) {
                lineaMaster.setNota(lineaMaster.getNota() + articuloBusc + "\n");
                lineaMaster.setValidado(false);
                lineas.setAlarma(true);
            }
            lineaMaster.getArrDetalleVentas().set(i, lineas);
        }
        System.out.println(lineaMaster.getJsonSalida().toString());
        return lineaMaster;
    }

    LineaOrdenDeVentas resTxSalien = null;

    public LineaOrdenDeVentas getResTxSalien() {
        return resTxSalien;
    }

    public void setResTxSalien(LineaOrdenDeVentas resTxSalien) {
        this.resTxSalien = resTxSalien;
    }

    public PnlPedido getpnlPedido() {
        if (pnlPedido == null) {
            pnlPedido = new PnlPedido();

            pnlPedido.getpnlSuperio().btnProcesa.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        pnlPedido.getpnlTotal().txtJson.setText(getResTxSalien().getJsonSalida().toString());
                        String res = getLineaComunicacion().getRest().procesa(getResTxSalien().getJsonSalida());
                        pnlPedido.getpnlTotal().TxtResulta.setText("" + res);
                        System.out.println(res);

                    } catch (IOException ex) {
                        Logger.getLogger(Consola.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });

            pnlPedido.getpnlSuperio().btnValida.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pnlPedido.getpnlSuperio().btnProcesa.setEnabled(false);
                    System.out.println(getDOCTO_PV_ID());
                    setResTxSalien(valicacion(getDOCTO_PV_ID()));
                    pnlPedido.getpnlSuperio().txtCliente.setText(getResTxSalien().getCliente());
                    pnlPedido.getpnlSuperio().txtidCliente.setText(getResTxSalien().getId_ClienteNet() + "");
                    pnlPedido.setArrOrdenes(getResTxSalien().getArrDetalleVentas());
                    pnlPedido.getpnlTotal().total.setText(getResTxSalien().getMonto() + "");
                    pnlPedido.actualiza();
                    pnlPedido.getpnlTotal().txtJson.setText(getResTxSalien().getJsonSalida().toString());
                    if (getResTxSalien().isValidado()) {
                        pnlPedido.getpnlSuperio().btnProcesa.setEnabled(true);
                    } else {
                        pnlPedido.getpnlTotal().txtJson.setText(getResTxSalien().getNota());
                    }
                    if (getResTxSalien().isValidado()) {
                        pnlPedido.getpnlSuperio().btnProcesa.setEnabled(true);
                    }
                }
            });

            pnlPedido.getpnlSuperio().btnRegreso.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    verMain();
                }
            });
        }

        return pnlPedido;
    }

    public void verdetalle() {
        java.awt.CardLayout card = (java.awt.CardLayout) getpnlCardPedido().getLayout();
        card.show(getpnlCardPedido(), "detalle");
    }

    public void verMain() {
        java.awt.CardLayout card = (java.awt.CardLayout) getpnlCardPedido().getLayout();
        card.show(getpnlCardPedido(), "main");
    }

    private int DOCTO_PV_ID = 0;

    public int getDOCTO_PV_ID() {
        return DOCTO_PV_ID;
    }

    public void setDOCTO_PV_ID(int DOCTO_PV_ID) {
        this.DOCTO_PV_ID = DOCTO_PV_ID;
    }

    public boolean verDetallesValida(int row) {
        boolean resData = false;
        ArrayList<LineaOrdenDeVentas> arrlinea = pnlOrdenesVentas.getArrLineaOrdenDeVentas();
        LineaOrdenDeVentas linea = arrlinea.get(row);
        setDOCTO_PV_ID(linea.getDOCTO_PV_ID());
        arrlinea.set(row, masterProces(linea));
        return resData;
    }

    public LineaOrdenDeVentas masterProces(LineaOrdenDeVentas linea) {
        getpnlPedido().getpnlSuperio().txtCliente.setText(linea.getCliente());
        linea.setId_ClienteNet(getLineaComunicacion().getDbo2().buscaCliente(linea.getCliente()).getIdinternoNetsuite());
        System.out.println("Cliente "+linea.getId_ClienteNet());
        getpnlPedido().getpnlSuperio().txtFecha.setText(linea.getFecha());
        getpnlPedido().getpnlSuperio().txtid.setText(linea.getDOCTO_PV_ID() + "");
        getpnlPedido().getpnlTotal().total.setText(Herramientas.convierteMoneda(linea.getMonto()));
        System.out.println(linea.getDOCTO_PV_ID());
        linea.setArrDetalleVentas(getLineaComunicacion().getConex().getVentasDetalle(linea.getDOCTO_PV_ID()));
        
        boolean valida = true;
        String listaaetic = "";
        for (int i = 0; i < linea.getArrDetalleVentas().size(); i++) {
            LineaDetalleVentas lineaBus = linea.getArrDetalleVentas().get(i);
            LineaItemNetsuite res = getLineaComunicacion().getDbo2().buscaArticulo(lineaBus.getARTICULO());
            if (res == null) {
                listaaetic += lineaBus.getARTICULO() + ", ";
                valida = false;
                lineaBus.setIdNetsuite(0);
                lineaBus.setMesaje("No se encontro.. " + lineaBus.getARTICULO());
            } else {
                lineaBus.setIdNetsuite(res.getIdNetsuite());
            }
            linea.getArrDetalleVentas().set(i, lineaBus);
        }

        if (valida == false) {
            linea.setValidado(valida);
            linea.setNota("No encontro Artuculo " + listaaetic);
        } else {
            linea.setValidado(true);
            linea.setNota("Ok -> Subir");
        }
        getpnlPedido().setArrOrdenes(linea.getArrDetalleVentas());
        getpnlPedido().gettablaDatos().repaint();
        getpnlPedido().getjscPanel().setViewportView(getpnlPedido().gettablaDatos());
        javax.swing.JScrollBar bar = getpnlPedido().getjscPanel().getVerticalScrollBar();
        bar.setValue(bar.getMinimum());
        linea.setArrDetalleVentas(getpnlPedido().getArrOrdenes());
        return linea;
    }

    private PnlVerDetalles pnlmsg = null;

    public void muestraMensage(String txt) {
        pnlmsg = new PnlVerDetalles(txt);
        pnlmsg.setSize(600, 400);
        pnlmsg.setLocation(Herramientas.puntoCentroPag(pnlmsg));
        pnlmsg.setVisible(true);
        pnlmsg.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                pnlmsg.setVisible(false);
                pnlmsg = null;
            }

        });

        pnlmsg.getbtnSalida().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pnlmsg.setVisible(false);
                pnlmsg = null;
            }
        });

    }

    public PnlOrdenesVentas getpnlOrdenesVentas() {
        if (pnlOrdenesVentas == null) {
            pnlOrdenesVentas = new PnlOrdenesVentas(getLineaComunicacion());
            
               javax.swing.table.TableColumn col1 = pnlOrdenesVentas.gettablaProcesar().getColumnModel().getColumn(8);
                    col1.setPreferredWidth(300);
            pnlOrdenesVentas.gettablaProcesar().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {

                        int row = pnlOrdenesVentas.gettablaProcesar().rowAtPoint(e.getPoint());
                        int col = pnlOrdenesVentas.gettablaProcesar().columnAtPoint(e.getPoint());
                        System.out.println(row + " " + col);
                        if (col < 6) {
                            verDetallesValida(row);
                            verdetalle();
                        } else {
                            LineaOrdenDeVentas linea = pnlOrdenesVentas.getArrLineaOrdenDeVentas().get(row);
                            String txt = "";
                            switch (col) {
                                case 7:
                                    txt = linea.getValidadoText();
                                    break;
                                case 8:
                                    txt = linea.getProcesadoText();
                                    break;
                                case 9:
                                    txt = linea.getNota();
                                    break;
                            }
                            muestraMensage(txt);

                        }

                    }
                }
            });
            pnlOrdenesVentas.getbtnValidar().addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pnlOrdenesVentas.getbtnValidar().setEnabled(false);
                    pnlOrdenesVentas.getbtnProcesarMx().setEnabled(false);
                    MainCLassValidacion vali = new MainCLassValidacion();
                    vali.start();
                }
            });
            pnlOrdenesVentas.getbtnProcesarMx().addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Subiendo");
                    MainProcesarSubir main =  new MainProcesarSubir();
                    main.start();
                }
            });
            
            
           // 
            pnlOrdenesVentas.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                }
            });
        }
        return pnlOrdenesVentas;
    }

    public class MainProcesarSubir extends Thread {

        public MainProcesarSubir() {

        }

        public void run() {
            getpnlOrdenesVentas().getbtnValidar().setEnabled(false);
            getpnlOrdenesVentas().getbtnProcesarMx().setEnabled(false);

            for (int i = 0; i < getpnlOrdenesVentas().getArrLineaOrdenDeVentas().size(); i++) {
                LineaOrdenDeVentas linea = getpnlOrdenesVentas().getArrLineaOrdenDeVentas().get(i);
                linea.setProcesadoText("");
                getpnlOrdenesVentas().getArrLineaOrdenDeVentas().set(i, linea);
            }
            getpnlOrdenesVentas().repaint();
            for (int i = 0; i < getpnlOrdenesVentas().getArrLineaOrdenDeVentas().size(); i++) {
                LineaOrdenDeVentas linea = getpnlOrdenesVentas().getArrLineaOrdenDeVentas().get(i);
                if (linea.isValidado()) {
                    try {
                        System.out.println(linea.getValidadoText());;
                        JSONObject obsSali = linea.getJsonSalida();
                        obsSali.accumulate("accion","produccion2");
                        obsSali.accumulate("data", Base64Trans.base64Encode(linea.getValidadoText()));
                        String resJson = getLineaComunicacion().getRest().procesa(obsSali);
                        linea.setProcesadoText(resJson);
                        linea.setProcesado(true);
                        getpnlOrdenesVentas().getArrLineaOrdenDeVentas().set(i,linea );
                        getpnlOrdenesVentas().getmodelotx().fireTableRowsUpdated(i, i);
                        System.out.println("Rest "+ resJson);
                    } catch (IOException ex) {
                        Logger.getLogger(Consola.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                getpnlOrdenesVentas().getArrLineaOrdenDeVentas().set(i, linea);
            }
            System.out.println("Termina");
            getpnlOrdenesVentas().getbtnValidar().setEnabled(true);
            getpnlOrdenesVentas().getbtnProcesarMx().setEnabled(true);

        }

    }

    public class MainCLassValidacion extends Thread {

        public MainCLassValidacion() {

        }

        public void run() {
            getpnlOrdenesVentas().getbtnValidar().setEnabled(false);
            getpnlOrdenesVentas().getbtnProcesarMx().setEnabled(false);

            for (int i = 0; i < getpnlOrdenesVentas().getArrLineaOrdenDeVentas().size(); i++) {
                LineaOrdenDeVentas linea = getpnlOrdenesVentas().getArrLineaOrdenDeVentas().get(i);
                linea.setNota("");
                linea.setValidadoText("");
                linea.setProcesadoText("");
                getpnlOrdenesVentas().getArrLineaOrdenDeVentas().set(i, linea);
            }
            getpnlOrdenesVentas().repaint();
            //////////  
            for (int i = 0; i < getpnlOrdenesVentas().getArrLineaOrdenDeVentas().size(); i++) {
                LineaOrdenDeVentas linea = getpnlOrdenesVentas().getArrLineaOrdenDeVentas().get(i);
                linea = masterProces(linea);
                linea.setValidadoText(linea.getJsonSalida().toString());
                getpnlOrdenesVentas().getArrLineaOrdenDeVentas().set(i, linea);
            }
            getpnlOrdenesVentas().repaint();
            getpnlOrdenesVentas().getbtnValidar().setEnabled(true);
            getpnlOrdenesVentas().getbtnProcesarMx().setEnabled(true);

        }
    }

    public javax.swing.JLabel getlbProceso() {
        if (lbProceso == null) {
            lbProceso = new javax.swing.JLabel();
        }
        return lbProceso;
    }

    public PnlItem getpnlItemCompurativo() {
        if (pnlItemCompurativo == null) {
            pnlItemCompurativo = new PnlItem(getLineaComunicacion(), 'B', false);
        }
        return pnlItemCompurativo;
    }

    public PnlClientes getpnlClienteFirebird() {
        if (pnlClienteFirebird == null) {
            pnlClienteFirebird = new PnlClientes(getLineaComunicacion(), false);
            pnlClienteFirebird.getbtnActualizar().addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("");
                    pnlClienteFirebird.actualizaFireBird();
                }
            });

        }
        return pnlClienteFirebird;
    }

    public PnlClientes getpnlClienteNetsuite() {
        if (pnlClienteNetsuite == null) {
            pnlClienteNetsuite = new PnlClientes(getLineaComunicacion(), true);
        }
        return pnlClienteNetsuite;
    }

    public void cierra() {
        getLineaComunicacion().getDbo2().closeConnection();
        System.exit(0);
    }

    public LineaComunicacion getLineaComunicacion() {
        return lineaComunicacion;
    }

    public void setLineaComunicacion(LineaComunicacion lineaComunicacion) {
        this.lineaComunicacion = lineaComunicacion;
    }

    public javax.swing.JPanel getpnlBotones() {
        if (pnlBotones == null) {
            pnlBotones = new javax.swing.JPanel();
            pnlBotones.add(getbtnIniciaProceso());
            pnlBotones.add(getlbProceso());
        }
        return pnlBotones;
    }

    public javax.swing.JPanel getpnlSetup() {
        if (pnlSetup == null) {
            pnlSetup = new javax.swing.JPanel(new java.awt.BorderLayout());
            pnlSetup.add(getpnlBotones());
        }
        return pnlSetup;
    }

    public javax.swing.JButton getbtnIniciaProceso() {
        if (btnIniciaProceso == null) {
            btnIniciaProceso = new javax.swing.JButton("Actualizacion de Datos Netsuita a Mysql");
            btnIniciaProceso.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ProcesoInicioB proc = new ProcesoInicioB();
                    proc.start();
                }
            });

        }
        return btnIniciaProceso;
    }

    public class ProcesoInicioB extends Thread {

        public ProcesoInicioB() {

        }

        public void run() {
            System.out.println("Iniciado");
            getbtnIniciaProceso().setEnabled(false);
            getlbProceso().setText("Inicia Proceso con  Articulos");
            try {
                getpnlItem().actualizaNetsuite();
            } catch (Exception e) {
               // e.printStackTrace();
            }
            getpnlItemCompurativo().actualizaFireBird();
            getlbProceso().setText("Inicia Proceso con  Clientes");

            getpnlOrdenesVentas().actualizaCodigos();
            getpnlClienteFirebird().actualizaFireBird();
            getpnlClienteNetsuite().actualizaNetsuite();
            getbtnIniciaProceso().setEnabled(true);
            getpnlItem().actualizaFireBird();
            getlbProceso().setText("Listo");

        }

    }

    public javax.swing.JPanel getpnlAbajo() {
        if (pnlAbajo == null) {
            pnlAbajo = new javax.swing.JPanel();
        }
        return pnlAbajo;
    }

    public PnlItem getpnlItem() {
        if (pnlItem == null) {
            pnlItem = new PnlItem(getLineaComunicacion(), 'A', true);
        }
        return pnlItem;
    }

    public javax.swing.JPanel getpnlMaster() {
        if (pnlMaster == null) {
            pnlMaster = new javax.swing.JPanel(new java.awt.BorderLayout());
            pnlMaster.add(gettabProcesos(), java.awt.BorderLayout.CENTER);

        }
        return pnlMaster;
    }

    private javax.swing.JTabbedPane datosMicosis = null;

    public javax.swing.JTabbedPane getdatosMicosis() {
        if (datosMicosis == null) {
            datosMicosis = new javax.swing.JTabbedPane();
            datosMicosis.addTab("Artiulos de Microsis", getpnlItemCompurativo());
            datosMicosis.addTab("Clientes", getpnlClienteFirebird());
        }
        return datosMicosis;
    }
    private javax.swing.JTabbedPane tabNetsoft = null;

    public javax.swing.JTabbedPane gettabNetsoft() {
        if (tabNetsoft == null) {
            tabNetsoft = new javax.swing.JTabbedPane();
            tabNetsoft.add("Item", getpnlItem());
            tabNetsoft.add("Clientes", getpnlClienteNetsuite());
        }
        return tabNetsoft;
    }

    public javax.swing.JTabbedPane gettabProcesos() {
        if (tabProcesos == null) {
            tabProcesos = new javax.swing.JTabbedPane();
            tabProcesos.addTab("Setup panel", getpnlSetup());
            tabProcesos.addTab("NETSUITE", gettabNetsoft());
            tabProcesos.addTab("MICROSIP.", getdatosMicosis());
            tabProcesos.addTab("Ventas", getpnlOrdenesVentas());
            tabProcesos.setIconAt(0, Herramientas.getIcono(""));

        }
        return tabProcesos;
    }

}
