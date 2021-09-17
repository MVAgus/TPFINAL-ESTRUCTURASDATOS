/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidad;
import Entidades.Cliente;
import Entidades.Vuelo;

/**
 *
 * @author mano_
 */
public class ArbolAVL {
    
    private NodoAVL raiz;
    
    public ArbolAVL(){
        this.raiz = null;
       
    }
    
    public boolean insertar(Comparable i, Object dato) {
        //siempre va a retornar true, el elemento repetido no influye

        boolean exito = true;

        if (this.raiz == null) {
            this.raiz = new NodoAVL(i, dato);
//            this.raiz.recalcularAltura(); //no es necesario ya que al ser el primer elemento que se inserta en el arbol, la altura va a ser 0
        } else {
            boolean[] res = new boolean[1];
            res[0] = true;
            this.raiz = insertarAux(this.raiz, i, dato, res);
            if (res[0]) {//este if esta agregado para verificar si el elemento que se quiere ingresar esta repetido ono
                exito = true;
            } else {
                exito = false;
            }

        }

        return exito;
    }

    private NodoAVL insertarAux(NodoAVL n, Comparable elem, Object dato, boolean[] r) {
        if (n != null) {
            if (n.getClave().compareTo(elem) != 0) {

                if (elem.compareTo(n.getClave()) < 0) {
                    if (n.getIzquierdo() != null) {
                        n.setIzquierdo(insertarAux(n.getIzquierdo(), elem, dato, r));
                        n.recalcularAltura(); //con este recalcular lo que hago es actualizar la altura del nodo, dado que se agrego un nuevo HI
                        n = chequearBalance(n);

                    } else {
                        NodoAVL aux = new NodoAVL(elem, dato);
                        n.setIzquierdo(aux);
                        n.recalcularAltura();
                    }
                } else {
                    if (n.getDerecho() != null) {
                        n.setDerecho(insertarAux(n.getDerecho(), elem, dato, r));
                        n.recalcularAltura();
                        n = chequearBalance(n);
                    } else {
                        NodoAVL aux = new NodoAVL(elem, dato);
                        n.setDerecho(aux);

                        n.recalcularAltura();
                    }
                }

            } else {
                r[0] = false;
            }
        }
        return n;

    }
    
  
    
    public boolean eliminar(Comparable buscado){
       boolean exito = false;
       boolean[] res = new boolean[1];
       res[0] = false;
       this.raiz = eliminarAux(this.raiz,buscado,res);
       if (res[0]){
           exito = true;
       }
       
       return exito;
        
    }
    
    
    private NodoAVL eliminarAux(NodoAVL nodoAc, Comparable buscado,boolean[] res){
        
        //nodo r es el nodo que se retorna
        NodoAVL nodoR = nodoAc;
        if (nodoAc != null){
            if (nodoAc.getClave().equals(buscado)){//encontro el elemento a eliminar
                res[0] = true;
                if (nodoAc.getIzquierdo() == null && nodoAc.getDerecho() == null){ //el nodo a eliminar es una hoja
                   nodoR = null;
                } else {
                    if (nodoAc.getIzquierdo() != null && nodoAc.getDerecho() != null){
                        boolean[] exito = new boolean[1];
                        exito[0]= false;
                        nodoAc.setDerecho(eliminarCasoTres(nodoAc,nodoAc.getDerecho(),nodoAc,exito)); //aca ya seteo el derecho ya que al buscar el candidato siempre bajo un nivel
                        //hacia la derecha y luego busco el minimo elemento de
                    } else {
                        if (nodoAc.getIzquierdo() != null){
                            nodoR = nodoAc.getIzquierdo();
                        } else {
                            nodoR = nodoAc.getDerecho();
                        }
                    }
                }
            } else {
                if (buscado.compareTo(nodoAc.getClave()) < 0){
                    nodoAc.setIzquierdo(eliminarAux(nodoAc.getIzquierdo(),buscado,res));
                } else {
                    nodoAc.setDerecho(eliminarAux(nodoAc.getDerecho(),buscado,res));
                }
            }
            if (res[0]){
                nodoAc.recalcularAltura();
                nodoR = this.chequearBalance(nodoAc);
            }
        }
        return nodoR;
    }
    
