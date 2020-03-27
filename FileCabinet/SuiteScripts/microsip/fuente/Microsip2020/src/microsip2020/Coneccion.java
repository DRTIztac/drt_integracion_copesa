/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microsip2020;

import restletsmx.LineaSecret;
import chilesSecos.LineaClientes;
import chilesSecos.LineaDetalleVentas;
import chilesSecos.LineaItemNetsuite;
import chilesSecos.LineaOrdenDeVentas;
import chilesSecos.Lineas_articulos;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class Coneccion {

    private Connection connection = null;

    private String ruta = "C:\\Microsip datos\\CHILES SECOS JR.FDB";
    private LineaSecret lineaSecreta = null;
    private String usurioFireBird = "sysdba";
    private String passwordFireBird = "masterkey";

    public Coneccion() {
    }

    
    
    public String getUsurioFireBird() {
        return usurioFireBird;
    }

    public void setUsurioFireBird(String usurioFireBird) {
        this.usurioFireBird = usurioFireBird;
    }

    public String getPasswordFireBird() {
        return passwordFireBird;
    }

    public void setPasswordFireBird(String passwordFireBird) {
        this.passwordFireBird = passwordFireBird;
    }

    public Coneccion(LineaSecret lineaSecreta) {
        setLineaSecreta(lineaSecreta);
    }

    public LineaSecret getLineaSecreta() {
        return lineaSecreta;
    }

    public void setLineaSecreta(LineaSecret lineaSecreta) {
        setRuta(lineaSecreta.getRuta());
        setUsurioFireBird(lineaSecreta.getUsuarioFireBird());
        setPasswordFireBird(lineaSecreta.getPasswordFireBird());
        this.lineaSecreta = lineaSecreta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public java.sql.Connection conectar() throws ClassNotFoundException, SQLException {
        Class.forName("org.firebirdsql.jdbc.FBDriver");
        setConnection(DriverManager.getConnection(
                "jdbc:firebirdsql://localhost:3050/" + getRuta(),
                getUsurioFireBird(), getPasswordFireBird()));
        System.out.println("Conectado");
        return getConnection();
    }

    public java.util.ArrayList<LineaDetalleVentas> getVentasDetalle(int DOCTO_PV_ID) {
        java.util.ArrayList<LineaDetalleVentas> arrLinea = new java.util.ArrayList<LineaDetalleVentas>();
        String sql = "select "
                + " DOCTOS_PV_DET.DOCTO_PV_DET_ID,"
                + " DOCTOS_PV_DET.DOCTO_PV_ID, "
                + " DOCTOS_PV_DET.POSICION, "
                + " DOCTOS_PV_DET.UNIDADES, "
                + " DOCTOS_PV_DET.PRECIO_UNITARIO,"
                + " DOCTOS_PV_DET.PRECIO_UNITARIO_IMPTO,"
                + " ARTICULOS.NOMBRE, "
                + " ARTICULOS.ARTICULO_ID"
                + " FROM DOCTOS_PV_DET "
                + " JOIN ARTICULOS ON DOCTOS_PV_DET.ARTICULO_ID  =  ARTICULOS.ARTICULO_ID "
                + " WHERE DOCTOS_PV_DET.DOCTO_PV_ID = " + DOCTO_PV_ID + "";
        System.out.println(sql);
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                LineaDetalleVentas linea = new LineaDetalleVentas();
                linea.setQty(rs.getFloat("UNIDADES"));
                linea.setARTICULO_ID(rs.getInt("ARTICULO_ID"));
                linea.setARTICULO(rs.getString("NOMBRE"));
                linea.setDOCTO_PV_DET_ID(rs.getInt("DOCTO_PV_DET_ID"));
                linea.setDOCTO_PV_ID(rs.getInt("DOCTO_PV_ID"));
                linea.setPRECIO_TOTAL_NETO(rs.getFloat("PRECIO_UNITARIO_IMPTO"));
                // System.out.println( rs.getString("PRECIO_UNITARIO_IMPTO")+ " "+rs.getFloat("PRECIO_UNITARIO_IMPTO"));
                arrLinea.add(linea);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrLinea;
    }

    public java.util.ArrayList<LineaOrdenDeVentas> getLineaVentas(String fecha) {
        java.util.ArrayList<LineaOrdenDeVentas> arrLinea = new java.util.ArrayList<LineaOrdenDeVentas>();
        String sql = "SELECT DOCTOS_PV.CAJA_ID, "
                + " DOCTOS_PV.DOCTO_PV_ID, "
                + " DOCTOS_PV.TIPO_DOCTO, "
                + " DOCTOS_PV.FOLIO, "
                + " DOCTOS_PV.FECHA, "
                + " DOCTOS_PV.HORA, "
                + " DOCTOS_PV.CAJERO_ID,"
                + " DOCTOS_PV.VENDEDOR_ID,"
                + " DOCTOS_PV.CLIENTE_ID,"
                + " DOCTOS_PV.IMPORTE_NETO,"
                + " CLIENTES.NOMBRE"
                + " FROM DOCTOS_PV "
                + " JOIN CLIENTES ON  CLIENTES.CLIENTE_ID = DOCTOS_PV.CLIENTE_ID "
                + " WHERE  DOCTOS_PV.TIPO_DOCTO =  'V'"
               /* + " AND DOCTOS_PV.FECHA BETWEEN  '"+fecha+"' AND '"+fecha+"'"*/;
        System.out.println(sql);
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                LineaOrdenDeVentas linea = new LineaOrdenDeVentas();
                linea.setDOCTO_PV_ID(rs.getInt("DOCTO_PV_ID"));
                linea.setCAJERO_ID(rs.getInt("CAJERO_ID"));
                linea.setId_vendedor(rs.getInt("VENDEDOR_ID"));
                linea.setCliente(rs.getString("NOMBRE"));
                linea.setFecha(rs.getString("FECHA"));
                linea.setHora(rs.getString("HORA"));
                linea.setMonto(rs.getFloat("IMPORTE_NETO"));
                System.out.println("CLIENTE ES :" + rs.getString("CLIENTE_ID") + " " + rs.getString("NOMBRE") + " " + rs.getString("FECHA"));
                arrLinea.add(linea);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrLinea;
    }

    public LineaOrdenDeVentas getLineaVentas2(int DOCTO_PV_ID) {
        LineaOrdenDeVentas linea = new LineaOrdenDeVentas();

        String sql = "SELECT DOCTOS_PV.CAJA_ID, "
                + " DOCTOS_PV.DOCTO_PV_ID, "
                + " DOCTOS_PV.TIPO_DOCTO, "
                + " DOCTOS_PV.FOLIO, "
                + " DOCTOS_PV.FECHA, "
                + " DOCTOS_PV.HORA,  "
                + " DOCTOS_PV.CAJERO_ID,"
                + " DOCTOS_PV.CLIENTE_ID,"
                + " DOCTOS_PV.IMPORTE_NETO,"
                + " CLIENTES.NOMBRE"
                + " FROM DOCTOS_PV "
                + " JOIN CLIENTES ON  CLIENTES.CLIENTE_ID = DOCTOS_PV.CLIENTE_ID "
                + " WHERE "
                + " DOCTOS_PV.DOCTO_PV_ID = " + DOCTO_PV_ID;
        System.out.println(sql);
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                linea.setDOCTO_PV_ID(rs.getInt("DOCTO_PV_ID"));
                linea.setCliente(rs.getString("NOMBRE"));
                linea.setFecha(rs.getString("FECHA"));
                linea.setMonto(rs.getFloat("IMPORTE_NETO"));
                System.out.println("CLIENTE ES :" + rs.getString("CLIENTE_ID") + " " + rs.getString("NOMBRE") + " " + rs.getString("FECHA"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }

        linea.setArrDetalleVentas(getVentasDetalle(DOCTO_PV_ID));
        return linea;
    }

    public void muestra() {
        String sql = "SELECT NOMBRE FROM ARTICULOS";
        System.out.println(sql);
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println("NOMBRE ES :" + rs.getString("NOMBRE"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public java.util.ArrayList<LineaItemNetsuite> getAllArticulos(String txt) {
        java.util.ArrayList<LineaItemNetsuite> arrLinea = new java.util.ArrayList<LineaItemNetsuite>();
        String sql = "SELECT "
                + " ARTICULOS.NOMBRE,"
                + " ARTICULOS.UNIDAD_VENTA,"
                + " ARTICULOS.UNIDAD_COMPRA,"
                + " ARTICULOS.ARTICULO_ID, "
                + " ARTICULOS.UNIDAD_COMPRA,"
                + " ARTICULOS.UNIDAD_VENTA, "
                + " ARTICULOS.LINEA_ARTICULO_ID,"
                + " PRECIOS_ARTICULOS.PRECIO,"
                + " ARTICULOS.ARTICULO_ID  "
                + " FROM ARTICULOS "
                + " JOIN PRECIOS_ARTICULOS ON  ARTICULOS.ARTICULO_ID = PRECIOS_ARTICULOS.ARTICULO_ID "
                + " WHERE  ARTICULOS.NOMBRE LIKE '%" + txt.toUpperCase() + "%' "
                + " ORDER BY ARTICULOS.NOMBRE";
        try {
            System.out.println(sql);

            //PRECIOS_ARTICULOS
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                LineaItemNetsuite linea = new LineaItemNetsuite();
                linea.setNombre(rs.getString("NOMBRE"));
                linea.setARTICULO_ID(rs.getInt("ARTICULO_ID"));
                linea.setPrecio(rs.getFloat("PRECIO"));
                linea.setUniadadVenta(rs.getString("UNIDAD_VENTA"));
                linea.setUnidadCompra(rs.getString("UNIDAD_COMPRA"));
                linea.setNetsuite(false);
                linea.setOrigen(0);
                arrLinea.add(linea);
                // System.out.println("Lectura " + linea.getNombre() + " "+ linea.getPrecio());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrLinea;
    }

    public java.util.ArrayList<LineaClientes> getAllClientes(String txt) {
        java.util.ArrayList<LineaClientes> arrLinea = new java.util.ArrayList<LineaClientes>();
        String sql = "SELECT  CLIENTES.CLIENTE_ID, "
                + " CLIENTES.NOMBRE "
                + " FROM CLIENTES "
                + " WHERE CLIENTES.NOMBRE LIKE '%" + txt + "%' ORDER BY CLIENTES.CLIENTE_ID;";
        try {
            System.out.println(sql);

            //PRECIOS_ARTICULOS
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                LineaClientes linea = new LineaClientes();
                linea.setCLIENTE_ID(rs.getInt("CLIENTE_ID"));
                linea.setIdinterno(rs.getInt("CLIENTE_ID"));
                linea.setNOMBRE(rs.getString("NOMBRE").trim());

                //   System.out.println(linea.getCLIENTE_ID()+ " "+linea.getNOMBRE() );
                arrLinea.add(linea);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrLinea;
    }

    public void muestra2() {
        String sql = "DESCRIBE TABLE  DOCTOS_PV";
        try {
            System.out.println(sql);

            //PRECIOS_ARTICULOS
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {

            }
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int maxInterArt() {
        int res = 0;
        String sql = "SELECT MAX(ARTICULO_ID) FROM ARTICULOS";
        try {
            System.out.println(sql);
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public int insertaArticulo(String art) throws UnsupportedEncodingException {
        int max = maxInterArt() + 1;
        java.util.ArrayList<Lineas_articulos> arr = new java.util.ArrayList<Lineas_articulos>();
        arr.add(new Lineas_articulos(617, "JAMIACA"));
        arr.add(new Lineas_articulos(619, "AJO"));
        arr.add(new Lineas_articulos(621, "MOLIDOS"));
        arr.add(new Lineas_articulos(625, "CHARALES"));

        int res = 0;
        String sql = "INSERT INTO ARTICULOS  "
                + "(ARTICULO_ID,"
                + " NOMBRE,"
                + " ES_ALMACENABLE,"
                + " ES_JUEGO,"
                + " ESTATUS,"
                + " IMPRIMIR_COMP,"
                + " PERMITIR_AGREGAR_COMP,"
                + " LINEA_ARTICULO_ID, "
                + " UNIDAD_VENTA,"
                + " UNIDAD_COMPRA,"
                + " CONTENIDO_UNIDAD_COMPRA,"
                + " PESO_UNITARIO,"
                + " ES_PESO_VARIABLE,"
                + " SEGUIMIENTO,"
                + " DIAS_GARANTIA,"
                + " ES_IMPORTADO,"
                + " ES_SIEMPRE_IMPORTADO,"
                + " PCTJE_ARANCEL,"
                + " IMPRIMIR_NOTAS_COMPRAS,"
                + " IMPRIMIR_NOTAS_VENTAS,"
                + " ES_PRECIO_VARIABLE,"
                + " APLICAR_FACTOR_VENTA,"
                + " FACTOR_VENTA"
                + ") "
                + "VALUES "
                + "("
                + max + "," //ARTICULO_ID
                + "'" + art + "'," //NOMBRE
                + "'S',"
                + "'N',"
                + "'A',"
                + "'N',"
                + "'N',"
                + "619,"
                + "'Kilogramo',"
                + "'Kilogramo',"
                + " 1,"
                + " 1,"
                + " 'S',"
                + " 'N',"
                + " 0,"
                + " 'N',"
                + " 'N',"
                + " '0',"
                + " 'N',"
                + " 'N',"
                + " 'N'," // + "ES_PRECIO_VARIABLE"
                + " 'N'," // APLICAR_FACTOR_VENTA
                + " 0"
                + ");";
        System.out.println(sql);
        try {
            Statement statement = getConnection().createStatement();
            res = statement.executeUpdate(sql);
            // getConnection().commit();
        } catch (Exception ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return max;
    }

    public LineaItemNetsuite getIdARTICULOS(LineaItemNetsuite linea) {
        int res = 0;
        String sql = "SELECT ARTICULO_ID FROM ARTICULOS WHERE NOMBRE = '" + linea.getNombre() + "';";
        System.out.println(sql);
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            linea.setARTICULO_ID(0);
            while (rs.next()) {
                res = rs.getInt("ARTICULO_ID");
                System.out.println("res  ARTICULO_ID"+ res);
                linea.setARTICULO_ID(res);
            }
        } catch (SQLException ex) {
        //    Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return linea;
    }

    public int maxInterPRECIOS_ARTICULOS() {
        int res = 0;
        String sql = "SELECT MAX(PRECIO_ARTICULO_ID) FROM PRECIOS_ARTICULOS";
        try {
            System.out.println(sql);
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public LineaItemNetsuite getPRECIO_ARTICULO_ID(LineaItemNetsuite linea) {
        int res = 0;
        String sql = "SELECT PRECIO_ARTICULO_ID "
                + " FROM PRECIOS_ARTICULOS "
                + " WHERE ARTICULO_ID = " + linea.getARTICULO_ID() + ";";
        System.out.println(sql);
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                linea.setPRECIO_ARTICULO_ID(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return linea;
    }

    private LineaItemNetsuite actualizaPrecio(LineaItemNetsuite linea) {
        linea = getPRECIO_ARTICULO_ID(linea);
        if (linea.getPRECIO_ARTICULO_ID() == 0) {
            linea.setPRECIO_ARTICULO_ID(maxInterPRECIOS_ARTICULOS() + 1) ;
        }
        System.out.println("PRECIO_ARTICULO_ID ->"+ linea.getPRECIO_ARTICULO_ID());
        if (linea.getPRECIO_ARTICULO_ID() > 0) {
            String sql = "update or insert  into PRECIOS_ARTICULOS   "
                    + "("
                    + " PRECIO_ARTICULO_ID,"
                    + " ARTICULO_ID, "
                    + " PRECIO,"
                    + " MONEDA_ID,"
                    + " PRECIO_EMPRESA_ID,"
                    + " MARGEN"
                    + ") "
                    + "values "
                    + "(" + linea.getPRECIO_ARTICULO_ID() + ","
                    + " " + linea.getARTICULO_ID()+","
                    + " " + linea.getPrecio()+","
                    + " 1,"
                    + " 42,"
                    +  "0" 
                    + ") MATCHING (PRECIO_ARTICULO_ID)";
            try {
                System.out.println(sql);
                Statement statement = getConnection().createStatement();
                int res = statement.executeUpdate(sql);
               // getConnection().commit();
               
            } catch (SQLException ex) {
                System.err.println( "---->"+ sql);
                Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
             Herramientas.mensageGrafico(linea.getPRECIO_ARTICULO_ID()+"");
        }
        return linea;
    }

    public LineaItemNetsuite controArticulos(LineaItemNetsuite linea) throws UnsupportedEncodingException {
        
        linea = getIdARTICULOS(linea);
        if (linea.getARTICULO_ID() == 0) {
            int resInser = insertaArticulo(linea.getNombre());
            if (resInser > 0) {
                linea.setARTICULO_ID(resInser);
            } else {
                System.out.println("Error al alta de Articulos");
            }
        }
        System.out.println("Id Articulo "+linea.getARTICULO_ID() );
        linea = actualizaPrecio( linea );
        return linea;
    }
    
    public int getCLIENTE_ID(String NOMBRE) {
        int res = 0;
        String sql = "SELECT CLIENTE_ID FROM CLIENTES WHERE NOMBRE = '" + NOMBRE + "';";
        System.out.println(sql);
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public LineaClientes validaCliente(LineaClientes linea ) throws UnsupportedEncodingException{
        int res = 0;
        int CLIENTE_ID  =  getCLIENTE_ID(linea.getNOMBRE());
        if(CLIENTE_ID ==  0){
            CLIENTE_ID = maxCLIENTE_ID() + 1;
            System.out.println("CLIENTE_ID "+CLIENTE_ID);
            res = actualizaCliente(linea.getNOMBRE(), CLIENTE_ID);
            System.out.println("Exito CLIENTE_ID "+res);
        }else{
            res= 1;
        }
        linea.setCLIENTE_ID(CLIENTE_ID);
        return linea;
    }
    
    public int maxCLIENTE_ID() {
        int res = 0;
        String sql = "SELECT MAX(CLIENTE_ID) FROM CLIENTES";
        try {
            System.out.println(sql);
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
   
    private int actualizaCliente(String NOMBRE, int CLIENTE_ID) throws UnsupportedEncodingException {
        int res = 0;
            String sql = "update or insert  into CLIENTES   "
                    + "( "
                    + "CLIENTE_ID,"
                    + "NOMBRE,"
                    + "CONTACTO1,"
                    + "CONTACTO2,"
                    + "ESTATUS,"
                    + "CAUSA_SUSP,"
                    + "COBRAR_IMPUESTOS,"
                    + "RETIENE_IMPUESTOS,"
                    + "SUJETO_IEPS,"
                    + "GENERAR_INTERESES,"
                    + "EMITIR_EDOCTA,"
                    + "DIFERIR_CFDI_COBROS,"
                    + "LIMITE_CREDITO,"
                    + "MONEDA_ID,"
                    + "COND_PAGO_ID,"
                    + "USUARIO_CREADOR"
                    + ") "
                    + "values ("
                    + ""+CLIENTE_ID+","
                    + "'"+NOMBRE+"',"
                    + "''," //CONTACTO1
                    + "''," //CONTACTO2
                    + "'A'," //ESTATUS
                    + "''," //CAUSA_SUSP
                    + "'S'," //COBRAR_IMPUESTOS
                    + "'N'," //RETIENE_IMPUESTOS
                    + "'N'," //SUJETO_IEPS
                    + "'S'," //GENERAR_INTERESES
                    + "'S'," //EMITIR_EDOCTA
                    + " FALSE,"
                    + " 10000,"
                    + " 1,"
                    + " 1041,"
                    + "'SYSDBA'" //DIFERIR_CFDI_COBROS
                    + ") MATCHING (CLIENTE_ID)";
            try {
                System.out.println(sql);
                Statement statement = getConnection().createStatement();
                res = statement.executeUpdate(sql);
            } catch (SQLException ex) {
                Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return res;
    }
    

    public static void main(String[] args) throws UnsupportedEncodingException {
        Coneccion conx = new Coneccion();
        try {
            conx.setRuta("C:\\Microsip datos\\CHILES SECOS JR.FDB");
            conx.conectar();
            String rr = Math.random()+"";
           // System.out.println(conx.validaCliente("JORGE_"+rr));
            // System.out.println(conx.getIdARTICULOS("MOLE"));
            LineaItemNetsuite linea =  new  LineaItemNetsuite();
            linea.setNombre("ÑÑÑÑÑ");
            linea.setPrecio(100);
            conx.controArticulos(linea);
            
//  String rr = Math.random()+"";
            //  System.out.println(conx.insertaArticulo("TEST ARTICULrr"+rr));
            //conx.getLineaVentas("2020-01-03");
            // conx.getVentasDetalle(141);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
