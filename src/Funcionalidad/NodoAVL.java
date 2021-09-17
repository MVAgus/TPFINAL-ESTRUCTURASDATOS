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
     
     
     public NodoAVL(){
         
     }
     public NodoAVL (Comparable clav, Object d){
         this.clave = clav;
         this.dato = d;
         this.altura = 0;
         this.izquierdo = null;
         this.derecho = null;
     }
    

    public Comparable getClave() {
        return clave;
    }

    public void setElem(Comparable clav) {
        this.clave = clav;
    }
    
    public void setDato(Object d){
        this.dato = d;
    }
    public Object obtenerDato(){
        return this.dato;
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
        
//        if (izquierdo != null){
//            //una vez que se agrego un hijo, al hizo izq recalculo la altura
//            izquierdo.recalcularAltura();
//        }
//        this.recalcularAltura();
    }

    public NodoAVL getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoAVL derecho) {
        this.derecho = derecho;
//        if (derecho != null){
//            derecho.recalcularAltura();
//        }
//   this.recalcularAltura();
    }
    
    public void recalcularAltura() {
        if (this.getIzquierdo() != null) {
            if (this.getDerecho() != null) {
                altura = 1 + Math.max(this.getIzquierdo().getAltura(), this.getDerecho().getAltura());
            } else {
                altura = 1 + this.izquierdo.altura;
            }
        } else {
            if (this.getDerecho() != null) {
                altura = 1 + this.derecho.altura;
            } else {
                if (this.getDerecho() == null) {
                    altura = 0;
                }
            }
        }
        
    }
     
  
}
 
 

            
          
