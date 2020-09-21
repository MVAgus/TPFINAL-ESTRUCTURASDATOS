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
    private String idVuelo;
    private String fecha;
    private int asientosTotales;
    private int asientosVendidos;
    
    public Viaje(String cod, String fecha, int asientosT,int asientosV){
        this.idVuelo = cod;
        this.fecha = fecha;
        this.asientosTotales = asientosT;
        this.asientosVendidos = asientosV;
    }
    public String getIdVuelo(){
        return this.idVuelo;
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

    public int getAsientosVendidos() {
        return asientosVendidos;
    }

    public void setAsientosVendidos(int asientosVendidos) {
        this.asientosVendidos = asientosVendidos;
    }

    @Override
    public String toString() {
        return "Viaje{" + "idVuelo=" + idVuelo + ", fecha=" + fecha + ", asientosTotales=" + asientosTotales + ", asientosVendidos=" + asientosVendidos + '}';
    }
    
    
}
