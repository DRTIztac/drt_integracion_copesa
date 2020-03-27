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
public class LineaDetalleVentas {
    private float qty =  0;
    private int DOCTO_PV_DET_ID = 0;
    private int DOCTO_PV_ID = 0;
    private int CLAVE_ARTICULO = 0;
    private int ARTICULO_ID = 0;
    private String ARTICULO = "";
    private int UNIDADES = 0;
    private float PRECIO_UNITARIO = 0;
    private float PRECIO_UNITARIO_IMPO = 0;
    private float IMPUESTO_POR_UNIDAD = 0;
    private float PCTJE_DSCTO = 0;
    private float PRECIO_TOTAL_NETO = 0;
    private float PRECIO_MODIFICADO = 0;
    private int  idNetsuite = 0;
    private boolean  alarma =  false;
    private String mesaje =  "";
    
    public LineaDetalleVentas() {
    }

    public String getMesaje() {
        return mesaje;
    }

    public void setMesaje(String mesaje) {
        this.mesaje = mesaje;
    }
    

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    
    
    public boolean isAlarma() {
        return alarma;
    }

    public void setAlarma(boolean alarma) {
        this.alarma = alarma;
    }

    public int getIdNetsuite() {
        return idNetsuite;
    }

    public void setIdNetsuite(int idNetsuite) {
        this.idNetsuite = idNetsuite;
    }

    
    
   
    
    public String getARTICULO() {
        return ARTICULO;
    }

    public void setARTICULO(String ARTICULO) {
        this.ARTICULO = ARTICULO;
    }

    
    
    public int getDOCTO_PV_DET_ID() {
        return DOCTO_PV_DET_ID;
    }

    public void setDOCTO_PV_DET_ID(int DOCTO_PV_DET_ID) {
        this.DOCTO_PV_DET_ID = DOCTO_PV_DET_ID;
    }

    public int getDOCTO_PV_ID() {
        return DOCTO_PV_ID;
    }

    public void setDOCTO_PV_ID(int DOCTO_PV_ID) {
        this.DOCTO_PV_ID = DOCTO_PV_ID;
    }

    public int getCLAVE_ARTICULO() {
        return CLAVE_ARTICULO;
    }

    public void setCLAVE_ARTICULO(int CLAVE_ARTICULO) {
        this.CLAVE_ARTICULO = CLAVE_ARTICULO;
    }

    public int getARTICULO_ID() {
        return ARTICULO_ID;
    }

    public void setARTICULO_ID(int ARTICULO_ID) {
        this.ARTICULO_ID = ARTICULO_ID;
    }

    public int getUNIDADES() {
        return UNIDADES;
    }

    public void setUNIDADES(int UNIDADES) {
        this.UNIDADES = UNIDADES;
    }

    public float getPRECIO_UNITARIO() {
        return PRECIO_UNITARIO;
    }

    public void setPRECIO_UNITARIO(float PRECIO_UNITARIO) {
        this.PRECIO_UNITARIO = PRECIO_UNITARIO;
    }

    public float getPRECIO_UNITARIO_IMPO() {
        return PRECIO_UNITARIO_IMPO;
    }

    public void setPRECIO_UNITARIO_IMPO(float PRECIO_UNITARIO_IMPO) {
        this.PRECIO_UNITARIO_IMPO = PRECIO_UNITARIO_IMPO;
    }

    public float getIMPUESTO_POR_UNIDAD() {
        return IMPUESTO_POR_UNIDAD;
    }

    public void setIMPUESTO_POR_UNIDAD(float IMPUESTO_POR_UNIDAD) {
        this.IMPUESTO_POR_UNIDAD = IMPUESTO_POR_UNIDAD;
    }

    public float getPCTJE_DSCTO() {
        return PCTJE_DSCTO;
    }

    public void setPCTJE_DSCTO(float PCTJE_DSCTO) {
        this.PCTJE_DSCTO = PCTJE_DSCTO;
    }

    public float getPRECIO_TOTAL_NETO() {
        return PRECIO_TOTAL_NETO;
    }

    public void setPRECIO_TOTAL_NETO(float PRECIO_TOTAL_NETO) {
        this.PRECIO_TOTAL_NETO = PRECIO_TOTAL_NETO;
    }

    public float getPRECIO_MODIFICADO() {
        return PRECIO_MODIFICADO;
    }

    public void setPRECIO_MODIFICADO(float PRECIO_MODIFICADO) {
        this.PRECIO_MODIFICADO = PRECIO_MODIFICADO;
    }
   
    
    
}
