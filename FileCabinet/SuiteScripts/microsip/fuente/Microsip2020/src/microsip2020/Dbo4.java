/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microsip2020;

import restletsmx.LineaSecret;
import restletsmx.LineaUltimaEntrada;
import chilesSecos.LineaClientes;
import chilesSecos.LineaItemNetsuite;
import static microsip2020.Herramientas.mensageError;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Query;
import java.io.File;

/**
 *
 * @author jorge
 */
public class Dbo4 {

    private static ObjectContainer db = null;
    private static Dbo4 INSTANCE = null;
    private String PATH = "c:\\microsip\\keys.yap";

    public Dbo4() {
        performConnection();
    }

    public String getPATH() {
        return PATH;
    }

    public void setPATH(String PATH) {
        this.PATH = PATH;
    }

    public void performConnection() {
        try {
            Herramientas.creaCarpetas(getPATH());
            EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
            db = Db4oEmbedded.openFile(config, getPATH());
        } catch (Exception e) {
            mensageError("La  base de datos esta  abierta .- Ciera un  programa");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void graba(Object obj) {
        try {
            db.store(obj);
            //   System.out.println("Grabando....." + obj.getClass());
        } catch (Exception ee) {
            //  mensageGrafico("NO se grabo..");
            ee.printStackTrace();
        }
    }

    public void borraArticulos(int ori) {
        Query query = getDb().query();
        query.constrain(LineaItemNetsuite.class);
        LineaItemNetsuite lineaTem = new LineaItemNetsuite();
        lineaTem.setOrigen(ori);
        if (ori == 1) {
            lineaTem.setNetsuite(true);
        }
        query.descend("netsuite").equals(lineaTem.isNetsuite());
        ObjectSet res = query.execute();
        for (int i = 0; i < res.size(); i++) {
            LineaItemNetsuite linea = (LineaItemNetsuite) res.next();
            delete(linea);
        }
    }

    public void borraClientes() {
        Query query = getDb().query();
        query.constrain(LineaClientes.class);
        ObjectSet res = query.execute();
        for (int i = 0; i < res.size(); i++) {
            LineaClientes linea = (LineaClientes) res.next();
            delete(linea);
        }
    }

    public LineaUltimaEntrada getUltEntradas() {
        LineaUltimaEntrada lin = new LineaUltimaEntrada();
        Query query = getDb().query();
        query.constrain(LineaUltimaEntrada.class);
        ObjectSet res = query.execute();
        for (int i = 0; i < res.size(); i++) {
            LineaUltimaEntrada linea = (LineaUltimaEntrada) res.next();
            System.out.println("Ambiente ->" + linea.getTipo());
            return linea;
        }
        return lin;
    }

    public LineaSecret parametosGenerales(int ambiente) {
        Query query = getDb().query();
        query.constrain(LineaSecret.class);
        LineaSecret linBus = new LineaSecret();
        linBus.setAmbiente(ambiente);
        // query.descend("folio").orderAscending();
        query.descend("ambiente").constrain(linBus.getAmbiente());
        ObjectSet res = query.execute();
        for (int i = 0; i < res.size(); i++) {
            LineaSecret linea = (LineaSecret) res.next();
            System.out.println(linea.getToken_id());
            return linea;
        }
        return null;
    }

    public LineaItemNetsuite buscaArticulo(String art) {
        Query query = getDb().query();
        query.constrain(LineaItemNetsuite.class);
        query.descend("nombre").constrain(art);
        ObjectSet res = query.execute();
        for (int i = 0; i < res.size(); i++) {
            LineaItemNetsuite linea = (LineaItemNetsuite) res.next();
            System.out.println("-->" + linea.getIdNetsuite() + " "+ linea.getNombre());
            return linea;
        }
        return null;
    }

    public java.util.ArrayList<LineaItemNetsuite> buscaArticuloGeneral(String art) {
        java.util.ArrayList<LineaItemNetsuite> arrList = new java.util.ArrayList<LineaItemNetsuite>();
        Query query = getDb().query();
        query.constrain(LineaItemNetsuite.class);
        if (art.length() > 0) {
            query.descend("nombre").constrain(art.toUpperCase());
        }
        ObjectSet res = query.execute();
        for (int i = 0; i < res.size(); i++) {
            LineaItemNetsuite linea = (LineaItemNetsuite) res.next();
            System.out.println("-->" + linea.getIdNetsuite() + " " + linea.getNombre());
            arrList.add(linea);
        }
        return arrList;
    }

    public LineaClientes buscaCliente(String cli) {
        Query query = getDb().query();
        query.constrain(LineaClientes.class);
        System.out.println("Buscando " + cli.toUpperCase());
        query.descend("NOMBRE").constrain(cli);
        ObjectSet res = query.execute();
        for (int i = 0; i < res.size(); i++) {
            LineaClientes linea = (LineaClientes) res.next();
            System.out.println("-->" + linea.getNOMBRE() + " " + linea.getIdinternoNetsuite());
            return linea;
        }
        return null;
    }

    public java.util.ArrayList<LineaClientes> getAllClientes() {
        java.util.ArrayList<LineaClientes> arrLineaCliente = new java.util.ArrayList<LineaClientes>();
        Query query = getDb().query();
        query.constrain(LineaClientes.class);
        ObjectSet res = query.execute();
        for (int i = 0; i < res.size(); i++) {
            LineaClientes linea = (LineaClientes) res.next();
            System.out.println(linea.getIdinterno() + " " + linea.getNOMBRE());
            arrLineaCliente.add(linea);
        }
        return arrLineaCliente;
    }

    public static void main(String[] args) {
        Dbo4 db04 = new Dbo4();
        db04.getAllClientes();
        db04.buscaCliente("ABARROTES Y CEREALES ANTILLON SA DE CV");
        /*
        db04.buscaArticuloGeneral("ARBOL PINTO CON CABO");
        LineaItemNetsuite linea = db04.buscaArticulo("CHARAL ENCHILADO");
        if (linea == null) {
            System.out.println("Sin  datos");
        } else {
            System.out.println("Con  datos" + linea.getIdNetsuite());
        }
        LineaItemNetsuite linea1 = db04.buscaArticulo("JAMAICA TEMPRANERA");
        if (linea1 == null) {
            System.out.println("Sin  datos");
        } else {
            System.out.println("Con  datos"+ linea.getIdNetsuite() );
        }
        LineaItemNetsuite linea2 = db04.buscaArticulo("JAMAICA COLIMA");
        if (linea2 == null) {
            System.out.println("Sin  datos");
        } else {
            System.out.println("Con  datos" + linea.getIdNetsuite());
        }
         */
        db04.closeConnection();
    }

    /*
    public java.util.ArrayList<LineaResult> getParaProcesar() {
        java.util.ArrayList<LineaResult> arrLinea = new java.util.ArrayList<LineaResult>();
        Query query = getDb().query();
        query.constrain(LineaHuman200.class);
        LineaResult lin = new LineaResult();
        query.descend("folio").orderAscending();
        query.descend("fecha").constrain(lin.getFecha()).like();
        ObjectSet res = query.execute();
        for (int i = 0; i < res.size(); i++) {
            LineaResult linea = (LineaResult) res.next();
            arrLinea.add(linea);
        }
        return arrLinea;
    }
     */
    public void delete(Object obt) {
        db.delete(obt);
    }

    public static ObjectContainer getDb() {
        return db;
    }

    public static void setDb(ObjectContainer db) {
        Dbo4.db = db;
    }

    public void closeConnection() {
        System.out.println("Cierra");
        if (db != null) {
            db.close();
        }
    }

}
