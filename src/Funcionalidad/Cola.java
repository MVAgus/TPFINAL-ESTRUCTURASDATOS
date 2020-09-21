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
public class Cola {

    private Nodo frente;
    private Nodo fin;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object elem) {
        boolean exito = true;
        //CASO ESPECIAL COLA VACIA
        Nodo nuevo = new Nodo(elem, null);
        if (this.esVacia()) {
            this.fin = nuevo;
            this.frente = nuevo;
        } //CASO GENERAL
        else {
            this.fin.setEnlace(nuevo);
            this.fin = this.fin.getEnlace();
        }
        return exito;
    }

    public boolean esVacia() {
        return (this.frente == null);
    }

    public boolean sacar() {
        boolean exito = true;

        if (esVacia()) {
            //la cola esta vacia reporta error
            exito = false;
        } else {
            //al menos hay un elemento
            //quita el primer elemento y actualiza el frente (y fin si queda vacia)
            this.frente = this.frente.getEnlace();
            if (this.frente == null) {
                this.fin = null;
            }
        }
        return exito;
    }

    public Object obtenerFrente() {
        Object respuesta;
        if (this.esVacia()) {
            respuesta = null;
        } else {
            respuesta = this.frente.getElem();
        }
        return respuesta;
    }

    public boolean vaciar() {
        boolean respuesta;
        if (this.esVacia()) {
            respuesta = false;
        } else {
            this.frente = null;
            this.fin = null;
            respuesta = true;
        }
        return respuesta;
    }

    public Cola clonar() {
        Cola colaClon = new Cola();
        Nodo aux1 = this.frente;
        //Creo el primer nodo de la cola auxiliar
        colaClon.frente = new Nodo(aux1.getElem(), null);
        //Me muevo al 2do nodo de la cola original
        aux1 = aux1.getEnlace();

        Nodo aux2 = colaClon.frente;

        while (aux1 != null) {
            //Crea el nodo y lo enlaza a continuacion de aux2, se repite n-1 veces
            aux2.setEnlace(new Nodo(aux1.getElem(), null));
            aux2 = aux2.getEnlace();
            aux1 = aux1.getEnlace();
        }
        colaClon.fin = aux2;

        return colaClon;
    }

    public Cola clone() {
        //clonar recursivo
        Cola colaClon = new Cola();
        if (!this.esVacia()) {
            colaClon.frente = clonador(this.frente, colaClon);
        }

        return colaClon;
    }

    private Nodo clonador(Nodo nodFrente, Cola cClon) {
        Nodo aux = new Nodo(null, null);
        aux.setElem(nodFrente.getElem());
        if (nodFrente.getEnlace() != null) {
            aux.setEnlace(clonador(nodFrente.getEnlace(), cClon));
        } else {
            //Esta sentencia se encarga de asignar el fin de la cola
            cClon.fin = aux;
        }
        return aux;
    }

    @Override
    public String toString() {
        String s;

        if (this.esVacia()) {
            s = "[]";
        } else {
            s = "[";
            Nodo aux = this.frente;
            while (aux != null) {
                //agrega el texto del elemento que tiene el nodo y avanza
                s += " " + aux.getElem();
                aux = aux.getEnlace();
            }
            s += "]";
        }
        return s;
    }
}
