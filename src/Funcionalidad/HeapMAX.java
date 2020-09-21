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
public class HeapMAX {
    

    private final int TAMANIO = 100;
    private Object heap[];
    private int ultimo; //La posicion 0 nunca es utilizada
    
    public HeapMAX(){
        this.heap= new Object[this.TAMANIO];
        this.ultimo = 0;
    }
    
    public Object[] recuperarCima(){
        Object c[] = null;
        if (!this.esVacio()){
            c = (Object[])this.heap[1];
            
        }
        return c;
    }
    public boolean esVacio() {
        return (this.ultimo == 0);
    }

    public boolean insertar(Comparable elem, Object objeto) {
        boolean exito = true;
        Object[] obj = new Object[2];
        obj[0] = elem;
        obj[1] = objeto;
        if (this.esVacio()) {
            //Si el heap esta vacio, inserta el arbol en la raiz
       
            this.heap[ultimo + 1] = obj;
            ultimo++;
        } else {
            //Si el arbol tiene al menos un elemento , verifica que haya espacio
            //suficiente en el heap para insertar el elemento
            if (ultimo < TAMANIO) {
                this.heap[ultimo + 1] = obj;
                ultimo++;
                int padre = ultimo / 2;
                //Ubica al elemento en la posicion correcta
                hacerSubir(padre);
            } else {
                exito = false;
            }
        }
        return exito;
    }

    private void hacerSubir(int posPadre) {
        //es decir this.heap[] es un arreglo que en cada una de las posiciones tiene un mini arreglo
        //de 2 elementos.El mini arreglo en la pos 0 tiene la clave que es de tipo comparable y en la pos1 tiene el objeto
        
    
        int posH = this.ultimo;
        
        Object[]objetoPadre = (Object[])this.heap[posPadre];
        Comparable clavePadre = (Comparable)objetoPadre[0];
     
        Object[]objetoNuevo = (Object[])this.heap[posH];
        Comparable claveElemNuevo = (Comparable)objetoNuevo[0];
    
        
        Object[] temp = (Object[])this.heap[posPadre];
        Comparable claveTemp = (Comparable)temp[0];
    
        
        boolean salir = false;
        while (!salir) {
            //Este bucle se repite hasta que el hijo llegue a la raiz
            // o hasta que llegue a una posicion en la cual sea mayor que su padre
            if (posPadre >= 1 && claveElemNuevo.compareTo(clavePadre) > 0) {
                //Si el nuevo elemento tiene mayor valor que su padre lo hacemos subir
                this.heap[posPadre] = objetoNuevo;
                this.heap[posH] = temp;
                posH = posPadre;
                posPadre = posPadre / 2;
                Object[] nuevo = (Object[])this.heap[posPadre];
                temp = nuevo;
            } else {
                salir = true;
            }

        }
    }
    public boolean eliminarCima() {
        boolean exito;
        if (this.esVacio()) {
            //Heap vacio
            exito = false;
        } else {
            //Saca la raiz y pone la ultima hoja en su lugar
            this.heap[1] = this.heap[ultimo];
            this.ultimo--;
            //Restablece la propiedad de heap minimo
            hacerBajar(1);
            exito = true;
        }
        return exito;
    }

     private void hacerBajar(int posPadre) {
        int posH;
        Object[]temp = (Object[])this.heap[posPadre];
        Comparable claveTemp = (Comparable)temp[0];
        
        Object[]hijoDer,hijoIzq;
       
        boolean salir = false;
        while (!salir) {
            posH = posPadre * 2;
            if (posH <= this.ultimo) {
                //temp tiene al menos un hijo (izq) y lo considera menor
                if (posH < this.ultimo) {
                    //hijo menor tiene hermano derecho
                    hijoDer = (Object[])this.heap[posH+1];
                    hijoIzq = (Object[])this.heap[posH];
                    Comparable claveDer,claveIzq;
                    claveDer = (Comparable)hijoDer[0];
                    claveIzq = (Comparable)hijoIzq[0];
                    if (claveDer.compareTo(claveIzq) > 0) {
                        //el hijo derecho es el mayor
                        posH++;
                    }
                }
                //compara al hijo menor con el padre
                Object[] objetoH = (Object[])this.heap[posH];
                Comparable claveH = (Comparable)objetoH[0];
                if (claveH.compareTo(claveTemp) > 0) {
                    //el hijo es menor que el padre, los intercambia
                    this.heap[posPadre] = objetoH;
                    this.heap[posH] = temp;
                    posPadre = posH;
                } else {
                    //el padre es menor que sus hijos , esta bien ubicado
                    salir = true;
                }
            } else {
                //el temp es hoja, esta bien ubicado
                salir = true;
            }
        }

    }
    
    @Override
    public String toString() {
        String s = "";
        int izq, der;
        Object[]objIzq,objDer;
        for (int i = 1; i <= this.ultimo; i++) {
            Object[]n = (Object[])this.heap[i];
            System.out.print("Nodo: " + n[0] + " ");
            izq = i * 2;
            der = izq + 1;
            if (izq <= this.ultimo && this.heap[izq] != null) {
                objIzq = (Object[])this.heap[izq];
                System.out.print("HI: " +objIzq[0]);
            } else {
                System.out.print("HI: -");
            }
            System.out.print("\t");
            if (der <= this.ultimo && this.heap[der] != null) {
                objDer = (Object[])this.heap[der];
                System.out.println("HD: " +objDer[0]);
            } else {
                System.out.println("HD: -");
            }
        }
        return s;
    }
    
    public HeapMAX clone() {
        HeapMAX clonado = new HeapMAX();
        if (!this.esVacio()) {
           clonado.heap = this.heap.clone();
           clonado.ultimo= this.ultimo;
        }
        return clonado;
    }
}

    


