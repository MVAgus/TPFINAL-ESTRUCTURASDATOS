/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidad;
import Entidades.Aeropuerto;
import Entidades.Vuelo;
import java.util.HashMap;
import Entidades.Cliente;
import Entidades.Pasaje;
import Entidades.Viaje;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



/**
 *
 * @author mano_
 */
public class SistemaViajes {
    private Grafo mapaAeropuertos;
    private ArbolAVL tablaVuelos;
    private ArbolAVL tablaClientes;
    private HashMap mapeoPasajes;
    

    //el mapeo de pasajes lo hace con dominio cliente y rango lista de pasajes
    //falta estructura auxiliar para listado
    
    public SistemaViajes (){
        //constructor
        this.mapaAeropuertos = new Grafo();
        tablaVuelos = new ArbolAVL();
        tablaClientes = new ArbolAVL();
        mapeoPasajes = new HashMap<Comparable,Lista>();
        
    }
    
    private void escribir(String cadena) {
        //Método que escribe en un archivo de texto lo que se va ejecutando
        File archivo = new File("C:\\Users\\mano_\\Documents\\cargaDatosTPEDD\\Escritura1.txt");
        try {
            FileWriter escribirArchivo = new FileWriter(archivo, true);
            try (BufferedWriter buffer = new BufferedWriter(escribirArchivo)) {
                buffer.write(cadena);
                buffer.newLine();
            }
        } catch (IOException ex) {
        }
    }
    
    
    //ALTA aeropuertos
    public boolean agregarAeropuerto(String nomAer, String ciudad, long telefono){
        //Alta de aeropuertos
        boolean exito;
        exito = this.mapaAeropuertos.insertarVertice(new Aeropuerto(nomAer,ciudad,telefono));
        if (exito){
            escribir("Se agrego el aeropuerto: "+ nomAer);
        } else {
            escribir("No se agrego el aeropuerto "+nomAer);
        }
        return exito;
    }
    //BAJA aeropuertos
    public boolean eliminarAeropuerto(String nomAer){
        //Baja de aeropuertos
        boolean existe;
        existe = this.mapaAeropuertos.existeVertice(new Aeropuerto(nomAer));
        if (existe){
            this.mapaAeropuertos.eliminarVertice(new Aeropuerto(nomAer));
            escribir("Se elimino con exito el aeropuerto "+nomAer);
        } else {
            escribir("No se pudo eliminar el aeropuerto "+nomAer);
        }
        return existe;
    }
    
    //MODIFICACION aeropuertos
    public boolean modificarAeropuerto(String nomAer,long numTelefono){
        //Supongo que solo se puede modificar el num de telefono del aeropuerto
        boolean exito = false;
        Aeropuerto a = (Aeropuerto)this.mapaAeropuertos.obtenerAeropuerto(new Aeropuerto(nomAer));
        if (a != null){
            a.setNumeroTelefono(numTelefono);
            exito = true;
            escribir("Se modifico con exito el telefono de contacto del aeropuerto "+a.getNombreAer());
        } 
        return exito;
    }
    
    //CONSULTAS DE AEROPUERTOS
    
    public String mostrarInfoAeropuerto(String codAeropuerto){
        String s = "";
        Aeropuerto a = (Aeropuerto)this.mapaAeropuertos.obtenerAeropuerto(new Aeropuerto(codAeropuerto));
        if (a != null){
            s = a.toString();
        } else{
            escribir("No se encontro el aeropuerto con ese codigo");
            System.out.println("No se encontro al aeropuerto con ese codigo");
        }
        return s;
    }
    
    

    //ALTA vuelos
    public boolean agregarVuelo(String codVuelo, String origen, String destino,String horaSalida,String horaLlegada ) {
        //Verifico que existan los aeropuertos origen y destino luego añado el arco
        boolean exito;
        Vuelo v = new Vuelo(codVuelo,origen,destino,horaSalida,horaLlegada);
        int duracion = v.getduracionVuelo();
        exito = this.mapaAeropuertos.insertarArco(new Aeropuerto(origen), new Aeropuerto(destino), duracion);
        if (exito){
            this.tablaVuelos.insertar(codVuelo,v);
            escribir("Se agrego el vuelo : "+codVuelo);
        } else {
            escribir("No se pudo agregar el vuelo: "+codVuelo);
        }
        return exito;
    }
    
