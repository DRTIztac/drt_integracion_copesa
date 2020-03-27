/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilesSecos;

/**
 *
 * @author jorge pc
 */
public class LineaClientes {
    private  int DIR_CLI_ID = 0;
    private  int idinterno = 0;
    private  int idinternoNetsuite = 0;
    private  int CLIENTE = 0;
    private  int CLIENTE_ID = 0;
    private  String  NOMBRE =  "";
    private  String  NOMBRE_CONSIG =  "";
    private  String  CALLE =  "";
    private  String  NOMBRE_CALLE =  "";
    private  String  NUM_EXT = "";
    private  String  NUM_INT = "";
    private  String  COLONIA = "";
    private  String  RFC_CURP = "";
    
    public LineaClientes() {
    }

    public int getIdinternoNetsuite() {
        return idinternoNetsuite;
    }

    public void setIdinternoNetsuite(int idinternoNetsuite) {
        this.idinternoNetsuite = idinternoNetsuite;
    }

   
    
    
    public int getCLIENTE_ID() {
        return CLIENTE_ID;
    }

    public void setCLIENTE_ID(int CLIENTE_ID) {
        this.CLIENTE_ID = CLIENTE_ID;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public int getDIR_CLI_ID() {
        return DIR_CLI_ID;
    }

    public void setDIR_CLI_ID(int DIR_CLI_ID) {
        this.DIR_CLI_ID = DIR_CLI_ID;
    }

    public int getIdinterno() {
        return idinterno;
    }

    public void setIdinterno(int idinterno) {
        this.idinterno = idinterno;
    }

    public int getCLIENTE() {
        return CLIENTE;
    }

    public void setCLIENTE(int CLIENTE) {
        this.CLIENTE = CLIENTE;
    }

    public String getNOMBRE_CONSIG() {
        return NOMBRE_CONSIG;
    }

    public void setNOMBRE_CONSIG(String NOMBRE_CONSIG) {
        this.NOMBRE_CONSIG = NOMBRE_CONSIG;
    }

    public String getCALLE() {
        return CALLE;
    }

    public void setCALLE(String CALLE) {
        this.CALLE = CALLE;
    }

    public String getNOMBRE_CALLE() {
        return NOMBRE_CALLE;
    }

    public void setNOMBRE_CALLE(String NOMBRE_CALLE) {
        this.NOMBRE_CALLE = NOMBRE_CALLE;
    }

    public String getNUM_EXT() {
        return NUM_EXT;
    }

    public void setNUM_EXT(String NUM_EXT) {
        this.NUM_EXT = NUM_EXT;
    }

    public String getNUM_INT() {
        return NUM_INT;
    }

    public void setNUM_INT(String NUM_INT) {
        this.NUM_INT = NUM_INT;
    }

    public String getCOLONIA() {
        return COLONIA;
    }

    public void setCOLONIA(String COLONIA) {
        this.COLONIA = COLONIA;
    }

    public String getRFC_CURP() {
        return RFC_CURP;
    }

    public void setRFC_CURP(String RFC_CURP) {
        this.RFC_CURP = RFC_CURP;
    }
    
    
    
    
    
}
