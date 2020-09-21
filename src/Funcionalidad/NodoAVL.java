/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidad;

/**
 *
 * @author mano_
 */
 class NodoAVL {
     
     private Comparable clave;
     private Object dato;
     private int altura;
     private NodoAVL izquierdo;
     private NodoAVL derecho;
     
     public NodoAVL (Comparable clav, Object d){
         this.clave = clav;
         this.dato = d;
         this.altura = 0;
         this.izquierdo = null;
         this.derecho = null;
     }
     public Object obtenerDato(){
         return this.dato;
     }

    public Comparable getClave() {
        return clave;
    }

    public void setElem(Comparable clav) {
        this.clave = clav;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public NodoAVL getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoAVL izquierdo) {
        this.izquierdo = izquierdo;
        if (izquierdo != null){
            izquierdo.recalcularAltura();
        }
        this.recalcularAltura();
    }

    public NodoAVL getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoAVL derecho) {
        this.derecho = derecho;
        if (derecho != null){
            derecho.recalcularAltura();
        }
        this.recalcularAltura();
    }
    
    public void recalcularAltura() {
        if (this.getIzquierdo() != null) {
            if (this.getDerecho() != null) {
                altura = 1 + Math.max(this.getIzquierdo().getAltura(), this.getDerecho().getAltura());
            } else {
                altura = 1 + this.getIzquierdo().getAltura();
            }
        } else {
            if (this.getDerecho() != null) {
                altura = 1 + this.getDerecho().getAltura();
            } else {
                if (this.getDerecho() == null) {
                    altura = 0;
                }
            }
        }
        
    }
 
 
}
            
          
