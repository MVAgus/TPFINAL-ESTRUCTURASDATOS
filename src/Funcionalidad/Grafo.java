
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
public class Grafo {
 private NodoVert inicio;
 
 
    public Grafo (){
     this.inicio = null;
    }
    
    public Object obtenerAeropuerto(Object a){
        Object elem = null;
        NodoVert buscado = ubicarVertice(a);
        if (buscado != null){
        elem = buscado.getElem();
        }
        return elem;
    }
   
    public boolean insertarVertice(Object elem) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(elem);
        if (aux == null) {
            this.inicio = new NodoVert(elem, this.inicio);
            exito = true;
        }
        return exito;
    }
    
    private NodoVert ubicarVertice(Object buscado) {

        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean existeVertice(Object buscado) {
        boolean existe = false;
        if (this.inicio != null) {
            existe = existeVerticeAux(buscado);
        }
        return existe;
    }

    private boolean existeVerticeAux(Object buscado) {
        boolean existe = false;
        NodoVert aux = this.inicio;
        while (aux != null && !existe) {
            if (aux.getElem().equals(buscado)) {
                existe = true;
            }
            aux = aux.getSigVertice();
        }
        return existe;
    }
    
    public boolean insertarArco(Object ori, Object des, int etiqueta) {
        boolean exito = false;
        //si no existe el origen y tampoco existe el destino retorna falso
        NodoVert origen = ubicarVertice(ori);
        if (origen != null) {
            NodoVert destino = ubicarVertice(des);
            if (destino != null) {
                //existe origen y destino
                insertarArcoAux(origen, destino, etiqueta);
                exito = true;
            }
        }
        return exito;
    }
     
    private void insertarArcoAux(NodoVert origen, NodoVert destino, int etiqueta) {

        if (origen.getPrimerAdy() == null) {
            //No tiene arcos el vertice, se crea el primer arco
            origen.setPrimerAdy(new NodoAdy(destino, etiqueta));
        } else {
            NodoAdy aux = origen.getPrimerAdy();
            //se busca al ultimo nodo adyacente y se le conecta el nuevo nodo
            while (aux.getSigAdyacente() != null) {
                aux = aux.getSigAdyacente();
            }
            aux.setSigAdyacente((new NodoAdy(destino, etiqueta)));
        }

    }
    public boolean eliminarArco(Object ori, Object des,int etiqueta) {
        boolean exito = false;
        //si no existe el origen y tampoco existe el destino retorna falso
        NodoVert origen = ubicarVertice(ori);
        if (origen != null) {
            NodoVert destino = ubicarVertice(des);
            if (destino != null) {
                if (etiqueta != -1){
                eliminarArcoAuxConEtq(origen,destino,etiqueta);
                } else {
                    eliminarArcoAux(origen,destino);
                }
                exito = true;
            }
        }
        return exito;
    }
    private void eliminarArcoAuxConEtq(NodoVert origen, NodoVert destino,int etq) {
        //modulo privado para eliminar arco
        //concidero que puede haber mas de dos arcos 
        //puede que el origen y el destino no esten conectados mediante un arco, en ese caso no pasa nada
        NodoAdy aux = origen.getPrimerAdy();

        while (aux != null) {
            if (aux.getVertice().equals(destino) && aux.getEtiqueta() == etq) {
                //quiere decir que el primer adyacente es el nodo a eliminar
                if (aux.getSigAdyacente() != null) {
                    origen.setPrimerAdy(aux.getSigAdyacente());
                    aux = origen.getPrimerAdy();
                } else {
                    // en el caso de que sea el unico nodo en la lista, el primer adyacente del vertice queda nulo
                    origen.setPrimerAdy(null);
                    aux = null;
                }
            } else {

                if (aux.getSigAdyacente() != null && aux.getSigAdyacente().getVertice().equals(destino)
                        && aux.getSigAdyacente().getEtiqueta() == etq) {
                    //si el siguiente al adyacente es el elemento a eliminar
                    if (aux.getSigAdyacente().getSigAdyacente() != null) {
                        //si existe nodo para enlazar se conectara a ese
                        aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                    } else {
                        //si no existe entonces el enlace al siguiente sera nulo
                        aux.setSigAdyacente(null);
                    }
                } else {
                    aux = aux.getSigAdyacente();
                }

            }
        }
    }
    private void eliminarArcoAux(NodoVert origen, NodoVert destino){
        NodoAdy aux = origen.getPrimerAdy();

        while (aux != null) {
            if (aux.getVertice().equals(destino)) {
                //quiere decir que el primer adyacente es el nodo a eliminar
                if (aux.getSigAdyacente() != null) {
                    origen.setPrimerAdy(aux.getSigAdyacente());
                    aux = origen.getPrimerAdy();
                } else {
                    // en el caso de que sea el unico nodo en la lista, el primer adyacente del vertice queda nulo
                    origen.setPrimerAdy(null);
                    aux = null;
                }
            } else {

                if (aux.getSigAdyacente() != null && aux.getSigAdyacente().getVertice().equals(destino)) {
                    //si el siguiente al adyacente es el elemento a eliminar
                    if (aux.getSigAdyacente().getSigAdyacente() != null) {
                        //si existe nodo para enlazar se conectara a ese
                        aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                    } else {
                        //si no existe entonces el enlace al siguiente sera nulo
                        aux.setSigAdyacente(null);
                    }
                } else {
                    aux = aux.getSigAdyacente();
                }

            }
        }
    }
      
    public boolean eliminarVertice(Object buscado) {
        boolean exito = false;
        //se verifica si existe el nodo a borrar
        NodoVert borrado = ubicarVertice(buscado);
        if (borrado != null) {
            eliminarVerticeAux(borrado);
            exito = true;
        }
        return exito;
    }

    private void eliminarVerticeAux(NodoVert buscado) {
        NodoVert aux;
        //si el vertice buscado esta como inicio
        if (this.inicio.equals(buscado)) {
            if (this.inicio.getSigVertice() != null) {
                //se asigna el siguiente del inicio como el nuevo inicio
                this.inicio = this.inicio.getSigVertice();
                //luego se elimina los arcos que unen al vertice eliminado
                aux = this.inicio;
                while (aux != null) {
                    eliminarArcoAux(aux, buscado);
                    aux = aux.getSigVertice();
                }
            } else {
                //si el grafo tiene un solo vertice el inicio sera nulo
                this.inicio = null;
            }
        } else {
            aux = this.inicio;
            /*se busca el vertice hasta encontrarlo y luego eliminarlo,
            sino retorna falso*/
            while (aux.getSigVertice() != null) {
                if (aux.getSigVertice().equals(buscado)) {
                    //si el elemento a eliminar tiene un vertice conectado, el vertice anterior se conectara a este
                    if (aux.getSigVertice().getSigVertice() != null) {
                        aux.setSigVertice(aux.getSigVertice().getSigVertice());
                    } else {
                        //sino la conexion al proximo vertice es nula
                        aux.setSigVertice(null);
                        //se elimina los arcos con direccion al vertice a borrar
                        eliminarArcoAux(aux, buscado);
                        //se corta la repeticion porque ya no quedan datos por iterar
                        break;
                    }
                }
                //se elimina los arcos a el nodo borrado
                eliminarArcoAux(aux, buscado);
                aux = aux.getSigVertice();
            }
        }
    }
 
 @Override
    public String toString(){
        return toStringAux();
    }

    private String toStringAux() {
        String s = "";
        if (this.inicio == null) {
            s = "Grafo vacio";
        } else {
            NodoVert aux = this.inicio;
            NodoAdy aux2;
            while (aux != null) {
                //lee el vertice y luego lee sus abyacentes
                s += aux.getElem() + "-->";
                aux2 = aux.getPrimerAdy();
                while (aux2 != null) {
                    s += " " + aux2.getVertice().getElem();
                    aux2 = aux2.getSigAdyacente();
                }
                s += "\n";
                aux = aux.getSigVertice();

            }
        }
        return s;
    }
     public void Vaciar() {
        this.inicio = null;
    }

    public boolean esVacio() {
        boolean vacio = false;
        if (this.inicio == null) {
            vacio = true;
        }
        return vacio;
    }
    
    
    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        NodoVert ori = ubicarVertice(origen);
        if (ori != null) {
            NodoVert dest = ubicarVertice(destino);
            if (dest != null) {
                Lista l = new Lista();
                exito = existeCaminoAux(ori, destino, l);

            }
        }
        return exito;

    }

    private boolean existeCaminoAux(NodoVert n, Object dest, Lista l) {
        boolean exito = false;
        if (n != null) {
            //si vertice n es igual al destino, hay camino
            if (n.getElem().equals(dest)) {
                exito = true;
            } else {
                //si no hay destino verifica si hay camino entre n y destino
                l.insertar(n.getElem(), l.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    if (l.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, l);
                    }
                    ady = ady.getSigAdyacente();
                }
            }

        }
        return exito;
    }
   
    private Lista caminoMasLargoAux(NodoVert n, Object dest, Lista fin, Lista l, int cantVertices) {
        if (n != null) {
            //si vertice n es igual al destino, hay camino
            if (n.getElem().equals(dest)) {
                if (fin.esVacia() || fin.longitud() < cantVertices) {
                    fin = l.clone();
                    fin.insertar(dest, l.longitud() + 1);
                }

            } else {

                l.insertar(n.getElem(), l.longitud() + 1);

                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    if (l.localizar(ady.getVertice().getElem()) < 0) {
                        fin = caminoMasLargoAux(ady.getVertice(), dest, fin, l, cantVertices + 1);
                    }
                    ady = ady.getSigAdyacente();
                }
                l.eliminar(l.longitud());

            }

        }
        return fin;
    }

    public Lista caminoMasLargo(Object origen, Object destino) {
        Lista fin = new Lista();
        NodoVert ori = ubicarVertice(origen);
        if (ori != null) {
            NodoVert dest = ubicarVertice(destino);
            if (dest != null) {
                fin = caminoMasLargoAux(ori, destino, fin, new Lista(), 0);
            }
        }
        return fin;
    }
     public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        //define un vertice donde empezar a recorrer
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert n, Lista l) {
        if (n != null) {
            //marca el vertice n como visitado
            l.insertar(n.getElem(), l.longitud() + 1);
            NodoAdy aux = n.getPrimerAdy();
            while (aux != null) {
                //visita en profundidad los adyacentes de n aun no visitados
                if (l.localizar(aux.getVertice().getElem()) < 0) {
                    listarEnProfundidadAux(aux.getVertice(), l);
                }
                aux = aux.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                listarEnAnchuraAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }

        return visitados;
    }
    private void listarEnAnchuraAux(NodoVert n, Lista visitados) {
        Cola c = new Cola();
        c.poner(n.getElem());
        while (!c.esVacia()) {
            if (visitados.localizar(c.obtenerFrente()) < 0) {
                visitados.insertar(c.obtenerFrente(), visitados.longitud() + 1);
            }
            NodoVert aux = ubicarVertice(c.obtenerFrente());
            c.sacar();
            NodoAdy aux2 = aux.getPrimerAdy();
            while (aux2 != null) {
                if (visitados.localizar(aux2.getVertice().getElem()) < 0) {
                    c.poner(aux2.getVertice().getElem());
                }
                aux2 = aux2.getSigAdyacente();
            }

        }
    }

    private Grafo cloneAux() {
        Grafo clon = new Grafo();
        if (this.inicio != null) {
            clon.inicio = new NodoVert(this.inicio.getElem());
            NodoVert aux = this.inicio.getSigVertice();
            NodoVert auxClon = clon.inicio;
            //se asignan todos los vertices del this grafo
            while (aux != null) {
                auxClon.setSigVertice(new NodoVert(aux.getElem()));
                aux = aux.getSigVertice();
                auxClon = auxClon.getSigVertice();
            }
            //se asignan los vertices adyacentes
            aux = this.inicio;
            auxClon = clon.inicio;
            NodoAdy auxAdy;
            NodoAdy auxAdyClon;
            while (aux != null) {
                auxAdy = aux.getPrimerAdy();
                if (auxAdy != null) {
                    auxClon.setPrimerAdy(adyacenteClon(auxAdy.getVertice().getElem(), clon.inicio, auxAdy.getEtiqueta()));
                    auxAdy = auxAdy.getSigAdyacente();
                    auxAdyClon = auxClon.getPrimerAdy();
                    while (auxAdy != null) {
                        auxAdyClon.setSigAdyacente(adyacenteClon(auxAdy.getVertice().getElem(), clon.inicio, auxAdy.getEtiqueta()));
                        auxAdy = auxAdy.getSigAdyacente();
                        auxAdyClon = auxAdyClon.getSigAdyacente();

                    }
                }
                aux = aux.getSigVertice();
                auxClon = auxClon.getSigVertice();
            }
        }

        return clon;
    }

    private NodoAdy adyacenteClon(Object elem, NodoVert clon, int etiqueta) {
        NodoAdy ret = new NodoAdy(null, 0);

        boolean encontrado = false;
        while (clon != null && !encontrado) {
            if (clon.getElem().equals(elem)) {
                ret = new NodoAdy(clon, etiqueta);
                encontrado = true;
            }
            clon = clon.getSigVertice();
        }

        return ret;
    }

    public Grafo clonar() {
        Grafo clon = cloneAux();
        return clon;
    }

    public Object obtenerLocacion(Object nombre) {
        NodoVert n = ubicarVertice(nombre);
        if (n != null) {
            return n.getElem();
        } else {
            return null;
        }
    }
    public boolean caminoPorXcantidadVuelo(Object origen, Object destino,int maxCantVuelos) {
     
     
        Lista fin = new Lista();
        boolean exito = false;
        NodoVert ori = ubicarVertice(origen);
        if (ori != null) {
            //si encuentro el origen, voy a buscar el destino
            NodoVert dest = ubicarVertice(destino);
            if (dest != null) {
                fin =  caminoPorXcantidadVueloAux(ori,destino,fin,new Lista(),0,maxCantVuelos);
                if (!fin.esVacia()){
                    //si la lista no esta vacia quiere decir que existe el camino
                   if (fin.longitud() <= maxCantVuelos){
                       //si la longitud de la lista es menor que la cantidad maxima de vuelos indica 
                       //que es posible llegar de origen a destino en como maximo maxCantVulos vuelos
                       exito = true;
                   }
                }
                }
        }
  
        return exito;
    }
    

    private Lista caminoPorXcantidadVueloAux(NodoVert n, Object dest,Lista fin,Lista l,int cantVertices,int maxV) {
        if (n != null) {
           
            if (cantVertices <= maxV){ // este if es para no recorrer de mas el grafo
            if (n.getElem().equals(dest)) {
                //si vertice n es igual al destino, hay camino
                if (fin.esVacia() || fin.longitud() > cantVertices) {
                    fin = l.clone();
                    fin.insertar(dest, l.longitud() + 1);
                }

            } else {
    
                l.insertar(n.getElem(), l.longitud() + 1);

                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    if (l.localizar(ady.getVertice().getElem()) < 0) {
                        fin = caminoPorXcantidadVueloAux(ady.getVertice(), dest, fin, l, cantVertices + 1,maxV);
                    }
                    ady = ady.getSigAdyacente();
                }
                l.eliminar(l.longitud());
                
            }
            }
            }
        
        return fin;
    }
    public Lista caminoMasCortoPorAero(Object origen, Object destino) {
        Object[] ret = new Object[2];
        ret[0] = new Lista();
        ret[1] = 0;
        String s;
        NodoVert ori = ubicarVertice(origen);
        if (ori != null) {
            NodoVert dest = ubicarVertice(destino);
            if (dest != null) {
                ret = caminoMasCortoPorAeroAux(ori, destino, ret, new Lista(), 1);
            }
        }
            Lista recorrido = (Lista) ret[0];
            recorrido.insertar(ret[1], recorrido.longitud() + 1);
            
//            if (!recorrido.esVacia()) {
//                s = "El camino mas corto de " + origen + " hasta " + destino + " es: " + recorrido.toString() + " en: " + ret[1] + " kilometros";
//            } else {
//                s = "No existe camino";
//            }

        
        return recorrido;
    }
    
    
    public Object[] caminoMasCortoPorAeroAux(NodoVert n, Object dest, Object[] ret, Lista l, int cantAeropuertos){
        if (n != null) {
            //si vertice n es igual al destino, hay camino

            if (n.getElem().equals(dest)) {
                Lista lis = (Lista) ret[0];
                if ((lis.esVacia()) || (int) ret[1] > cantAeropuertos) {
                    lis = l.clone();
                    lis.insertar(n.getElem(), lis.longitud() + 1);
                    ret[0] = lis;
                    ret[1] = cantAeropuertos;
                }
            } else {

                l.insertar(n.getElem(), l.longitud() + 1);

                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    if (l.localizar(ady.getVertice().getElem()) < 0) {
                        ret = caminoMasCortoPorAeroAux(ady.getVertice(), dest, ret, l, cantAeropuertos+1);
                    }
                    ady = ady.getSigAdyacente();
                }
                l.eliminar(l.longitud());
            }
        }
        return ret;
    }
    
    public Lista caminoMasCortoPorMinutos(Object origen, Object destino) {
        Object[] ret = new Object[2];
        ret[0] = new Lista();
        ret[1] = 0;
        
        NodoVert ori = ubicarVertice(origen);
        if (ori != null) {
            NodoVert dest = ubicarVertice(destino);
            if (dest != null) {
                ret = caminoMasCortoPorMinutosAux(ori, destino, ret, new Lista(), 0);
            }
        }
        Lista recorrido = (Lista) ret[0];
        //en el ultimo elemento de la lista va los kilometros
        recorrido.insertar(ret[1], recorrido.longitud() + 1);

        return recorrido;
    }

    private Object[] caminoMasCortoPorMinutosAux(NodoVert n, Object dest, Object[] ret, Lista l, int minutos) {
        if (n != null) {
            //si vertice n es igual al destino, hay camino

            if (n.getElem().equals(dest)) {
                Lista lis = (Lista) ret[0];
                if ((lis.esVacia()) || (int) ret[1] > minutos) {
                    lis = l.clone();
                    lis.insertar(n.getElem(), lis.longitud() + 1);
                    ret[0] = lis;
                    ret[1] = minutos;
                }

            } else {

                l.insertar(n.getElem(), l.longitud() + 1);

                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    if (l.localizar(ady.getVertice().getElem()) < 0) {
                        ret = caminoMasCortoPorMinutosAux(ady.getVertice(), dest, ret, l, minutos + ady.getEtiqueta());
                    }
                    ady = ady.getSigAdyacente();
                }
                l.eliminar(l.longitud());
            }
        }
        return ret;
    }

}