   private NodoAVL eliminarCasoTres(NodoAVL nodoAc,NodoAVL candidato, NodoAVL padre, boolean[] ex){
       NodoAVL nodoR = candidato;
       if (candidato.getIzquierdo() != null){
           padre = candidato;
           candidato = candidato.getIzquierdo();
           padre.setIzquierdo(eliminarCasoTres(nodoAc,candidato,padre,ex));
           if (ex[0]){
               nodoR.recalcularAltura();
               nodoR = this.chequearBalance(nodoR);
           }
       }
       else {
           ex[0] = true;
           nodoAc.setElem(candidato.getClave());
           nodoAc.setDato(candidato.obtenerDato());
           nodoR = candidato.getDerecho();
       }
       
       return nodoR;
   }
    

 private NodoAVL chequearBalance(NodoAVL nodo) {
    int balancePadre = calcularBalance(nodo), balanceHijo;
        if (balancePadre > 1) {                                              // si el balance es >1 entonces hay que calcular el balance de su hijo izquierdo
            balanceHijo = calcularBalance(nodo.getIzquierdo());
            if (balanceHijo >= 0) {                                           // si el balance de su hijo es mayor a 0 es rotacion simple a la derehca
                nodo = rotacionDerecha(nodo);
            } else {                                                         // sino rotacion doble izquierda - derecha     
                nodo.setIzquierdo(rotacionIzquierda(nodo.getIzquierdo()));
                nodo = rotacionDerecha(nodo);
            }
        } else {
            if (balancePadre < -1) {
                balanceHijo = calcularBalance(nodo.getDerecho());
                if (balanceHijo <= 0) {
                    nodo = rotacionIzquierda(nodo);
                } else {
                    nodo.setDerecho(rotacionDerecha(nodo.getDerecho()));
                    nodo = rotacionIzquierda(nodo);
                }
            }
        }
        return nodo;
    
 }
 
    //--------------Rotaciones-------------------------
   
    private NodoAVL rotacionIzquierda(NodoAVL r) {
        //r es el pivote
        NodoAVL h, temp;

        h = r.getDerecho();
        temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        r.recalcularAltura();
        h.recalcularAltura();
     
        return h;
    }
    

    private NodoAVL rotacionDerecha(NodoAVL r) {
        NodoAVL h, temp;
        h = r.getIzquierdo();
        temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }
    

    
    //----------------------------------------------------------------

    private boolean esHoja(NodoAVL nodo) {
        return (nodo.getIzquierdo() == null && nodo.getDerecho() == null);
    }

    private int calcularBalance(NodoAVL n) {
        int balance = 0;
        if ((n.getIzquierdo() == null) && (n.getDerecho() == null)) {
            balance = 0;
        } else {
            if (n.getIzquierdo() != null) {

                if (n.getDerecho() == null) {
                    balance = n.getIzquierdo().getAltura() + 1;
                } else {
                    balance = n.getIzquierdo().getAltura() - n.getDerecho().getAltura();
                }
            } else {
                if (n.getDerecho() != null) {
                    balance = -1 - n.getDerecho().getAltura();
                }
            }
        }
        return balance;
    }

    

    public boolean esVacio(){
        return this.raiz == null;
    }
    //Metodo minimoElemento
    public Comparable minimoElem() {
        NodoAVL aux = this.raiz;
        Comparable retornado;

        if (this.esVacio()) {
            retornado = null;
        } else {
            while (aux.getIzquierdo() != null) {
                aux = aux.getIzquierdo();
            }
            retornado = aux.getClave();
        }
        return retornado;
    }
    //Metodo maximoElemento
     public Comparable maximoElem() {
        NodoAVL aux = this.raiz;
        Comparable retornado;

        if (this.esVacio()) {
            retornado = null;
        } else {
            while (aux.getDerecho() != null) {
                aux = aux.getDerecho();
            }
            retornado = aux.getClave();
        }
        return retornado;
    }
     