    //BAJA vuelos
    public boolean eliminarVuelo(String codVuelo, String origen, String destino){
        //debo buscar el vuelo correcto 
        
        Vuelo buscado = (Vuelo)this.tablaVuelos.obtenerBuscado(codVuelo);
        int etiqueta = buscado.getduracionVuelo();
        
        //elimina el arco
        boolean exito = this.mapaAeropuertos.eliminarArco(new Aeropuerto(origen),new Aeropuerto(destino),etiqueta);
        if (exito){
            //elimina el vuelo de la tabla de vuelos
            this.tablaVuelos.eliminar(codVuelo);
            eliminarPasajesTotales(codVuelo);          
            escribir("Se elimino con exito vuelo desde: "+origen+" hacia: "+destino);
        } else {
            escribir("No se pudo eliminar vuelo desde: "+origen+" hacia: "+destino);
        }
        return exito;
    }
    public void eliminarPasajesTotales(String codVuelo){
        int longitudL = 0, i = 1;
        boolean encontrado = false;
        Lista l;
        Pasaje p;
        for (Object value : this.mapeoPasajes.values()) {
            l = (Lista)value;
            if (!l.esVacia()){
            longitudL = l.longitud();
            while (i <= longitudL && !encontrado){
                p = (Pasaje)l.recuperar(i);
                if (p != null){
                    if (p.getVuelo().equals(value)){
                        l.eliminar(i);
                    }
                }
                i++;
            }
            }
            i= 1;
        }
        
    }
    
    //MODIFICACION vuelos
    public boolean modificarVuelo(String codVuelo,String origen,String destino, String horaLlegada, String horaSalida) {
        int nuevaDuracion,duracion;
        boolean exito = false;
        Vuelo buscado = (Vuelo)this.tablaVuelos.obtenerBuscado(codVuelo);
        if (buscado != null) {//Encontro el vuelo
            //Vuelo v = (Vuelo);
            if (origen.length() != 0 ){
                buscado.setAeropuertoOrigen(origen);
                this.mapaAeropuertos.eliminarArco(origen,buscado.getAeropuertoDestino(),buscado.getduracionVuelo());
            }
            if (destino.length() != 0){
                buscado.setAeropuertoDestino(destino);
                this.mapaAeropuertos.eliminarArco(buscado.getAeropuertoOrigen(),destino,buscado.getduracionVuelo());
            }
            if (horaLlegada.length() != 0){
                duracion = buscado.getduracionVuelo();
                buscado.setHoraLlegada(horaLlegada);
                nuevaDuracion = buscado.getduracionVuelo();
                this.mapaAeropuertos.insertarArco(buscado.getAeropuertoOrigen(),buscado.getAeropuertoDestino(), nuevaDuracion);
                this.mapaAeropuertos.eliminarArco(buscado.getAeropuertoOrigen(),buscado.getAeropuertoDestino(), duracion);
            }
            if (horaSalida.length() != 0){
                duracion = buscado.getduracionVuelo();
                buscado.setHoraSalida(horaSalida);
                nuevaDuracion = buscado.getduracionVuelo();
                this.mapaAeropuertos.insertarArco(buscado.getAeropuertoOrigen(),buscado.getAeropuertoDestino(),nuevaDuracion);
                this.mapaAeropuertos.eliminarArco(buscado.getAeropuertoOrigen(),buscado.getAeropuertoDestino(), duracion);
            }
            if (origen.length() != 0 && destino.length() != 0 && horaLlegada.length() != 0 && horaSalida.length() != 0){
               String oriAux,destAux;
               oriAux = buscado.getAeropuertoOrigen();
               destAux = buscado.getAeropuertoDestino();
               int etq = buscado.getduracionVuelo();
               buscado.setAeropuertoOrigen(origen);
               buscado.setAeropuertoDestino(destino);
               buscado.setHoraSalida(horaSalida);
               buscado.setHoraLlegada(horaLlegada);
               this.mapaAeropuertos.eliminarArco(oriAux,destAux,etq);
            }
            escribir("Se modifico con exito el vuelo: " + codVuelo);
        } else {
            escribir("No se pudo modificar el vuelo " + codVuelo);
        }

        return exito;
    }
    
