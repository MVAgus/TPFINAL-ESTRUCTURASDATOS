/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author mano_
 */
public class Viaje {
    
    private String fecha;
    private int asientosTotales;
    private int asientosVendidos = 0;
    private char estado = 'P';
    
    public Viaje(String fecha, int asientosT){
        
        this.fecha = fecha;
        this.asientosTotales = asientosT;
    }
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getAsientosTotales() {
        return asientosTotales;
    }

    public void setAsientosTotales(int asientosTotales) {
        this.asientosTotales = asientosTotales;
    }
    
    public char getEstado(){
        return this.estado;
    }
    
    public void setEstado(){
        this.estado = 'V';
    }

    public int getAsientosVendidos() {
        return asientosVendidos;
    }

    public void setAsientosVendidos(int asientosVendidos) {
        this.asientosVendidos = asientosVendidos;
    }
    
    public boolean añadirPasajero(){
        boolean resultado = false;
        if (this.asientosVendidos != this.asientosTotales){
            this.asientosVendidos++;
            resultado = true;
        }
    return resultado;
    }
  
    @Override
    public String toString() {
        return "Viaje-> {fecha=" + fecha + ", asientosTotales=" + asientosTotales + ", asientosVendidos=" + asientosVendidos + '}';
    }
   
}
