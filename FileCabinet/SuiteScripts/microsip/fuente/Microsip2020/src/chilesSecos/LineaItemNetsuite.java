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
public class LineaItemNetsuite {

    private int idNetsuite = 0;
    private int  ARTICULO_ID = 0;
    private int PRECIO_ARTICULO_ID =  0;
    private String nombre = "";
    private String sku = "";
    private float precio = 0;
    private boolean validado = false;
    private String uniadadVenta = "";
    private String unidadCompra = "";
    private int origen = 0;
    private boolean netsuite = false;
    
    

    public LineaItemNetsuite() {
    }

    public int getARTICULO_ID() {
        return ARTICULO_ID;
    }

    public void setARTICULO_ID(int ARTICULO_ID) {
        this.ARTICULO_ID = ARTICULO_ID;
    }

    public int getPRECIO_ARTICULO_ID() {
        return PRECIO_ARTICULO_ID;
    }

    public void setPRECIO_ARTICULO_ID(int PRECIO_ARTICULO_ID) {
        this.PRECIO_ARTICULO_ID = PRECIO_ARTICULO_ID;
    }

  
    
    

    public boolean isNetsuite() {
        return netsuite;
    }

    public void setNetsuite(boolean netsuite) {
        this.netsuite = netsuite;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public String getUniadadVenta() {
        return uniadadVenta;
    }

    public void setUniadadVenta(String uniadadVenta) {
        this.uniadadVenta = uniadadVenta;
    }

    public String getUnidadCompra() {
        return unidadCompra;
    }

    public void setUnidadCompra(String unidadCompra) {
        this.unidadCompra = unidadCompra;
    }


    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public int getIdNetsuite() {
        return idNetsuite;
    }

    public void setIdNetsuite(int idNetsuite) {
        this.idNetsuite = idNetsuite;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.trim();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

}
