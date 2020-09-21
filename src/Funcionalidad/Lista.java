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
public class Lista {
    
    private Nodo cabecera;
    private int longitud = 0;
    
    public Lista(){
        this.cabecera = null;
    }
    public int longitud(){
        return this.longitud;
    }
    
    public boolean insertar(Object nuevoElem, int pos) {
        //Inserta el elemento nuevo en la posicion pos
        //detecta y reporta error posicion invalida
        boolean exito = true;

        if (pos < 1 || pos > this.longitud + 1) {
            exito = false;
        } else {
            if (pos == 1) //Crea un nuevo nodo y se enlaza en la cabecera
            {
                this.cabecera = new Nodo(nuevoElem, this.cabecera);
            } else { //avanza hasta el elemento en posicion pos-1
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                //crea el nodo y lo enlaza
                Nodo nuevo = new Nodo(nuevoElem, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
            this.longitud++;
        }
        //Nunca hay error de lista llena, entonces devuelve true
        return exito;
    }
    public boolean eliminar(int pos){
        boolean exito = true;
        if (pos < 1 || pos > this.longitud) {
            exito = false;
        }
        else
        {
            if (pos == 1)
            {
                this.cabecera = this.cabecera.getEnlace();
            }
            else
            {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos-1){
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
                
            }
            this.longitud--;
        }
        return exito;
    }
    
    @Override
    public String toString(){
        String s = "";
        if (this.esVacia())
        {
            s = "[]";
        }
        else
        {
           Nodo aux = this.cabecera;
           int i = 1;
           while (i <= this.longitud && aux != null){
               s+=aux.getElem()+" ";
               i++;
               aux = aux.getEnlace();
           }
        }
        return s;
    }
    public boolean esVacia(){
        return (this.longitud == 0);
    }
    public Object recuperar(int pos){
        //Recupera el elemento de la posicion pos
        Object elemento = null;
        if (this.esVacia() || (pos < 1 || pos > this.longitud)){
        }
        else
        {
            int i = 1;
            Nodo aux = this.cabecera;
            boolean encontrado = false;
            while (i != pos){
                aux = aux.getEnlace();
                i++;
            }
            if (aux != null){
                elemento = aux.getElem();
            }
        }
        return elemento;
    }
    public int localizar(Object elemento){
        //Localiza y devuelve la posicion del elemento buscado
        int pos = -1;
        if (this.esVacia()){  
        }
        else
        {
            Nodo aux = this.cabecera;
            pos = 1;
            boolean seguir = true;
            while (aux != null && seguir){
                if (aux.getElem().equals(elemento)){
                    seguir = false;
                }
                else{
                    aux = aux.getEnlace();
                    pos++;
                }
            }
            if (seguir){
                pos = -1;
            }
        }
        return pos;
    }
    
    public void vaciar(){
        //Tener en cuenta que esto lo hacemos asi solo porque estamos en JAVA
        //y el garbage colector se encarga de llevarse a la basura los demas elementos
        this.cabecera = null;
        this.longitud = 0;
    }
    @Override
    public Lista clone(){
        Lista clon = new Lista();
        if(!this.esVacia()){
            clon.cabecera = new Nodo(this.cabecera.getElem(),null);
            Nodo aux = this.cabecera, aux2 = clon.cabecera;
            clon.longitud++;
            aux = aux.getEnlace();
            
            while (aux != null){
                aux2.setEnlace(new Nodo(aux.getElem(),null));
                aux = aux.getEnlace();
                aux2 = aux2.getEnlace();
                clon.longitud++;
            }
        }
        return clon;
    }
    
    public Lista obtenerMultiplos(int num) {
        Lista listaMul = new Lista();
        Nodo aux = this.cabecera;
        Nodo auxNuevo = listaMul.cabecera;
        int pos = 1;

        while (aux != null) {
            if ((pos % num) == 0) {
                Nodo nuevo = new Nodo(aux.getElem(), null);
                if (listaMul.cabecera == null) {
                    //Primer caso
                    listaMul.cabecera = nuevo;
                    pos++;
                } else {
                    //Caso generico
                    auxNuevo.setEnlace(nuevo);
                }
            auxNuevo = auxNuevo.getEnlace();
            listaMul.longitud++;
            }
            aux = aux.getEnlace();
        }
        return listaMul;
    }
    public void eliminarApariciones(Object x) {
        Nodo aux = this.cabecera;
        while (aux.getEnlace() != null) {

            if (this.cabecera.getElem().equals(x)) {
                this.cabecera = aux.getEnlace();
                aux = aux.getEnlace();
            } else if (aux.getEnlace().getElem().equals(x)) {
                aux.setEnlace(aux.getEnlace().getEnlace());
            } else {
                aux = aux.getEnlace();
            }

        }
        if (this.cabecera.getEnlace() == null) {
            if (this.cabecera.getElem().equals(x)) {
                this.cabecera = null;
            }
        }
    }
}






