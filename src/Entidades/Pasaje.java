/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.util.Objects;

/**
 *
 * @author mano_
 */
public class Pasaje {
    private String codVuelo;
    private char estado;
    private String fecha;
    private int numAsiento;

    public Pasaje(String codV,String fecha, int numAsiento,char estado) {
        this.codVuelo = codV;
        this.estado = estado;
        this.fecha = fecha;
        this.numAsiento = numAsiento;
    }
    public String getVuelo(){
        return this.codVuelo;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public int getNumAsiento() {
        return numAsiento;
    }

    public void setNumAsiento(int numAsiento) {
        this.numAsiento = numAsiento;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.codVuelo);
        hash = 79 * hash + Objects.hashCode(this.fecha);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pasaje other = (Pasaje) obj;
        if (!Objects.equals(this.codVuelo, other.codVuelo)) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return true;
    }

    
    

   
    
    
    
    
}