    //ALTA clientes
    public void agregarCliente(String tipo, String num, String nombre, String apellido, String fechaNac, String domicilio,
            long numTelefono) {
    
        Cliente c = new Cliente(tipo, num);
        if (verificarFecha(fechaNac)) {
            completarDatosCliente(c, nombre, apellido, fechaNac, domicilio, numTelefono);
            this.tablaClientes.insertar(c.getClave(), c);
            escribir("Se agrego con exito el cliente: " + nombre + " " + apellido);
            
            this.mapeoPasajes.put(c.getClave(), new Lista());
      
        } else {

            escribir("No se pudo agregar el cliente : " + nombre + " " + apellido+ " la fecha de nacimiento no corresponde"
                    + " a una fecha valida");
        }
    }
   
    private void completarDatosCliente(Cliente c,String nombre, String apellido,String fechaNac,String domicilio,long numTel){
        c.setNombre(nombre);
        c.setApellido(apellido);
        c.setFechaNacimiento(fechaNac);
        c.setDomicilio(domicilio);
        c.setNumTelefono(numTel);
    }
    public boolean verificarCliente(String tipo, String num){
        boolean exito;
        Cliente c = new Cliente(tipo,num);
        exito = this.tablaClientes.pertenece(c.getClave());
        return exito;
    }
    
    public boolean eliminarCliente(String tipo,String num){
        boolean exito;
        Cliente c = new Cliente(tipo,num);
        exito = this.tablaClientes.eliminar(c.getClave());
        if (exito){
             escribir("Se elimino con exito al cliente: "+tipo+" , "+num);
        }
        else {
             escribir("No se pudo eliminar al cliente: "+tipo+" , "+num);
        }
        return exito;
       
    }
    
    public boolean modificarCliente(String tipo,String num,String domicilio,long tel){
        boolean exito = false;
        Cliente c = new Cliente(tipo,num);
        Cliente buscado = (Cliente)this.tablaClientes.obtenerBuscado(c.getClave());
        if (buscado != null){
            buscado.setDomicilio(domicilio);
            buscado.setNumTelefono(tel);
            exito = true;
            escribir("Se modificaron con exito los datos del cliente: "+buscado.getNombre());
        }
        else {
            escribir("No se encontro al cliente con los datos solicitados");
        }
        return exito;
    }
    
    public boolean agregarPasaje(String tipo, String num, String codVuelo, String fecha, int numAsiento, char estado) {
        //Verifico si existe el cliente y tambien debo verificar que no sea un pasaje repetido
        
        boolean existePasaje = false;
        Cliente c = new Cliente(tipo, num);
//        Lista l = this.tablaClientes.listarDatos();

        c = (Cliente) this.tablaClientes.obtenerBuscado(c.getClave());
        if (c != null) {
            //existe el cliente
            if (verificarFecha(fecha)) {
                //existe la fecha
                Object o = this.mapeoPasajes.get(c.getClave());
                Lista l = (Lista) o;
                if (l.esVacia()) {
                    Pasaje p = new Pasaje(codVuelo, fecha, numAsiento, estado);
                    //la lista de pasajes esta vacia entonces creo una lista y agrego al pasaje
                    l.insertar(p, l.longitud()+1);
                 
                } else {

                    Pasaje aux = (Pasaje) buscarPasaje(l, codVuelo, fecha, numAsiento);
                    if (aux == null) {

                        Pasaje nuevo = new Pasaje(codVuelo, fecha, numAsiento, estado);
                        l.insertar(nuevo,l.longitud()+1);
//                        this.mapeoPasajes.put(c.getClave(), l.insertar(nuevo, l.longitud() + 1));
                        escribir("Se agrego el pasaje del cliente " + c.getNombre() + " " + c.getApellido() + " con exito ");
                    } else {
                        existePasaje = true;
                        escribir("Error, el pasaje con los datos ingresados ya se encuentra en el sistema");
                    }
                }
            } else {
                escribir("La fecha del pasaje ingresado no es valida");
            }
        } else {
            escribir("No se encontro al cliente con tipo: " + tipo + " y num: " + num);
        }
        return existePasaje;
    }
        
