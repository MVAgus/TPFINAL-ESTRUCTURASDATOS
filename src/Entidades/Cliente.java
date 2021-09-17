/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Funcionalidad.Lista;
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
    private int cantPasajesComprados = 0; //este atributo lo utilizo para llevar un control de las personas que mas compraron pasaje
  
    
    public Cliente(String tipo, String num){
        this.clave = new ClaveCliente(tipo,num);
    }

    public Cliente(String tipo, String num, String nom,String ape,String fechaNac,String dom, long numTel){
        this.clave = new ClaveCliente(tipo,num);
        this.nombre = nom;
        this.apellido = ape;
        this.fechaNacimiento = fechaNac;
        this.domicilio = dom;
        this.numTelefono = numTel;
    }

    public Comparable getClave() {
        return (this.clave.getTipo()+" "+this.clave.getNum());
    }

    
    public String getNombre() {
        return this.nombre;
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
    
    public void añadirPasaje(){
        this.cantPasajesComprados+=1;
    }
    public int getPasajesComprados(){
        return this.cantPasajesComprados;
    }

    @Override
    public String toString() {
        return "Cliente{" + "clave=" + clave.getTipo()+","+clave.getNum()+ ", nombre=" + nombre + ", apellido=" + apellido + ", fechaNacimiento=" + fechaNacimiento + ", domicilio=" + domicilio + ", numTelefono=" + numTelefono + '}';
    }
    
   

    
    
    
}

