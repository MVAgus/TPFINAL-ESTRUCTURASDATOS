/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;
import Funcionalidad.Lista;

/**
 *
 * @author mano_
 */
public class Vuelo implements Comparable {
    
    private String codVuelo;
    private String aeropuertoOrigen;
    private String aeropuertoDestino;
    private String horaSalida;
    private String horaLlegada;
    private Lista viajes;

    public Vuelo(String codVuelo, String aeropuertoOrigen, String aeropuertoDestino, String horaSalida, String horaLlegada) {
        this.codVuelo = codVuelo;
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.aeropuertoDestino = aeropuertoDestino;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.viajes = new Lista();
    }
    public Lista getViajes(){
        return this.viajes;
    }

    public String getCodVuelo() {
        return codVuelo;
    }

    public String getAeropuertoOrigen() {
        return aeropuertoOrigen;
    }

    public void setAeropuertoOrigen(String aeropuertoOrigen) {
        this.aeropuertoOrigen = aeropuertoOrigen;
    }

    public String getAeropuertoDestino() {
        return aeropuertoDestino;
    }

    public void setAeropuertoDestino(String aeropuertoDestino) {
        this.aeropuertoDestino = aeropuertoDestino;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }
    public int getduracionVuelo(){
        //si la hora es 24:00 es porque se refiere a las 00:00
        int duracion,hS,mS,hL,mL;
        //s1 es un arreglo que guarda los minutos antes del :
        String[]s1 = (this.horaSalida.split(":"));
        hS = Integer.parseInt(s1[0]);
        mS = Integer.parseInt(s1[1]);
        
        String[]s2 = this.horaLlegada.split(":");
        hL = Integer.parseInt(s2[0]);
        mL = Integer.parseInt(s2[1]);
        
        duracion = Math.abs(hL-hS)*60 + Math.abs(mL-mS);
        
        return duracion;
     
    }
    
    public void agregarViaje(Viaje v){
        int i = this.viajes.longitud();
        this.viajes.insertar(v, i+1);
    }

    @Override
    public String toString() {
        return "V: ["+" codVuelo: "+codVuelo+", aeropuertoOrigen:" + aeropuertoOrigen +
                ", aeropuertoDestino:"+aeropuertoDestino + ", horaSalida:"
                + horaSalida + ", horaLlegada:" + horaLlegada +", duracion: "+this.getduracionVuelo()+" } ";
    }
    
    @Override
    public int compareTo(Object v){
        Vuelo vuelo = (Vuelo) v;
        return this.codVuelo.compareTo(vuelo.getCodVuelo());
    }
    
   
}
