/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilesSecos;

import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author jorge pc
 */
public class LineaOrdenDeVentas {

    private int idInternoNetsuite = 0;
    private boolean procesado = false;
    private boolean validado = false;
    private String procesadoText = "";
    private String validadoText = "";
    private String fecha = "";
    private String hora = "";
    private String cliente = "";
    private int idcliente = 0;
    private float monto = 0;
    private int DOCTO_PV_ID = 0;
    private int CLIENTE_ID = 0;
    private int id_ClienteNet = 0;
    private int id_vendedor = 0;
    private int id_movimieto = 0;
    private int CAJERO_ID = 0;
    
    private java.util.ArrayList<LineaDetalleVentas> arrDetalleVentas = new java.util.ArrayList<LineaDetalleVentas>();
    private String nota = "";

    public LineaOrdenDeVentas() {
    }

    public int getCAJERO_ID() {
        return CAJERO_ID;
    }

    public void setCAJERO_ID(int CAJERO_ID) {
        this.CAJERO_ID = CAJERO_ID;
    }
    
    

    public String getProcesadoText() {
        return procesadoText;
    }

    public void setProcesadoText(String procesadoText) {
        this.procesadoText = procesadoText;
    }

    public String getValidadoText() {
        return validadoText;
    }

    public void setValidadoText(String validadoText) {
        this.validadoText = validadoText;
    }

    
    
    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    public int getId_ClienteNet() {
        return id_ClienteNet;
    }

    public void setId_ClienteNet(int id_ClienteNet) {
        this.id_ClienteNet = id_ClienteNet;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public JSONObject getJsonSalida() {
        org.json.JSONObject jsonSalida = new org.json.JSONObject();
        jsonSalida.accumulate("venta_id", getDOCTO_PV_ID());
        jsonSalida.accumulate("cliente_id", getId_ClienteNet());
        jsonSalida.accumulate("cliente", getCliente());
        jsonSalida.accumulate("cajero_id", getCAJERO_ID());
        jsonSalida.accumulate("fecha", getFecha());
        jsonSalida.accumulate("hora", getHora());
        org.json.JSONArray jsonArr =  new org.json.JSONArray(); 
        for (int i = 0; i < getArrDetalleVentas().size(); i++) {
            LineaDetalleVentas lineaVenta = getArrDetalleVentas().get(i);
            org.json.JSONObject jsonLinea = new org.json.JSONObject();
            jsonLinea.accumulate("qty", lineaVenta.getQty());
            jsonLinea.accumulate("itemid", lineaVenta.getIdNetsuite());
            jsonLinea.accumulate("itemtxt", lineaVenta.getARTICULO());
            jsonLinea.accumulate("precio", lineaVenta.getPRECIO_TOTAL_NETO());
            jsonArr.put(i, jsonLinea);
        }
        jsonSalida.accumulate("item", jsonArr);
        return jsonSalida;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public int getCLIENTE_ID() {
        return CLIENTE_ID;
    }

    public void setCLIENTE_ID(int CLIENTE_ID) {
        this.CLIENTE_ID = CLIENTE_ID;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public int getId_movimieto() {
        return id_movimieto;
    }

    public void setId_movimieto(int id_movimieto) {
        this.id_movimieto = id_movimieto;
    }


    public ArrayList<LineaDetalleVentas> getArrDetalleVentas() {
        return arrDetalleVentas;
    }

    public void setArrDetalleVentas(ArrayList<LineaDetalleVentas> arrDetalleVentas) {
        this.arrDetalleVentas = arrDetalleVentas;
    }

    public int getDOCTO_PV_ID() {
        return DOCTO_PV_ID;
    }

    public void setDOCTO_PV_ID(int DOCTO_PV_ID) {
        this.DOCTO_PV_ID = DOCTO_PV_ID;
    }

    public String getFecha() {
        return fecha.replaceAll("\\/", "-");
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public int getIdInternoNetsuite() {
        return idInternoNetsuite;
    }

    public void setIdInternoNetsuite(int idInternoNetsuite) {
        this.idInternoNetsuite = idInternoNetsuite;
    }

    public boolean isProcesado() {
        return procesado;
    }

    public void setProcesado(boolean procesado) {
        this.procesado = procesado;
    }

}
