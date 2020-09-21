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
public class Aeropuerto {
    
    private String nombreAeronautico;
    private String ciudad;
    private long numeroTelefono;

    public Aeropuerto(String codVuelo){
        this.nombreAeronautico = codVuelo;
    }
    public Aeropuerto(String nombreAeronautico, String ciudad, long numeroTelefono) {
        this.nombreAeronautico = nombreAeronautico;
        this.ciudad = ciudad;
        this.numeroTelefono = numeroTelefono;
    }
    
    public String getNombreAer(){
        return this.nombreAeronautico;
    }
    
    public String getCiudad() {
        return ciudad;
    }

    public long getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(long numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.nombreAeronautico);
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
        final Aeropuerto other = (Aeropuerto) obj;
        if (!Objects.equals(this.nombreAeronautico, other.nombreAeronautico)) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return "A: {" + "nombreAeronautico=" + nombreAeronautico + ", ciudad=" + ciudad + ", numeroTelefono=" + numeroTelefono + '}';
    }
    
    
    
    
}
