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
 class Nodo {
    private Object elem;
    private Nodo enlace;
    
    //constructor
    
    public Nodo(Object elem, Nodo enlace){
        this.elem = elem;
        this.enlace = enlace;
    }


    public void setElem(Object elem) {
        this.elem = elem;
    }
    public Object getElem() {
        return elem;
    }
    public void setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }
    public Nodo getEnlace() {
        return enlace;
    }
     
}

