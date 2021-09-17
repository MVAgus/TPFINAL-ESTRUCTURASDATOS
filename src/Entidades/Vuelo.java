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
    //El vuelo viene a hacer la ruta entre dos aeropuertos
    private String codVuelo;
    private String aeropuertoOrigen;
    private String aeropuertoDestino;
    private String horaSalida;
    private String horaLlegada;
    private Lista viajesDeCadaDia; //esta lista representa la cantidad de viajes que se realizan en esa ruta

    public Vuelo(String codVuelo, String aeropuertoOrigen, String aeropuertoDestino, String horaSalida, String horaLlegada) {
        this.codVuelo = codVuelo;
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.aeropuertoDestino = aeropuertoDestino;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.viajesDeCadaDia = new Lista();
    }
    
    public Lista getViajes(){
        return this.viajesDeCadaDia;
    }
    
    public boolean viajeTienePasajesVendidos(String fecha){
        Viaje v;
        boolean buscado = false,resultado = false;
        int i = 1;
        
        while (!buscado){
            v = (Viaje)this.viajesDeCadaDia.recuperar(i);
            if (v.getFecha().compareTo(fecha) == 0){
                buscado = true;
                //encontro el viaje, se fija si tiene pasajes vendidos
                if (v.getAsientosVendidos() > 0){
                    //se vendio al menos un pasaje
                    resultado = true;
                }
            }
            i++;
        }
        return resultado;
    }
    
    public int obtenerViajeBuscado(String fecha){
        /*este modulo recorre la lista de viajes 
        que tiene asociada un vuelo para encontrar el viaje con la fecha indicada*/
        boolean resultado = false;
        int j = 1, posicionBuscado = 0,longitudLista;
        longitudLista = this.viajesDeCadaDia.longitud();
        while (j <= longitudLista && !resultado) {
            Viaje v = (Viaje)this.viajesDeCadaDia.recuperar(j);
            if (v.getFecha().compareTo(fecha) == 0){
                resultado = true;
                posicionBuscado = j;
            }
            j++;
        }
        if (!resultado){
            posicionBuscado = -1;
        }
     return posicionBuscado;   
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
        
        String s;
        //s1 es un arreglo que guarda los minutos antes del :
        String[]s1 = (this.horaSalida.split(":"));
        hS = Integer.parseInt(s1[0]);
        mS = Integer.parseInt(s1[1]);
        
        String[]s2 = this.horaLlegada.split(":");
        hL = Integer.parseInt(s2[0]);
        s = s2[1];
        mL = Integer.valueOf(s);
//        mL = Integer.parseInt(s2[1]);
        
        duracion = Math.abs(hL-hS)*60 + Math.abs(mL-mS);
        
        return duracion;
     
    }
    
    public void agregarViaje(Viaje v){
        int i = this.viajesDeCadaDia.longitud();
        this.viajesDeCadaDia.insertar(v, i+1);
    }

    @Override
    public String toString() {
        return "Vuelo: ["+" codVuelo: "+codVuelo+", aeropuertoOrigen:" + aeropuertoOrigen +
                ", aeropuertoDestino:"+aeropuertoDestino + ", horaSalida:"
                + horaSalida + ", horaLlegada:" + horaLlegada +", duracion: "+this.getduracionVuelo()+" } Viajes: "+"\n"+
                this.toStringViaje();
    }
    
    private String toStringViaje(){
        String s = "";
        int i = 1, j = this.viajesDeCadaDia.longitud();
        
        while (i <= j){
            Viaje v = (Viaje)this.viajesDeCadaDia.recuperar(i);
            s+= v.toString()+"\n";
            i++;
        }
        return s;
        
    }
    
    @Override
    public int compareTo(Object v){
        Vuelo vuelo = (Vuelo) v;
        return this.codVuelo.compareTo(vuelo.getCodVuelo());
    }
   
}