    private Object buscarPasaje(Lista l, String codVuelo, String fecha, int numAsiento) {
        int longLt = l.longitud();
        Object buscado = null;
        int j = 1;
        //la lista de pasajes contiene al menos un elemento, debo verificar que no exista repetido
        while (j <= longLt && buscado == null) {
            Pasaje aux = (Pasaje) l.recuperar(j);
            if (aux.getVuelo().equals(codVuelo) && aux.getFecha().equals(fecha) && aux.getNumAsiento() == numAsiento) {
                buscado = aux;
                escribir("El pasaje con los datos ingresado ya se encuentra en el sistema");
            }
            j++;
        }
        return buscado;

    }
    public boolean eliminarPasaje(String tipo, String num, String codVuelo, String fecha, char estado, int numAsiento) {
        boolean exito = false;
        Cliente c = new Cliente(tipo, num);
        c = (Cliente) this.tablaClientes.obtenerBuscado(c.getClave());
        if (c != null) {
            if (verificarFecha(fecha)){
            boolean eliminado = false;
            Lista l = (Lista) this.mapeoPasajes.get(c.getClave());
            int longitud = l.longitud();
            int j = 1;
            while (j <= longitud && !eliminado) {
                Pasaje p = (Pasaje) l.recuperar(j);
                if (p.getVuelo().equals(codVuelo) && p.getFecha().equals(fecha) && p.getNumAsiento() == numAsiento) {
                    l.eliminar(j);
                    eliminado = true;
                    escribir("Se elimino con exito el pasaje del cliente: " + c.getNombre() + " " + c.getApellido());
                }
                j++;
            }
            } else {
                escribir("La fecha ingresada no es valida");
            }
        } else {
            escribir("No se encontro al cliente con tipo: " + tipo + " y num: " + num);
        }
        return exito;
    }
    
    public boolean modificarPasaje(String tipo, String num, String codVuelo, String fecha, char estado, int numAsiento) {
        //busco al pasaje por el codigo de vuelo y la fecha
        //modifico el estado y el num de asiento
        boolean encontrado = false;
        Cliente c = new Cliente(tipo, num);
        c = (Cliente) this.tablaClientes.obtenerBuscado(c.getClave());
        if (c != null) {
            if (verificarFecha(fecha)) {
                Lista l = (Lista) this.mapeoPasajes.get(c.getClave());
                Pasaje p = (Pasaje) buscarPasaje(l, codVuelo, fecha, numAsiento);
                if (p != null) {
                    p.setEstado(estado);
                    encontrado = true;
                    escribir("Se modifico con exito el pasaje: " + p.getVuelo() + " del cliente: "
                            + c.getNombre() + " " + c.getApellido());
                } else {
                    escribir("No se encontro el pasaje solicitado");
                }
            } else {
                escribir("La fecha ingresada no es valida");
            }
        } else {
            escribir("No se ha encontrado al cliente ingresado");
        }
        return encontrado;
    }
    
    
    //CONSULTAS SOBRE CLIENTES
    public void mostrarCiudadesVisitadas(Object c) {
        String ciudades = "";
        String vuelo;
        Pasaje p;
        Cliente cl = (Cliente) c;
        if (this.tablaClientes.pertenece(cl.getClave())) {
            Lista l = (Lista) this.mapeoPasajes.get(cl.getClave());
            if (!l.esVacia()) {
                int i = 1;
                int longitudL = l.longitud();
                while (i <= longitudL) {
                    p = (Pasaje) l.recuperar(i);
                    vuelo = p.getVuelo();
                    Vuelo v = (Vuelo) this.tablaVuelos.obtenerBuscado(vuelo);
                    //buscar una forma de eliminar ciudades repetidas
                    ciudades += v.getAeropuertoDestino() + " ";
                    i++;
                }
                escribir(ciudades);
            } else {
                escribir("El cliente " + cl.getNombre() + " " + cl.getApellido() + "todavia no ha viajado en ningun"
                        + "vuelo");
            }
        } else {
            escribir("El cliente ingresado no se encuentra almacenado en el sistema");
        }

    }
    public Object obtenerCliente(String tipo, String num) {
        Cliente c = new Cliente(tipo, num);
        c = (Cliente) this.tablaClientes.obtenerBuscado(c.getClave());
        return c;

    }
    public void verfyMostrarInformacionCliente(String tipo, String num) {
        //Verifica y muestra informacion de contacto de un cliente
        //Tambien muestra un listado de los pasajes pendientes de volar
        Cliente c = new Cliente(tipo, num);
        Lista pasajesPendientes = new Lista();
        String s = null;
        int j = 0;
        c = (Cliente) this.tablaClientes.obtenerBuscado(c.getClave());
        if (c != null) {
            Lista l = (Lista) this.mapeoPasajes.get(c.getClave());
            if (!l.esVacia()) {
                int i = 1;
                int tam = l.longitud();
                while (i <= tam) {
                    Pasaje p = (Pasaje) l.recuperar(i);
                    if (p.getEstado() == 'P') {
                        pasajesPendientes.insertar(p, j + 1);
                        j = j + 1;
                        s += p.toString() + " ";
                    }
                    i++;
                }
            } else {
                escribir("El cliente " + c.getNombre() + " " + c.getApellido() + " no posee pasajes a su nombre");
            }
            escribir("Cliente: " + c.getNombre() + " " + c.getApellido() + " domicilio: " + c.getDomicilio() + " telefono: " + c.getNumTelefono());
            if (s != null) {
                escribir("Pasajes: " + s);
            }
        } else {
            escribir("No existe un cliente que corresponda con los datos ingresado");
        }
    }
    
