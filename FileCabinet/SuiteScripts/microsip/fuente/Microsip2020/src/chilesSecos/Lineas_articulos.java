/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilesSecos;

/**
 *
 * @author jorge
 */
public class Lineas_articulos {
    private  int id = 0;
    private  String nombre = "";
    
    public Lineas_articulos(int id, String nombre) {
        setId(id);
        setNombre(nombre);
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}