    //**METODO PERTENECE**
    public boolean pertenece(Comparable elem) {
        boolean respuesta;

        if (this.esVacio()) {
            respuesta = false;
        } else {
            respuesta = perteneceAux(this.raiz, elem);
        }
        return respuesta;
    }
    private boolean perteneceAux(NodoAVL nodo, Comparable elem) {
        boolean respuesta = false;
        if (nodo != null) {
            if (elem.compareTo(nodo.getClave())== 0) {
                respuesta = true;
            } else {
                if (elem.compareTo(nodo.getClave()) < 0) {
                    respuesta = perteneceAux(nodo.getIzquierdo(), elem);
                } else {
                    respuesta = perteneceAux(nodo.getDerecho(), elem);
                }
            }
        }
        return respuesta;
    }

    
    //METODO LISTARRANGO
    public Lista listarRango(Comparable e1, Comparable e2) {
        Lista lista = new Lista();

        if (this.raiz != null) {
            listarRangoAux(e1, e2, this.raiz, lista);
        }
        return lista;
    }
    
    private void listarRangoAux(Comparable min, Comparable max, NodoAVL n, Lista ls) {

        if (n != null) {
            if (n.getClave().compareTo(max) < 0) {
                listarRangoAux(min, max, n.getDerecho(), ls);
            }
            if (n.getClave().compareTo(min) >= 0 && n.getClave().compareTo(max) <= 0) {
                //Si el elemento del nodo actual esta dentro del rango lo listo
                ls.insertar(n.getClave(), 1);
            }
            if (n.getClave().compareTo(max) > 0 || n.getClave().compareTo(min) > 0) {
                //el nodo actual es mayor que el max entonces bajo por la izq
                listarRangoAux(min, max, n.getIzquierdo(), ls);
            }
        }
    }
 
    public Lista listarDatos() {
        Lista l = new Lista();
        if (this.raiz != null) {
            listarDatosAux(this.raiz, l);
        }
        return l;
    }
  
    private void listarDatosAux(NodoAVL n, Lista ls) {
        if (n != null) {
            listarDatosAux(n.getIzquierdo(), ls);
            ls.insertar(n.obtenerDato(), ls.longitud() + 1);
            listarDatosAux(n.getDerecho(), ls);
        }
    }
    
    public Object obtenerBuscado(Comparable codVuelo) {
        Object v = obtenerAux(this.raiz, codVuelo);
        return v;
    }

    private Object obtenerAux(NodoAVL n, Comparable codV) {
        //Metodo Privado que busca el nodo donde se encuentra almacenado el elemento 
        //con clave codV
        //que lo contiene. Si no se encuentra buscado devuelve null
        Object resultado = null;

        if (n != null) {

            if (codV.compareTo(n.getClave()) == 0) {
                resultado = n.obtenerDato();
            } else {
                if (codV.compareTo(n.getClave()) > 0){
                    resultado = obtenerAux(n.getDerecho(),codV);
                } else {
                    resultado = obtenerAux(n.getIzquierdo(),codV);
                }
            }
        }
        return resultado;
    }
    
    public HeapMAX objetosDeMayoraMenor(){
        HeapMAX heap = new HeapMAX();
        if (!this.esVacio()){
        heap = mayoraMenorAux(this.raiz,heap);   
        }
     return heap;   
    }
    
    private HeapMAX mayoraMenorAux(NodoAVL n, HeapMAX hp){
        
        if (n != null){
            
            hp.insertar(n.getClave(),n.obtenerDato());
            hp = mayoraMenorAux(n.getIzquierdo(),hp);
            hp = mayoraMenorAux(n.getDerecho(),hp);
        }
        
        return hp;
    }
   