    //CONSULTAS SOBRE VUELOS
    
    public void mostrarInformacionVuelo(String codVuelo, String fecha) {
        //buscar el vuelo correspondiente
        boolean encontrado = false;
        Vuelo v = (Vuelo) this.tablaVuelos.obtenerBuscado(codVuelo);
        Lista listaViajesVuelo = v.getViajes();
        int pos = 1;
        int longL = listaViajesVuelo.longitud();
        Viaje vj = (Viaje) listaViajesVuelo.recuperar(pos);
        if (verificarFecha(fecha)) {
            while (pos <= longL && !encontrado) {
                if (vj.getFecha().equals(fecha)) {
                    encontrado = true;
                    escribir("Aeropuerto origen " + v.getAeropuertoOrigen() + " Aeropuerto destino: " + v.getAeropuertoDestino());
                    escribir(vj.toString());
                    escribir("Hora salida: " + v.getHoraSalida() + " Hora llegada: " + v.getHoraLlegada());
                } else {
                    pos++;
                    vj = (Viaje) listaViajesVuelo.recuperar(pos);
                }
            }
            if (!encontrado) {
                escribir("No se encontro el vuelo con la fecha solicitada");
            }
        } else {
            escribir("La fecha ingresada no es una fecha valida");
        }
    }

    private boolean verificarFecha(String fech) {
        boolean exito = false;
        String[] partes = fech.split("/");
        String dia = partes[0];
        String mes = partes[1];
        String anio = partes[2];
        int d, m, a;
        d = Integer.parseInt(dia);
        m = Integer.parseInt(mes);
        a = Integer.parseInt(anio);

        if (d > 0 && d <= 31) {
            if (m > 0 && m <= 12) {
                if (a > 1900) {
                    exito = true;
                }
            }
        }
        return exito;
    }
    
    public void mostrarCodigos(String codVuelo, String otroCod) {
        Lista listaCod = this.tablaVuelos.listarRango(codVuelo, otroCod);
        escribir(listaCod.toString());
        if (listaCod.esVacia()) {
            escribir("No se han encontrado vuelos dentro del rango [" + codVuelo + "," + otroCod + "]");
        }

    }
    
    public void xcantidadVuelos(String a, String b, int cantV) {
        boolean exito;
        exito = this.mapaAeropuertos.caminoPorXcantidadVuelo(new Aeropuerto(a), new Aeropuerto(b), cantV);
        escribir("Es posible llegar del aeropuerto "+a+" al aeropuerto b en como maximo "+cantV+" vuelos?"
                + "Respuesta: "+exito);

    }
    public Lista vueloMasCorto(String a, String b) {
        Lista l = this.mapaAeropuertos.caminoMasCortoPorMinutos(new Aeropuerto(a), new Aeropuerto(b));
        return l;
    }



//Mostrar estado sistema
    public String mostrarGrafoAeropuertos() {
        String a;
        a = this.mapaAeropuertos.toString();
        return a;
    }

   

