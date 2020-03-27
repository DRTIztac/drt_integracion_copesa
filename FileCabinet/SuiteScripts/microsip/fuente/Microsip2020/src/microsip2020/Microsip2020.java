/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microsip2020;

import restletsmx.LineaSecret;
import restletsmx.LineaUltimaEntrada;
import restletsmx.Login;
import restletsmx.RestLet_Service;
import chilesSecos.Consola;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author jorge
 */
public class Microsip2020 extends javax.swing.JFrame {

    private Login login = null;
    private String version = "1.2";
    private javax.swing.JFrame frameCredenciales = null;
    private restletsmx.Credenciales credenciales = null;
    private Dbo4 dbo4 = null;
    private LineaSecret lineaSecreta = null;
    private Consola consola = null;
    private RestLet_Service rest = null;
    private LineaComunicacion lineaComunicaion = null;
    private LineaUltimaEntrada lineaEntrada = new LineaUltimaEntrada();

    public Microsip2020() {
        setLineaEntrada(getdbo4().getUltEntradas());
        getlogin().ambiente.setSelectedIndex(getLineaEntrada().getTipo());
       
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                getdbo4().closeConnection();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                getdbo4().closeConnection();
                System.exit(0);
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }
        });
        getlogin().ambiente.setSelectedIndex(getLineaEntrada().getTipo());
        setIconImage(Herramientas.getImage("Logo_Chiles_Secos.jpg"));
        setTitle("Chiles Secos " + version);
        add(getlogin());
        pack();

        
    }

    public void valicaion() {
        if (frameCredenciales == null) {
            if (getLineaEntrada() == null) {
                setLineaEntrada(new LineaUltimaEntrada());
            }
            setLineaSecreta(getdbo4().parametosGenerales(getLineaEntrada().getTipo()));
            if (getLineaSecreta() == null) {
                Herramientas.mensageGrafico("No  hay Credenciales");
                getlogin().ambiente.setEditable(true);
            }
        }
    }

    public LineaUltimaEntrada getLineaEntrada() {
        return lineaEntrada;
    }

    public void setLineaEntrada(LineaUltimaEntrada lineaEntrada) {
        this.lineaEntrada = lineaEntrada;
    }

    public LineaComunicacion getLineaComunicaion() {
        return lineaComunicaion;
    }

    public void setLineaComunicaion(LineaComunicacion lineaComunicaion) {
        this.lineaComunicaion = lineaComunicaion;
    }

    public LineaSecret getLineaSecreta() {
        return lineaSecreta;
    }

    public void setLineaSecreta(LineaSecret lineaSecreta) {
        this.lineaSecreta = lineaSecreta;
    }

    public Dbo4 getdbo4() {
        if (dbo4 == null) {
            dbo4 = new Dbo4();
        }
        return dbo4;
    }

    public static java.awt.Point puntoCentroPag(javax.swing.JFrame frame) {
        java.awt.Point point = new java.awt.Point();
        int yy = frame.getHeight();
        int xx = frame.getWidth();
        java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        java.awt.Dimension dim = toolkit.getScreenSize();
        int x = (int) ((float) dim.getWidth() / 2) - (int) ((float) xx / 2);
        int y = (int) ((float) dim.getHeight() / 2) - (int) ((float) yy / 2);
        point.setLocation(x, y);
        return point;
    }

    public void credencial(LineaSecret lineaS) {
        if (frameCredenciales == null) {
            frameCredenciales = new javax.swing.JFrame();
            frameCredenciales.addWindowListener(new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    getLineaComunicaion().getDbo2().closeConnection();
                    frameCredenciales.setVisible(false);
                    frameCredenciales = null;
                    credenciales = null;
                    System.exit(0);
                }

            });
            frameCredenciales.setIconImage(Herramientas.getImage("Logo_Chiles_Secos.jpg"));
            credenciales = new restletsmx.Credenciales();
            if (lineaS != null) {
                credenciales.setLinea(lineaS);
            }

            credenciales.ambiente.setSelectedIndex(login.ambiente.getSelectedIndex());
            credenciales.btnGrabar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    LineaSecret linea = credenciales.getLinea();
                    linea.setAmbiente(login.ambiente.getSelectedIndex());
                    getdbo4().graba(linea);
                    getLineaEntrada().setTipo(login.ambiente.getSelectedIndex());
                    getdbo4().graba(getLineaEntrada());
                    frameCredenciales.setAlwaysOnTop(false);
                    Herramientas.mensageGrafico("Por favor vuelva a Cargar");
                    getdbo4().closeConnection();
                    System.exit(0);
                }
            });
            login.ambiente.setEnabled(false);
            frameCredenciales.add(credenciales);
            frameCredenciales.pack();
            frameCredenciales.setLocation(puntoCentroPag(frameCredenciales));
            frameCredenciales.setVisible(true);
            frameCredenciales.setAlwaysOnTop(true);
            frameCredenciales.setAlwaysOnTop(false);
            frameCredenciales.setResizable(false);
        }
    }

    public RestLet_Service getRest() {
        return rest;
    }

    public void setRest(RestLet_Service rest) {
        this.rest = rest;
    }

    public void append(String txt) {
        getlogin().append(txt + "\n");
    }

    public class ProceCarga extends Thread {

        public ProceCarga() {

        }

        public void muestraConsola() {
            consola = new Consola(getLineaComunicaion(), true);
            consola.setTitle(getTitle() + " " + login.ambiente.getSelectedItem().toString());
            consola.setIconImage(Herramientas.getImage("Logo_Chiles_Secos.jpg"));
            consola.setSize(800, 600);
            consola.setLocation(Herramientas.puntoCentroPag(consola));
            consola.setVisible(true);
            setVisible(false);

        }

        public void run() {
            try {
                append("Inicia el  proceso");
                getLineaEntrada().setTipo(login.ambiente.getSelectedIndex());
                getdbo4().graba(getLineaEntrada());

                login.btnEntar.setEnabled(false);
                login.jtxtAreaProceso.setText("");
                // base  de datos...

                setRest(new RestLet_Service(getLineaSecreta()));
                JSONObject jsonService = new JSONObject();
                jsonService.accumulate("accion", "entrada");
                append("Comunciacion con netsuite");
                RestLet_Service restl = new RestLet_Service(getLineaSecreta());
                setRest(restl);
                String resultado1 = getRest().procesa(jsonService);
                System.out.println("R->" + resultado1);
                JSONObject jsonRespuesta = null;
                setLineaComunicaion(new LineaComunicacion());
                try {
                    jsonRespuesta = new JSONObject(resultado1);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
                if (jsonRespuesta == null) {
                    Herramientas.mensageError("NO TIENES ENTRADA");
                    append("-----Error----en  la comunicaion con Netsuite" + resultado1);
                    return;
                } else {
                    try {
                        if (jsonRespuesta.get("ok").equals("ok")) {
                            append("-----OK-----");
                            getLineaComunicaion().setRest(getRest());
                        } else {
                            login.btnEntar.setEnabled(true);
                            append("PROBLEMAS CON COMUNICACION");
                            return;
                        }
                    } catch (Exception ee) {
                        login.btnEntar.setEnabled(true);
                        append("PROBLEMAS CON COMUNICACION");
                        append(ee.getMessage());
                        append(resultado1);
                        return;
                    }
                }
                ////////////7 fase 2
                //  valida  comuncion  con  base de Datos
                append("Comunciacion con FireBird");
                Coneccion conx = new Coneccion();
                try {
                    conx.setLineaSecreta(getLineaSecreta());
                    java.sql.Connection contor = conx.conectar();
                    if (contor == null) {
                        append("---NO SE CONECTO A BASE--");
                        login.btnEntar.setEnabled(true);
                        return;
                    }
                    conx.muestra();
                    getLineaComunicaion().setConex(conx);
                    append("---OK----");
                } catch (Exception ee) {
                    append("---error en firebird----" + "\\n" + ee.getMessage());
                    ee.printStackTrace();
                    login.btnEntar.setEnabled(true);
                }
            } catch (IOException ex) {
                Logger.getLogger(Microsip2020.class.getName()).log(Level.SEVERE, null, ex);
                Herramientas.mensageError(ex.getMessage());
                login.btnEntar.setEnabled(true);
            }
            ///---------------------------------
            append("---BASE ORIENTADA A OBJETO---");
            append("---OK---");
            getLineaComunicaion().setDbo2(getdbo4());
            muestraConsola();

            login.btnEntar.setEnabled(true);

        }

    }

    private Login getlogin() {
        if (login == null) {
            login = new Login();
            login.btnEntar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ProceCarga proc = new ProceCarga();
                    proc.start();
                }
            });
            login.ambiente.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getLineaEntrada().setTipo(login.ambiente.getSelectedIndex());
                    valicaion();
                }
            });
            login.btnCredenciales.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    credencial(getLineaSecreta());
                }
            });
        }
        return login;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Microsip2020 microChiles = new Microsip2020();
        microChiles.setResizable(false);
        microChiles.setVisible(true);
        microChiles.setLocation(puntoCentroPag(microChiles));

    }

}