     public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoAVL n) {
        String s = "";
        if (n != null) {

            if (n.getIzquierdo() == null && n.getDerecho() == null) {
                s += n.getClave() + "\n\tHI: - \n\tHD: -" + "\n";
            } else {
                if (n.getIzquierdo() == null && n.getDerecho() != null) {
                    s += n.getClave() + "\n\tHI: - \n\tHD: " + n.getDerecho().getClave() + "\n";
                    s += toStringAux(n.getDerecho());
                } else {
                    if (n.getDerecho() == null && n.getIzquierdo() != null) {
                        s += n.getClave() + "\n\tHI: " + n.getIzquierdo().getClave() + "\n\tHD: -" + "\n";
                        s = s + toStringAux(n.getIzquierdo());
                    } else {

                        s += n.getClave() + "\n\tHI: " + n.getIzquierdo().getClave() + "\n\tHD: " + n.getDerecho().getClave() + "\n";
                        s += toStringAux(n.getIzquierdo());
                        s += toStringAux(n.getDerecho());
                    }
                }
            }

        }
        return s;
    }
//     public boolean eliminar(Comparable elem) {
//        boolean exito = false;
//        if (this.raiz.getClave().compareTo(elem) == 0) {
//            //la raiz es el nodo a eliminar
//            if (this.raiz.getDerecho() == null && this.raiz.getIzquierdo() == null) {
//                this.raiz = null;
//            } else {
//                if (this.raiz.getDerecho() != null && this.raiz.getIzquierdo() == null) {
//                    this.raiz = this.raiz.getIzquierdo();
//                } else {
//                    if (this.raiz.getIzquierdo() != null && this.raiz.getDerecho() == null) {
//                        this.raiz = this.raiz.getDerecho();
//                    } else {
////                        System.out.println("Eliminar caso 3");
//                        eliminarCasoTres(this.raiz);
//                        
//                    }
//                }
//                this.raiz.recalcularAltura();
//                balancearArbol(this.raiz, null);
//                exito = true;
//            }
//        } else {
//
//            if (elem.compareTo(this.raiz.getClave()) < 0) {
//                exito = eliminarAux(this.raiz.getIzquierdo(), elem, this.raiz);
//            } else {
//                exito = eliminarAux(this.raiz.getDerecho(), elem, this.raiz);
//            }
//        }
//        return exito;
//    }
//
//   
//    private boolean eliminarAux(NodoAVL n, Comparable elem, NodoAVL padre) {
//        boolean exito = false;
//
//        if (n != null && !exito) { //con exito detecto si debo seguir o no
//            if (elem.compareTo(n.getClave()) == 0) {
//                exito = true;
//                if (n.getIzquierdo() == null && n.getDerecho() == null) {
//                    // el nodo es hoja, caso 1
//                    eliminarCasoUno(padre, n.getClave());
//                   
//                } else {
//                    if (n.getDerecho() != null && n.getIzquierdo() != null) {
//                        //el nodo tiene 2 hijos, caso 3
//                        eliminarCasoTres(n);
//                        
//                    } else {
//                        //el nodo tiene al menos 1 hijo
//                        if (n.getClave().compareTo(padre.getClave()) > 0) {
//                            //el nodo N es mayor al padre, por lo tanto se encuentra a su derecha Verifico
//                            //si el nodo tiene 1 hijo
//                            eliminarCasoDos(n, padre, true);
//                            padre.recalcularAltura();
//                        } else {
//                            // el nodo N es menor al padre, por lo tanto se encuentra a su izquierda
//                            eliminarCasoDos(n, padre, false);
//                            
//                        }
//                    }
//                }
//            } else {
//                if (elem.compareTo(n.getClave()) < 0) {
//                    //Avanzo por el subarbol izquierdo
//                    exito = eliminarAux(n.getIzquierdo(), elem, n);
//                } else {
//                    //Avanzo por el subarbol derecho
//                    exito = eliminarAux(n.getDerecho(), elem, n);
//                }
//            }
//        }
//        padre.recalcularAltura();
//        balancearArbol(padre, this.raiz);
//        return exito;
//    }
//    private void balancearArbol(NodoAVL n, NodoAVL padre) {
//        //NodoAVL padre = obtenerPadre(this.raiz, n.getElem());
//        int balanceN = calcularBalance(n);
//
//        if (balanceN > 1) {
//            /* arbol caido a la izquierda */
//            if ((calcularBalance(n.getIzquierdo())) < 0) {
//                /* rotacion doble izq ---> derecha */
////                System.out.println("Rotacion doble izq derecha");
//                rotacionDobleIzquierdaDerecha(n, padre);
//            } else {
//                /* rotacion simple a derecha */
////                System.out.println("Rotacion simple a derecha");
//                if (n == this.raiz) {
//                    this.raiz = rotacionSimpleDerecha(n);
//                    this.raiz.recalcularAltura();
//                } else {
//                    padre.setIzquierdo(rotacionSimpleDerecha(n));
//                }
//            }
//        } else {
//            if (balanceN < -1) {
//                /* arbol caido a la derecha */
//                if (calcularBalance(n.getDerecho()) > 0) {
//                    /* rotacion doble derecha izquierda */
////                    System.out.println("rotacion doble derecha izquierda");
//                    rotacionDobleDerechaIzquierda(n, padre);
//                } else {
//                    /* rotacion simple a izquierda */
////                    System.out.println("Rotacion simple a izq");
//                    if (n == this.raiz) {
//                        this.raiz = rotacionSimpleIzquierda(n);
//                        this.raiz.recalcularAltura();
//                    } else {
//                        padre.setDerecho(rotacionSimpleIzquierda(n));
//                    }
//                }
//            }
//        }
//        n.recalcularAltura();
//    }

    
//    private void eliminarCasoUno(NodoAVL padre, Comparable elem) {
//
//        if (padre.getDerecho() != null && (elem.compareTo(padre.getDerecho().getClave()) == 0)) {
//            //La hoja es HD del padre
//            padre.setDerecho(null);
//        } else {
//            //La hoja es HI del padre
//            padre.setIzquierdo(null);
//        }
//    }
//
//    private void eliminarCasoDos(NodoAVL n, NodoAVL padre, boolean derecha) {
//        if (derecha) {
//            if (n.getDerecho() != null) {
//                // el nodo N tiene un solo hijo derecho
//                padre.setDerecho(n.getDerecho());
//            } else {
//                padre.setDerecho(n.getIzquierdo());
//            }
//        } else {
//            if (n.getIzquierdo() != null) {
//                // el nodo N tiene un solo hijo derecho
//                padre.setIzquierdo(n.getIzquierdo());
//            } else {
//                padre.setIzquierdo(n.getDerecho());
//            }
//        }
//    }
//
//    private void eliminarCasoTres(NodoAVL n) {
//        
//        NodoAVL[] arrNodos = buscarCandidatoRecursivo(n.getDerecho(), n);
//        NodoAVL candidato = arrNodos[0];
//        NodoAVL padreCandidato = arrNodos[1];
//        n.setElem((candidato.getClave()));
//        n.setDato(candidato.obtenerDato());
//        
//        
//        if (padreCandidato != null){
//            
//        }
//        if (candidato.getIzquierdo() != null || candidato.getDerecho() != null) {
//            //Si tiene al menos un HI o un HD es caso dos
//            eliminarCasoDos(candidato, padreCandidato, false);
//        } else {
//            eliminarCasoUno(padreCandidato, candidato.getClave());
//        }
//
//    }
//
//    
//    private NodoAVL[] buscarCandidatoRecursivo(NodoAVL n, NodoAVL padre) {
//        NodoAVL arrNodos[] = new NodoAVL[2];
//        NodoAVL candidato = n;
//        NodoAVL father = padre;
//
//        if (candidato.getIzquierdo() != null) {
//         arrNodos = buscarCandidatoRecursivo(candidato.getIzquierdo(),candidato);
//        }
//        else {
//            arrNodos[0] = candidato;
//            arrNodos[1] = father;
//        }
//       
//        return arrNodos;
//    }
}