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
public class Cliente {
    private ClaveCliente clave;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String domicilio;
    private long numTelefono;
    
    public Cliente(String tipo, String num){
        this.clave = new ClaveCliente(tipo,num);
    }

    public Cliente(String tipo, String num, String nom,String ape,String fechaNac,String dom, long numTel){
        this.clave = new ClaveCliente(tipo,num);
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNac;
        this.domicilio = dom;
        this.numTelefono = numTel;
    }

    public ClaveCliente getClave() {
        return clave;
    }

    public void setClave(ClaveCliente clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public long getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(long numTelefono) {
        this.numTelefono = numTelefono;
    }

    @Override
    public String toString() {
        return "Cliente{" + "clave=" + clave + ", nombre=" + nombre + ", apellido=" + apellido + ", fechaNacimiento=" + fechaNacimiento + ", domicilio=" + domicilio + ", numTelefono=" + numTelefono + '}';
    }
    
   

    
    
    
}

