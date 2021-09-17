
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
    
    public Object obtenerElementoVertice(Object a){
        Object elem = null;
        NodoVert buscado = ubicarVertice(a);
        if (buscado != null){
        elem = buscado.getElem();
        }
        return elem;
    }
    
  
    public boolean modificarEtiquetaArco(Object origen, Object destino, int etiquetaAc, int nuevaEtq) {
        boolean exito = false;

        NodoVert org = this.ubicarVertice(origen);
        if (org != null) {
            NodoVert dest = this.ubicarVertice(destino);
            if (dest != null) {
                //si existe origen y destino
                exito = this.modificarEtiquetaArcoAux(org, destino, etiquetaAc, nuevaEtq); //modifica la etiqueta que va de A--> B
                exito = this.modificarEtiquetaArcoAux(dest, origen, etiquetaAc, nuevaEtq); //modifica la etiqueta que va de B--> A
            }
        }
        return exito;
    }
    
    private boolean modificarEtiquetaArcoAux(NodoVert org, Object destino, int etiquetaAc, int nuevaEtq) {
        boolean exito = false;
        NodoAdy aux = org.getPrimerAdy();
        //caso especial, el primer adyacente es el destino
        if (aux.getVertice().getElem().equals(destino) && aux.getEtiqueta() == etiquetaAc) {
            org.getPrimerAdy().setEtiqueta(nuevaEtq);
            exito = true;
        } else {
        //caso general, tiene que buscar en la lista de adyacentes
            boolean modificado = false;
            NodoAdy aux2 = org.getPrimerAdy();

            while (aux2 != null && !modificado) {
                if (aux2.getVertice().getElem().equals(destino) && aux2.getEtiqueta() == etiquetaAc) {
                    aux2.setEtiqueta(nuevaEtq);
                    modificado = true;
                    exito = true;
                }
                aux2 = aux2.getSigAdyacente();
            }

        }
        return exito;

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
//                insertarArcoAux(origen, destino, etiqueta);
                exito = insertarArcoAux(origen,destino,etiqueta);
            }
        } 
        
        return exito;
    }

    
    private boolean insertarArcoAux(NodoVert origen, NodoVert destino, int etiqueta) {
        boolean repetido = false; //se utiliza para verificar si se trata de un arco repetido
        boolean respuesta = true;
        if (origen.getPrimerAdy() != null) { //si el origen tiene adyacentes recorro la lista de adyacentes y me fijo si no esta repetido el arco
            NodoAdy auxOr = origen.getPrimerAdy();
            while (auxOr.getSigAdyacente() != null && !repetido) {
                auxOr = auxOr.getSigAdyacente();
                if (auxOr.getVertice().getElem().equals(destino.getElem()) && auxOr.getEtiqueta() == etiqueta) {
                    //si el elemento del vertice es igual y la etiqueta tambien, indica que ese arco ya esta cargado en el grafo
                    repetido = true;
                }
            }
            if (!repetido) {
                auxOr.setSigAdyacente((new NodoAdy(destino, etiqueta)));
            }
        } else { //si el origen no tiene adyacentes, seteo el arco como primer adyacente
            origen.setPrimerAdy(new NodoAdy(destino, etiqueta));
        }
        if (destino.getPrimerAdy() != null) { //la misma consideracion que tuve en cuenta para el origen
            NodoAdy aux = destino.getPrimerAdy();
            while (aux.getSigAdyacente() != null & !repetido) { //este repetido sirve cortar el while en el caso de que al tratar de insertar desde origen hacia destino este repetido el arco
                aux = aux.getSigAdyacente();
                if (aux.getVertice().getElem().equals(origen.getElem()) && aux.getEtiqueta() == etiqueta) {
                    repetido = true;
                }
            }
            if (!repetido) {
                aux.setSigAdyacente(new NodoAdy(origen, etiqueta));
            }
        } else {
            destino.setPrimerAdy(new NodoAdy(origen, etiqueta));
        }
        if (repetido) {
            respuesta = false;
        }
        return respuesta;
    }

    
    public boolean eliminarArco(Object ori, Object dest, int etiqueta) {
        boolean exito = false;
        //si no existe el origen y tampoco existe el destino retorna falso
        NodoVert origen = ubicarVertice(ori);
        if (origen != null) {
            exito = eliminarArcoAux(origen, dest, etiqueta);
         
        }
        return exito;
    }
    
    public boolean eliminarArcos(Object ori, Object dest) {
        boolean exito = false;
        NodoVert origen = ubicarVertice(ori);
        if (origen != null) {
            exito = eliminarArcosAux(origen, dest);
        }
        return exito;
    }

    private boolean eliminarArcoAux(NodoVert origen, Object destino, int etq) {
        boolean eliminado = false;
        NodoAdy auxAdyOrigen = origen.getPrimerAdy(); //a partir del origen busco en la lista de adyacentes el nodo destino
        NodoVert verticeDestino;
        NodoAdy adyDest;
        if (auxAdyOrigen.getVertice().getElem().equals(destino) && auxAdyOrigen.getEtiqueta() == etq) {//si el primer adyacente es el nodo a eliminar
            verticeDestino = auxAdyOrigen.getVertice();
            adyDest = verticeDestino.getPrimerAdy();
            eliminarInverso(verticeDestino, adyDest,origen, etq); //antes de eliminar el nodo lo que hago es eliminar su inverso dado que es un grafo bidireccional

            if (auxAdyOrigen.getSigAdyacente() != null) { //si hay mas adyacentes en la lista, se setea como primer adyacente el que le sigue al primero de la lista
                origen.setPrimerAdy(auxAdyOrigen.getSigAdyacente());
            } else {
                origen.setPrimerAdy(null);
            }
            eliminado = true;
        } else {
            while (auxAdyOrigen.getSigAdyacente() != null && !eliminado) {
                if (auxAdyOrigen.getSigAdyacente().getVertice().getElem().equals(destino) && auxAdyOrigen.getSigAdyacente().getEtiqueta() == etq) {

                    verticeDestino = auxAdyOrigen.getSigAdyacente().getVertice();
                    adyDest = verticeDestino.getPrimerAdy();
                    eliminarInverso(verticeDestino, adyDest,origen, etq);
                    if (auxAdyOrigen.getSigAdyacente().getSigAdyacente() != null) {//verifico si existe un nodoAdy siguiente para enlazar
                        auxAdyOrigen.setSigAdyacente(auxAdyOrigen.getSigAdyacente().getSigAdyacente());
                    } else {
                        //si no existe entonces el enlace al siguiente sera nulo
                        auxAdyOrigen.setSigAdyacente(null);
                    }
                    eliminado = true;
                } else {
                    auxAdyOrigen = auxAdyOrigen.getSigAdyacente();
                }
            }
        }
        return eliminado;
    }
    private boolean eliminarArcosAux(NodoVert origen, Object destino) {
        boolean eliminado = false;
        NodoAdy auxAdyOrigen = origen.getPrimerAdy(); //a partir del origen busco en la lista de adyacentes el nodo destino
        NodoVert verticeDestino;
        NodoAdy adyDest;
        if (auxAdyOrigen.getVertice().getElem().equals(destino)) {//si el primer adyacente es el nodo a eliminar
            verticeDestino = auxAdyOrigen.getVertice();
            adyDest = verticeDestino.getPrimerAdy();
            eliminarInversos(verticeDestino, adyDest,origen); //antes de eliminar el nodo lo que hago es eliminar su inverso dado que es un grafo bidireccional

            if (auxAdyOrigen.getSigAdyacente() != null) { //si hay mas adyacentes en la lista, se setea como primer adyacente el que le sigue al primero de la lista
                origen.setPrimerAdy(auxAdyOrigen.getSigAdyacente());
            } else {
                origen.setPrimerAdy(null);
            }
            eliminado = true;
        } else {
            while (auxAdyOrigen.getSigAdyacente() != null && !eliminado) {
                if (auxAdyOrigen.getSigAdyacente().getVertice().getElem().equals(destino)) {

                    verticeDestino = auxAdyOrigen.getSigAdyacente().getVertice();
                    adyDest = verticeDestino.getPrimerAdy();
                    eliminarInversos(verticeDestino, adyDest,origen);
                    if (auxAdyOrigen.getSigAdyacente().getSigAdyacente() != null) {//verifico si existe un nodoAdy siguiente para enlazar
                        auxAdyOrigen.setSigAdyacente(auxAdyOrigen.getSigAdyacente().getSigAdyacente());
                    } else {
                        //si no existe entonces el enlace al siguiente sera nulo
                        auxAdyOrigen.setSigAdyacente(null);
                    }
                    eliminado = true;
                } else {
                    auxAdyOrigen = auxAdyOrigen.getSigAdyacente();
                }
            }
        }
        return eliminado;
    }
    private void eliminarInverso(NodoVert vertD, NodoAdy adyDest,NodoVert origen, int etq) {
        //lo que hace este metodo es eliminar el arco inverso al origen
        //se utiliza en el metodo eliminar arco
        boolean eliminado = false;

        if (adyDest.getVertice().equals(origen) && adyDest.getEtiqueta() == etq) { //si es el primero,lo enlazo con el siguiente
            if (adyDest.getSigAdyacente() != null) {
                vertD.setPrimerAdy(adyDest.getSigAdyacente());
            } else {
                vertD.setPrimerAdy(null);
            }
        } else {
            while (adyDest.getSigAdyacente() != null && !eliminado) {
                if (adyDest.getSigAdyacente().getVertice().equals(origen)) {
                    if (adyDest.getSigAdyacente().getSigAdyacente() != null) {
                        adyDest.setSigAdyacente(adyDest.getSigAdyacente().getSigAdyacente());
                    } else {
                        adyDest.setSigAdyacente(null);
                    }
                    eliminado = true;
                } else {
                    adyDest = adyDest.getSigAdyacente();
                }
            }
        }

    }
    
    private void eliminarInversos(NodoVert vertD, NodoAdy adyDest, NodoVert or) {
        //lo que hace este metodo es eliminar el arco inverso al origen
        //se utiliza en el metodo eliminar arco
        boolean eliminado = false;

        if (adyDest.getVertice().equals(or)) { //si es el primero,lo enlazo con el siguiente
            if (adyDest.getSigAdyacente() != null) {
                vertD.setPrimerAdy(adyDest.getSigAdyacente());
            } else {
                vertD.setPrimerAdy(null);
            }
        } else {
            while (adyDest.getSigAdyacente() != null && !eliminado) {
                if (adyDest.getSigAdyacente().getVertice().equals(or)) {
                    if (adyDest.getSigAdyacente().getSigAdyacente() != null) {
                        adyDest.setSigAdyacente(adyDest.getSigAdyacente().getSigAdyacente());
                    } else {
                        adyDest.setSigAdyacente(null);
                    }
                    eliminado = true;
                } else {
                    adyDest = adyDest.getSigAdyacente();
                }
            }
        }

    }

    public boolean eliminarVertice(Object buscado) {
        boolean exito = false;
        //se verifica si existe el nodo a borrar
        NodoVert borrado = ubicarVertice(buscado);
        if (borrado != null) {
            exito = eliminarVerticeAux(borrado);
        }
        return exito;
    }
    
    private boolean eliminarVerticeAux(NodoVert buscado) {
        boolean eliminado = false;
        NodoVert verticeaBorrar = null;
        if (this.inicio.equals(buscado)) {//el inicio es el nodo a borrar
            verticeaBorrar = this.inicio;
            this.inicio = this.inicio.getSigVertice();
            eliminado = true;
        } else {
            NodoVert aux = this.inicio;
            while (aux.getSigVertice() != null && !eliminado) {
                //recorro la lista de vertices para buscarlo
                if (aux.getSigVertice().equals(buscado)) {
                    verticeaBorrar = aux.getSigVertice(); //vertice a borrar
                    if (aux.getSigVertice().getSigVertice() != null) {
                        aux.setSigVertice(aux.getSigVertice().getSigVertice());
                    } else {
                        aux.setSigVertice(null);
                    }
                    eliminado = true;
                } else {
                    aux = aux.getSigVertice();
                }
            }
        }
        if (eliminado) { //una vez que encontre el vertice y lo desanganche lo que hago es borar sus arcos
            NodoAdy adyacentesABorrar = verticeaBorrar.getPrimerAdy();
            while (adyacentesABorrar != null) {
                Object dest = adyacentesABorrar.getVertice().getElem();
                eliminarArcosAux(verticeaBorrar, dest);
                adyacentesABorrar = adyacentesABorrar.getSigAdyacente();
            }

        }
        return eliminado;
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
                    s += " " + aux2.getVertice().getElem()+ " ("+aux2.getEtiqueta()+")";
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

    //CAMINOS
    
    public boolean caminoPorXcantidadDeVertices(Object origen, Object destino, int maxCantVertices) {
        /*este modulo devuelve v cuando es posible que un cliente que parte del origen
     llege a destino en como maximo X vuelos*/

        Lista fin = new Lista();
        Lista caminoAc = new Lista();
        boolean exito = false;
        NodoVert ori = ubicarVertice(origen);
        if (ori != null) {
            //si encuentro el origen, voy a buscar el destino
            NodoVert dest = ubicarVertice(destino);
            if (dest != null) {
                boolean[] seguir = new boolean[1];
                seguir[0] = true;
                fin = caminoPorXcantidadVerticesAux(ori, destino, caminoAc, new Lista(), 0, maxCantVertices,seguir);
                if (!seguir[0]){
                    exito = true;
                }
                }
        }
        return exito;
    }
    

    private Lista caminoPorXcantidadVerticesAux(NodoVert n, Object dest, Lista caminoAc, Lista fin, int cantVertices, int maxV, boolean[] seguir) {
        //recorrido en profundidad, devuelve el primer camino que encuentra 
        if (n != null) {

            caminoAc.insertar(n.getElem(), caminoAc.longitud() + 1);

            if (n.getElem().equals(dest)) {
                //si vertice n es igual al destino, hay camino
                seguir[0] = false;
                fin = caminoAc.clone();

            } else {
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null && seguir[0]) {
                    if (caminoAc.localizar(ady.getVertice().getElem()) < 0 && cantVertices < maxV) { //verifico que no se pase la cantidad de vertices visitados
                        fin = caminoPorXcantidadVerticesAux(ady.getVertice(), dest, caminoAc, fin, cantVertices + 1, maxV, seguir);
                    }
                    ady = ady.getSigAdyacente();
                }
                caminoAc.eliminar(caminoAc.longitud());
            }

        }
        return fin;
    }

     
    public Lista caminoMasCortoPorVertices(Object origen, Object destino) {
        /*Dados dos aeropuertos A y B, obtener el camino que llegue de A a B pasando por la
        mínima cantidad de aeropuertos*/
        Lista caminoActual = new Lista();
        Lista caminoMasCorto = new Lista();
      
        NodoVert ori = ubicarVertice(origen); //ubica el origen
        if (ori != null) {
            NodoVert dest = ubicarVertice(destino); //ubica el destino
            if (dest != null) {
                
                caminoMasCorto = caminoMasCortoPorVerticesAux(ori, destino,caminoActual,caminoMasCorto);
            }
        }
        
        return caminoMasCorto;
    }
   
    private Lista caminoMasCortoPorVerticesAux(NodoVert n, Object dest, Lista caminoAc, Lista cMasCorto) {

        if (n != null) {

            caminoAc.insertar(n.getElem(), caminoAc.longitud() + 1);
            System.out.println("visitados --> " + caminoAc.toString());
            if (n.getElem().equals(dest)) {
                cMasCorto = caminoAc.clone();

            } else {
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    if (caminoAc.localizar(ady.getVertice().getElem()) < 0) {//si no esta en la lista de visitados
                        if (cMasCorto.esVacia() || caminoAc.longitud() + 1 < cMasCorto.longitud()) {
                            cMasCorto = caminoMasCortoPorVerticesAux(ady.getVertice(), dest, caminoAc, cMasCorto);
                        }

                    }
                    ady = ady.getSigAdyacente();
                }

            }
            caminoAc.eliminar(caminoAc.longitud());

        }
        return cMasCorto;
    }
   
    
    public Lista caminoMasCortoPorminutossquePasaporX(Object origen, Object destino, Object intermedio){
      //Asumi que el camino mas rapido sea por la cantidad de minutos
     int[] ret = new int[2];
     ret[0] = 0;
     ret[1] = 0;
     Lista listR = new Lista();
     Lista caminoActual = new Lista();
        
        NodoVert ori = ubicarVertice(origen);
        if (ori != null) {
            NodoVert dest = ubicarVertice(destino);
            if (dest != null) {
                listR = caminoMasCortoPorminutosquePasaporXAux(ori, destino,intermedio, caminoActual, listR, ret);
            }
        }
        
        return listR;
    
    }
    
    private Lista caminoMasCortoPorminutosquePasaporXAux(NodoVert n, Object dest, Object intermedio, Lista caminoAc, Lista masCorto, int[] cantMinutos) {
        if (n != null) {

            caminoAc.insertar(n.getElem(), caminoAc.longitud() + 1);
            if (n.getElem().equals(dest) && caminoAc.localizar(intermedio) != -1) {
                if (cantMinutos[1] == 0 || cantMinutos[0] < cantMinutos[1]) {
                    //si es el primer camino encontrado o si  la cantidad de minutos del camino mas corto es mayor a la cantidad de 
                    //minutos del actual
                    masCorto = caminoAc.clone();
                    cantMinutos[1] = cantMinutos[0];
                }
                //si vertice n es igual al destino y si el aeropuerto del medio se encuentra en la lista

            } else {
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    if (caminoAc.localizar(ady.getVertice().getElem()) < 0) { //si el nodo a visitar no se encuentra en la lista de visitados
                        if (cantMinutos[0] + ady.getEtiqueta() < cantMinutos[1] || (cantMinutos[1] == 0)) {
                            cantMinutos[0] = cantMinutos[0] + ady.getEtiqueta(); //a la ida va sumando los minutos
                            masCorto = caminoMasCortoPorMinutosAux(ady.getVertice(), dest, caminoAc, masCorto, cantMinutos);
                            cantMinutos[0] = cantMinutos[0] - ady.getEtiqueta(); //a la vuelta va restando los minutos que habia sumado
                        }
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            caminoAc.eliminar(caminoAc.longitud());
        }
        return masCorto;
    }
    
    

    public Lista caminoMasCortoPorMinutos(Object origen, Object destino) {
        Lista caminoMasCorto = new Lista();
        NodoVert ori = ubicarVertice(origen);
        if (ori != null) {
            NodoVert dest = ubicarVertice(destino);
            if (dest != null) {
                int[] ret = new int[2];
                Lista caminoAc = new Lista();
                ret[0] = 0;
                ret[1] = 0;
                caminoMasCorto = caminoMasCortoPorMinutosAux(ori, destino, caminoAc, caminoMasCorto, ret);
            }
    }
        return caminoMasCorto;
    }
    

    private Lista caminoMasCortoPorMinutosAux(NodoVert n, Object dest, Lista caminoAc, Lista cMasCorto, int[] cantMinutos) {
        //cantMinutos es un arreglo que en la posicion 0 lleva la cantidad de minutos del caminoActual
        //y en la posicion 1 la cantidad de minutos del camino mas corto
        if (n != null) {
            caminoAc.insertar(n.getElem(), caminoAc.longitud() + 1);

            if (n.getElem().equals(dest)) { //encontro el camino

                if (cantMinutos[1] == 0 || cantMinutos[0] < cantMinutos[1]) {
                    //si es el primer camino encontrado o si  la cantidad de minutos del camino mas corto es mayor a la cantidad de 
                    //minutos del actual
                    cMasCorto = caminoAc.clone();
                    cantMinutos[1] = cantMinutos[0];
                }

            } else {

                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    if (caminoAc.localizar(ady.getVertice().getElem()) < 0) { //si el nodo a visitar no se encuentra en la lista de visitados
                        if (cantMinutos[0] + ady.getEtiqueta() < cantMinutos[1] || (cantMinutos[1] == 0)) {
                            cantMinutos[0] = cantMinutos[0] + ady.getEtiqueta(); //a la ida va sumando los minutos
                            cMasCorto = caminoMasCortoPorMinutosAux(ady.getVertice(), dest, caminoAc, cMasCorto, cantMinutos);
                            cantMinutos[0] = cantMinutos[0] - ady.getEtiqueta(); //a la vuelta va restando los minutos que habia sumado
                        }
                    }
                    ady = ady.getSigAdyacente();
                }

            }
            caminoAc.eliminar(caminoAc.longitud());
        }
        return cMasCorto;
    }

}
