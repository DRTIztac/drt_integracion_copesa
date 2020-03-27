/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microsip2020;

import restletsmx.RestLet_Service;

/**
 *
 * @author jorge pc
 */
public class LineaComunicacion {
    private Coneccion  conex =  null;
    private Dbo4 dbo2 = null;
    private RestLet_Service rest =  null;
    
    public LineaComunicacion() {
    }
    
    public LineaComunicacion(Coneccion  conex,Dbo4 dbo2, RestLet_Service rest) {
        setConex(conex);
        setDbo2(dbo2);
        setRest(rest);
    }

    public Coneccion getConex() {
        return conex;
    }

    public void setConex(Coneccion conex) {
        this.conex = conex;
    }

    public Dbo4 getDbo2() {
        return dbo2;
    }

    public void setDbo2(Dbo4 dbo2) {
        this.dbo2 = dbo2;
    }

    public RestLet_Service getRest() {
        return rest;
    }

    public void setRest(RestLet_Service rest) {
        this.rest = rest;
    }
    
    
}