    public String mostrarClientes() {
        String s = "";
        Lista listaC = this.tablaClientes.listarDatos();
        int pos = 1;
        int longL = listaC.longitud();
        Cliente c;
        while (pos <= longL){
            c = (Cliente)listaC.recuperar(pos);
            s += c.getNombre()+c.getApellido()+"\n";
            pos++;
        }
        return s;
    }
    public String mostrarInfoCliente(String tipo,String num){
        String s = "";
        Cliente c = new Cliente(tipo,num);
        c =(Cliente) this.tablaClientes.obtenerBuscado(c.getClave());
        if (c != null){
            s = c.toString();
        } else {
            escribir("No se encontro al cliente con tipo "+tipo+" y num "+num);
        }
        return s;
    }
    
    public String mostrarVuelos(){
        String s = "";
        Lista listaV = this.tablaVuelos.listarDatos();
        int pos = 1;
        int longL = listaV.longitud();
        Vuelo v;
        while (pos <= longL){
            v = (Vuelo)listaV.recuperar(pos);
            s+= v.getCodVuelo()+"\n";
            pos++;
        }
        
        return s;
    }


    
    public void mostrarMapeoPasajes() {

        escribir(this.mapeoPasajes.toString());
    }

    public void mostrarInformacionAeropuerto(String codVuelo) {
        Aeropuerto a = (Aeropuerto) this.mapaAeropuertos.obtenerAeropuerto(new Aeropuerto(codVuelo));
        if (a != null) {
            escribir(a.toString());
        } else {
            escribir("El codigo de aeropuerto ingresado no se encuentra almacenado en el sistema");
        }
    }

    public void mostrarInformacionViajes(String codVuelo, String fecha) {
        Vuelo v = (Vuelo) this.tablaVuelos.obtenerBuscado(codVuelo);
        Lista listaViajes = v.getViajes();
        int i = 0, longitudL;
        boolean encontrado = false;
        longitudL = listaViajes.longitud();
        while (i <= longitudL && !encontrado) {
            Viaje vj = (Viaje) listaViajes.recuperar(i);
            if (vj.getFecha().equals(fecha)) {
                encontrado = true;
                escribir(vj.toString());
            }
        }
        if (!encontrado) {
            escribir("El vuelo no realizo ningun viaje en la fecha indicada");
        }
    }

    public Lista caminoMasCortoporMinutos(String origen, String destino) {
        Lista listaCamino;
        listaCamino = this.mapaAeropuertos.caminoMasCortoPorMinutos(new Aeropuerto(origen), new Aeropuerto(destino));
        return listaCamino;
    }

    public Lista clientesAptosPromocion() {
        HeapMAX heap;
        Lista l = new Lista();
        heap = this.tablaClientes.objetosDeMayoraMenor();
        int pos = 1;
        Object[] o;
        Cliente c;
        while (!heap.esVacio()) {
            o = heap.recuperarCima();
            c = (Cliente)o[1];
            String s = c.getNombre()+" "+c.getApellido()+",";
            l.insertar(s, pos);
            heap.eliminarCima();
            pos++;
        }
        return l;
    }
    
    public Lista caminoMasCortoAeropuertos(String origen,String destino){
        Lista listaCaminoAero;
        listaCaminoAero = this.mapaAeropuertos.caminoMasCortoPorAero(new Aeropuerto(origen), new Aeropuerto(destino));
        return listaCaminoAero;
    }
    
    
    
    public void generarViaje(String codVuelo, String fecha, int cantAsientosTotales) {
        int cantVendidos = 0, i = 1, lLista;
        Lista l;
        Pasaje p;
        //con este for itero sobre el hashmap
        for (Object value : this.mapeoPasajes.values()) {
            l = (Lista) value;
            lLista = l.longitud();
            //con este while recorro cada elemento de la lista 
            while (i <= lLista) {
                p = (Pasaje) l.recuperar(i);
                if (p != null) {
                    if (p.getVuelo().equals(codVuelo)) {
                        cantVendidos++;
                    }
                }
                i++;
            }
            i = 1;
        }
        Viaje nuevo = new Viaje(codVuelo, fecha, cantAsientosTotales, cantVendidos);
        Vuelo v = (Vuelo) this.tablaVuelos.obtenerBuscado(codVuelo);
        v.agregarViaje(nuevo);

    }
    
    }
       
    
        

