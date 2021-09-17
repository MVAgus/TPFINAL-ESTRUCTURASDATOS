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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 *
 * @author mano_
 */
public class SistemaViajes {
    private Grafo mapaAeropuertos; //aca se almacenan los aeropuertos. Cada nodo vertice es un aeropuerto
    private ArbolAVL tablaVuelos;  //en esta estructura se almacenan los vuelos, estan organizados por la clave de cada vuelo
    private ArbolAVL tablaClientes; //aca se almacenan los clientes que se cargan al sistema, organizados por tipo y num de dni
    private HashMap mapeoPasajes; //en este hashmap (uno a muchos) se mapea la clave de x cliente con su respectivo pasaje
    

    //el mapeo de pasajes lo hace con dominio cliente y rango lista de pasajes
    
    
    public SistemaViajes (){
        //constructor
        this.mapaAeropuertos = new Grafo();
        tablaVuelos = new ArbolAVL();
        tablaClientes = new ArbolAVL();
        mapeoPasajes = new HashMap<Comparable,Lista>(); 
    }
    
    private void escribir(String cadena) {
        //Método que escribe en un archivo de texto lo que se va ejecutando
        File archivo = new File("C:\\Users\\mano_\\Downloads\\TPFINALEDTAMarianoVergara\\EDDA-SistemaViajes\\EDDA-SistemaViajes\\src\\Archivo\\Escritura3.txt");
        try {
            FileWriter escribirArchivo = new FileWriter(archivo, true);
            try (BufferedWriter buffer = new BufferedWriter(escribirArchivo)) {
                buffer.write(cadena);
                buffer.newLine();
            }
        } catch (IOException ex) {
        }
    }
    
    //Validacion de Datos, verificaciones etc
     public boolean existeAeropuerto(String codAep){
         //verifica la existencia de un aeropuerto
        boolean respuesta;
        codAep = codAep.toUpperCase();
        respuesta = this.mapaAeropuertos.existeVertice(new Aeropuerto(codAep));
        return respuesta;
    }
     
     public boolean verificarHoraValida(String hora){
        boolean resultado = false;
        //verifica que el formato de la hora sea el correcto HH:MM
        if (hora.length() != 0){
        String primera;
        String segunda;
     
        primera = hora.substring(0, 2);
        int i = Integer.parseInt(primera);
        if (i <= 24 || i >= 1){
            segunda = hora.substring(3);
            int j = Integer.parseInt(segunda);
            if (j >= 0 || j <= 59){
                resultado = false;
        }
        }
        }
        return resultado;
    }
    private boolean verificarFecha(String fech) {
        //verifica que el formato de la fecha sea el correcto. DD/MM/AAAA
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
    
    public boolean verificarCliente(String tipo, String num){
        //devuelve true si el cliente se encuentra cargado en el sistema
        boolean exito;
        Cliente c = new Cliente(tipo,num);
        exito = this.tablaClientes.pertenece(c.getClave());
        return exito;
    }
   //------------------------------------------------------------------------------------------
    
    
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
        //Cuando doy de baja un aeropuerto, tengo que eliminar todos los vuelos que tenian a ese aeropuerto
        //si ya hay vuelos desde o hacia el aeropuerto que ingreso como parametro, se debe producir un error al eliminar
        Aeropuerto a = (Aeropuerto)this.mapaAeropuertos.obtenerElementoVertice(new Aeropuerto(nomAer)); //busco al aeropuerto
        boolean poseeArcoAsociado = false,eliminado = false;
        if (a != null){ //si el aeropuerto existe debo verificar que no tenga ningun vuelo asociado
            //checkeo la tabla de vuelos para verificar si el aerouerto a eliminar tiene algun vuelo asociado
            Lista l = this.tablaVuelos.listarDatos();
            int i = 1, longitudL = l.longitud();
            while (i <= longitudL && !poseeArcoAsociado){
                Vuelo v = (Vuelo)l.recuperar(i);
                if (v.getAeropuertoDestino().compareTo(nomAer) == 0 || v.getAeropuertoOrigen().compareTo(nomAer) == 0){
                    poseeArcoAsociado = true;
                    escribir("El aeropuerto "+nomAer+" posee al menos un arco asociado, por lo tanto no es posible eliminarlo");
                }
                i++;
            }
            if (!poseeArcoAsociado){
                this.mapaAeropuertos.eliminarVertice(a);
                escribir("Se elimino con exito el aeropuerto "+nomAer);
                eliminado = true;
            }
        } else {
            escribir("No se pudo encontrar al aeropuerto para eliminarlo "+nomAer);
        }
        return eliminado;
    }
    
    //MODIFICACION aeropuertos
    public boolean modificarAeropuerto(String nomAer,long numTelefono){
        //Supongo que solo se puede modificar el num de telefono del aeropuerto
        boolean exito = false;
        Aeropuerto a = (Aeropuerto)this.mapaAeropuertos.obtenerElementoVertice(new Aeropuerto(nomAer));
        if (a != null){
            a.setNumeroTelefono(numTelefono);
            exito = true;
            escribir("Se modifico con exito el telefono de contacto del aeropuerto "+a.getNombreAer());
        } else {
            escribir ("No se encontro al aeropuerto ingresado");
        } 
        return exito;
    }
    
    //CONSULTAS DE AEROPUERTOS
    
    public String mostrarInfoAeropuerto(String codAeropuerto){
        String s = "";
        Aeropuerto a = (Aeropuerto)this.mapaAeropuertos.obtenerElementoVertice(new Aeropuerto(codAeropuerto));
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
    
    public boolean verificarExistenciaVuelo(String codVuelo){
        boolean exito = false;
        Vuelo buscado = (Vuelo)this.tablaVuelos.obtenerBuscado(codVuelo);
        if (buscado != null){
            exito = true;
        }
        return exito;
        
    } 
    public boolean eliminarVuelo(String codVuelo, String origen, String destino) {
        //debo buscar el vuelo correcto 
        origen = origen.toUpperCase();
        destino = destino.toUpperCase();
        codVuelo = codVuelo.toUpperCase();
        boolean exito = false;
        Vuelo buscado = (Vuelo) this.tablaVuelos.obtenerBuscado(codVuelo); //verifico que el vuelo exista
        if (buscado != null) {
            //si el vuelo existe, obtengo la etiqueta
            int etiqueta = buscado.getduracionVuelo();
           
            exito = this.mapaAeropuertos.eliminarArco(new Aeropuerto(origen), new Aeropuerto(destino), etiqueta);
            if (exito) {
                //si se pudo eliminar el arco entre el origen y destino, procedo a eliminar los vuelos que existen
                //en la tabla de vuelos
                exito = this.tablaVuelos.eliminar(codVuelo);
//                Lista l = this.tablaVuelos.listarDatos();
                
                escribir("Se elimino con exito vuelo desde: " + origen + " hacia: " + destino);
            } else {
                escribir("No se pudo eliminar vuelo desde: " + origen + " hacia: " + destino);
                System.out.println("No se pudo eliminar vuelo desde: " + origen + " hacia: " + destino);
            }
        } else {
            escribir("No hay ningun vuelo asociado al codigo: "+codVuelo);
            System.out.println("No hay ningun vuelo asociado al codigo: " + codVuelo);
        }
        return exito;
    }
    public void eliminarPasajesDeVuelo(String codVuelo) {
        //Elimina los pasajes de todos los clientes que tengan un vuelo asociado al codigo ingresado por parametro
        int longitudL = 0, i = 1;
        boolean encontrado = false;
        Lista l;
        Pasaje p;
        for (Object value : this.mapeoPasajes.values()) {
            l = (Lista) value;
            if (!l.esVacia()) {
                longitudL = l.longitud();
                while (i <= longitudL && !encontrado) {
                    p = (Pasaje) l.recuperar(i);
                    if (p != null) {
                        if (p.getVuelo().equals(value)) {
                            l.eliminar(i);
                        }
                    }
                    i++;
                }
            }
            i = 1;
        }

    }
    
    //MODIFICACION vuelos
    public boolean modificarVuelo(String codVuelo, String origen, String destino, String horaLlegada, String horaSalida) {
        int nuevaDuracion, duracion;
        boolean exito = false;
        Vuelo buscado = (Vuelo) this.tablaVuelos.obtenerBuscado(codVuelo);
        if (buscado != null) {//Encontro el vuelo
            //Vuelo v = (Vuelo);
            //modifica la hora de llegada
            if (this.verificarHoraValida(horaLlegada)) {
                //si el formato de hora es valido
                duracion = buscado.getduracionVuelo();
                buscado.setHoraLlegada(horaLlegada); //se modica la hora en el vuelo
                nuevaDuracion = buscado.getduracionVuelo();
                this.mapaAeropuertos.modificarEtiquetaArco(new Aeropuerto(origen), new Aeropuerto(destino), duracion, nuevaDuracion);
            } else {
                escribir("Formato de hora invalido");
            }

            if (this.verificarHoraValida(horaSalida)) {
                //modifica la hora de salida
                duracion = buscado.getduracionVuelo();
                buscado.setHoraSalida(horaSalida); //se modifica la hora en el vuelo
                nuevaDuracion = buscado.getduracionVuelo();
                this.mapaAeropuertos.modificarEtiquetaArco(new Aeropuerto(origen), new Aeropuerto(destino), duracion, nuevaDuracion);
            } else {
                escribir("Formato de hora invalido");
            }

            escribir("Se modifico con exito el vuelo: " + codVuelo);
        } else {
            escribir("Vuelo no encontrado. No se pudo modificar el vuelo " + codVuelo);
        }

        return exito;
    }
    
    //ALTA clientes
    public void agregarCliente(String tipo, String num, String nombre, String apellido, String fechaNac, String domicilio,
            long numTelefono) {
    
        Cliente c = new Cliente(tipo, num);
        if (verificarFecha(fechaNac)) { //si el formato de la fecha es valido 
            //antes de ingresar verificar si no es un cliente repetido
            completarDatosCliente(c, nombre, apellido, fechaNac, domicilio, numTelefono);
            this.tablaClientes.insertar(c.getClave(), c);
            escribir("Se agrego con exito el cliente: " + nombre + " " + apellido);
            this.mapeoPasajes.put(c.getClave(), new Lista()); //se agrega al cliente al hashmap 
      
        } else {
            escribir("No se pudo agregar el cliente : " + nombre + " " + apellido+ " la fecha de nacimiento no corresponde"
                    + " a una fecha valida");
        }
    }
    //BAJA clientes
    public boolean eliminarCliente(String tipo,String num){
    //al dar de baja los clientes debo eliminar los pasajes asociados
        boolean exito;
        Cliente c = new Cliente(tipo,num);
        exito = this.tablaClientes.eliminar(c.getClave()); 
        if (exito){
            //si el cliente existe debo eliminar los pasajes a ese cliente
            this.mapeoPasajes.remove(c.getClave());
             escribir("Se elimino con exito al cliente: "+tipo+" , "+num);
        }
        else {
             escribir("No se pudo eliminar al cliente: "+tipo+" , "+num);
        }
        return exito;  
    }
    
    //MODIFICACION cliente
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
   
    private void completarDatosCliente(Cliente c,String nombre, String apellido,String fechaNac,String domicilio,long numTel){
        c.setNombre(nombre);
        c.setApellido(apellido);
        c.setFechaNacimiento(fechaNac);
        c.setDomicilio(domicilio);
        c.setNumTelefono(numTel);
    }
    
      
    public boolean agregarPasaje(String tipo, String num, String codVuelo, String fecha) {
        //Verifico si existe el cliente y tambien debo verificar que no sea un pasaje repetido
        boolean seAgregoPasaje = false, repetido = false;
        Cliente c = new Cliente(tipo, num);
        int numAsiento = 0, i = 1, longitudPasajesCliente;
        c = (Cliente) this.tablaClientes.obtenerBuscado(c.getClave()); //obtenemos al cliente
        if (c != null) {
            //existe el cliente
            if (verificarFecha(fecha)) { //este metodo es para verificar que el formato de la fecha sea correcto
                Object o = this.mapeoPasajes.get(c.getClave()); //con esto obtengo la lista de los pasajes que estan asociados al cliente c
                Lista listaPasajesCliente = (Lista) o;
                //tengo que verificar que el viaje exista (lo busco por fecha)
                Vuelo v = (Vuelo) this.tablaVuelos.obtenerBuscado(codVuelo); //obtengo el vuelo
                if (v != null) {
                    int posViajeBuscado = v.obtenerViajeBuscado(fecha); //obtengo la posicion donde se encuentra el viaje buscado

                    if (posViajeBuscado != -1) {
                        //el viaje existe entonces modificamos la cantidad de pasajes vendidos, debemos verificar que no sea un pasaje repetido
                        Lista listaViajes = v.getViajes();
                        Viaje viaje = (Viaje) listaViajes.recuperar(posViajeBuscado);

                        if (viaje.getAsientosVendidos() != viaje.getAsientosTotales()) {
                            longitudPasajesCliente = listaPasajesCliente.longitud();
                            while (i <= longitudPasajesCliente && !repetido) {
                                Pasaje p = (Pasaje) listaPasajesCliente.recuperar(i);
                                if (p.getVuelo().compareTo(codVuelo) == 0 && p.getFecha().compareTo(fecha) == 0) {
                                    repetido = true;
                                    escribir("Los datos del pasaje (fecha y codigo de vuelo) ya estan registrados, no es posible agregar el pasaje");
                                }
                                i++;
                            } //luego de checkear cada pasaje, si no hay ninguno repetido, lo inserta, siempre y cuando hayan asientos disponibles
                            if (!repetido) {
                                viaje.setAsientosVendidos(viaje.getAsientosVendidos() + 1);
                                numAsiento = viaje.getAsientosVendidos(); //siempre le doy a la persona el ultimo asiento vendido +1
                                Pasaje p = new Pasaje(codVuelo, fecha, numAsiento, 'P');
                                listaPasajesCliente.insertar(p, listaPasajesCliente.longitud() + 1);
                                seAgregoPasaje = true;
                                c.añadirPasaje();
                                escribir("Se agrego con el exito el pasaje del cliente " + tipo + " " + num);
                            }
                        } else {
                            escribir("No se pudo agregar el pasaje del cliente " + tipo + " " + num + " porque no hay asientos disponibles");
                        }
                    }
                } else {
                    escribir("No se encuentra el vuelo correspondiente con los datos ingresados");
                }
            } else {
                escribir("El formato de la fecha ingresada no es valida");
            }
        } else {
            escribir("No se encontro al cliente con tipo: " + tipo + " y num:  " + num);
        }
        return seAgregoPasaje;

    }

    private Object buscarPasaje(Lista l, String codVuelo, String fecha, int numAsiento) {
//        este metodo lo tenia para buscar un pasaje repetido, pero al modelar de forma en la cual siempre
//    le doy el ultimo asiento disponible al pasajero no llegaria nunca a un pasaje repetido
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
//
//    
    public boolean eliminarPasaje(String tipo, String num, String codVuelo, String fecha,int numAsiento) {
        //verifica que exista el cliente y luego que exista el pasaje
        boolean exito = false;
        Cliente c = new Cliente(tipo, num);
        c = (Cliente) this.tablaClientes.obtenerBuscado(c.getClave());
        if (c != null) {
            if (verificarFecha(fecha)){
            boolean eliminado = false;
            Lista l = (Lista) this.mapeoPasajes.get(c.getClave());
            int longitudListaPasajes = l.longitud();
            int j = 1;
            while (j <= longitudListaPasajes && !eliminado) {
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
    
    public boolean modificarPasaje(String tipo, String num, String codVuelo, String fecha,int numAsiento,String estado) {
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
                    p.setEstado(estado.charAt(0));
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
        List<String> ciudades;
        ciudades = new ArrayList<String>();
        String vuelo;
        Pasaje p;
        Cliente cl = (Cliente) c;
        Comparable clave;
        clave = cl.getClave();
        if (this.tablaClientes.pertenece(clave)) {
            Lista l = (Lista) this.mapeoPasajes.get(clave);
            if (!l.esVacia()) {
                int i = 1;
                int longitudL = l.longitud();
                while (i <= longitudL) { //recorro la lista de pasajes que esta asociada al cliente
                    p = (Pasaje) l.recuperar(i);
                    if (p.getEstado() == 'V') {
                        vuelo = p.getVuelo();
                        Vuelo v = (Vuelo) this.tablaVuelos.obtenerBuscado(vuelo);
                        ciudades.add(v.getAeropuertoDestino());
                    }
                    i++;
                }
                Set<String> hashSet = new HashSet<String>(ciudades); //esta parte es para eliminar repetidos
                ciudades.clear();
                ciudades.addAll(hashSet);
                escribir("Ciudades visitadas por el cliente : " + cl.getClave() + " -->" + ciudades.toString());
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
        String s = "";
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
    
    public String mostrarInformacionVuelo(String codVuelo) {
        //muestra informacion correspondiente al codigo de vuelo ingresado por parametro
        String resultado = "";
        Vuelo v = (Vuelo) this.tablaVuelos.obtenerBuscado(codVuelo);
        if (v != null) {
           resultado = v.toString();
           
        } else {
            escribir("No se encontro al vuelo con el codigo" + codVuelo);
            System.out.println("No se encontro al vuelo buscado");
        }
        return resultado;
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
        exito = this.mapaAeropuertos.caminoPorXcantidadDeVertices(new Aeropuerto(a), new Aeropuerto(b), cantV);
        escribir("Es posible llegar del aeropuerto "+a+" al aeropuerto "+b+"  en como maximo "+cantV+" vuelos?"
                + "Respuesta: "+exito);

    }
    public Lista vueloMasCorto(String a, String b) {
        Lista l = this.mapaAeropuertos.caminoMasCortoPorMinutos(new Aeropuerto(a), new Aeropuerto(b));
        escribir(l.toString());
        return l;
    }




    //MOSTRAR ESTADO SISTEMA
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


    
    public String mostrarMapeoPasajes() {
        String s = "";
        Pasaje p;
        Lista l;
        String sPasaje = "";
        int i = 1, longitudL;
        Iterator<Map.Entry<Comparable, Lista>> entries = this.mapeoPasajes.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry<Comparable, Lista> entry = entries.next();

            Cliente c = (Cliente) this.tablaClientes.obtenerBuscado(entry.getKey());

            l = (Lista) this.mapeoPasajes.get(entry.getKey());
            longitudL = l.longitud();
            while (i <= longitudL) {
                p = (Pasaje) l.recuperar(i);
                sPasaje += p.toString() + " ";
                i++;
            }
            i = 1;
            s += "Cliente: " + c.getClave() + " Lista pasajes: " + sPasaje + "\n";
            sPasaje = "";
        }
        return s;
    }

    public void mostrarInformacionAeropuerto(String codVuelo) {
        Aeropuerto a = (Aeropuerto) this.mapaAeropuertos.obtenerElementoVertice(new Aeropuerto(codVuelo));
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
        escribir(listaCamino.toString());
        return listaCamino;
    }

    public Lista clientesAptosPromocion() {
      
        Lista l = new Lista();
        HeapMAX heapPromocion = new HeapMAX();
        l = this.tablaClientes.listarDatos(); //obtengo a todos los clientes
        //decidi trabajar de esta forma porque como el heap guarda objetos genericos, crei que
        //haciendolo de esta manera guardando por cantidad de pasajes comprados era la forma correcta 
        int i = 1;
        while (!l.esVacia()) {
            //en este while los ordeno de mayor a menor (por cant de pasajes comprados)
            Cliente c = (Cliente)l.recuperar(i);
            if (c != null){
            heapPromocion.insertar(c.getPasajesComprados(), c);
                System.out.println("-------------------------");;
                System.out.println(heapPromocion.toString());
            l.eliminar(i);
            
            }
        } //luego de esto puedo directamente mostrar el toString de heap??
        int pos = 1;
        Object[] o;
        Cliente cl;
        while (!heapPromocion.esVacio()) {
            o = heapPromocion.recuperarCima();
            cl = (Cliente) o[1];
            String s = "Cliente: "+cl.getClave()+"  Pasajes comprados: "+cl.getPasajesComprados();
            l.insertar(s, pos);
            heapPromocion.eliminarCima();
            pos++;
        }
        return l;
    }
    
    public String caminoMasCortoPorAeropuertos(String origen,String destino){
        Lista listaCaminoAero;
        String s = "";
        listaCaminoAero = this.mapaAeropuertos.caminoMasCortoPorVertices(new Aeropuerto(origen), new Aeropuerto(destino));
        if ((int)listaCaminoAero.recuperar(listaCaminoAero.longitud()) == 0){
            escribir("No existe camino entre los dos aeropuertos ingresados");
            s = "No existe camino entre los dos aeropuertos ingresados";
        } else {
            s = listaCaminoAero.toString();
        }
        return s;
    }
    
    public Lista caminoMasCortodeAaByPasaPorC(String origen,String destino,String intermedio){
        //este metodo devuelve el camino mas corto de A a B y que pasa por C
        Lista listaCamino;
        listaCamino = this.mapaAeropuertos.caminoMasCortoPorminutossquePasaporX(new Aeropuerto(origen),new Aeropuerto(destino),new Aeropuerto(intermedio));
        return listaCamino;
    }
    
    
    
    public void agregarViaje(String codVuelo, String fecha, String cantAsientosTotales) {
        //Descripcion: genera un viaje con un codigo de vuelo, una fecha y una cantidad de asientos totales
        int i = 1;
        int cantAsientosT = Integer.parseInt(cantAsientosTotales);
        //Primero que nada se verifica si el vuelo (es decir la ruta) existe 
        Vuelo v = (Vuelo) this.tablaVuelos.obtenerBuscado(codVuelo);
        boolean repetido = false;
        if (v != null) {
            //si el vuelo existe, debo verificar que no hayan viajes repetidos

            Lista listaViajes = v.getViajes();
            int longitudLista = listaViajes.longitud();

            while (i <= longitudLista && !repetido) {
                Viaje vj = (Viaje) listaViajes.recuperar(i);
                if (vj != null) {
                    if (vj.getFecha().compareTo(fecha) == 0 && vj.getAsientosTotales() == cantAsientosT) {
                        repetido = true;
                    }
                }
                i++;
            }
            if (repetido) {
                escribir("No se puede agregar el viaje ya que hay un viaje con los mismos datos");
            } else {
                Viaje viajeNuevo = new Viaje(fecha,cantAsientosT);
                v.agregarViaje(viajeNuevo);
                escribir("Se agrego con el exito el viaje");
            }

        } else {
            escribir("No existe el vuelo correspondiente a ese codigo de vuelo");
        }

    }

    public void efectuarViaje(String codVuelo, String fecha) {
        //Cambia el estado del pasaje de los pasajes que se encuentren con el codigo
        //de vuelo y la fecha ingresada por parametro
        int i = 1, lLista;
        Lista l;
        Pasaje p;
        //tengo que verificar si existe el vuelo
        Vuelo v = (Vuelo) this.tablaVuelos.obtenerBuscado(codVuelo);
        Viaje viaje;
        if (v != null) {
            if (verificarFecha(fecha)) {
                Lista listaViajes = v.getViajes();
                int posViajeBuscado = v.obtenerViajeBuscado(fecha);
                if (posViajeBuscado != -1) {
                    viaje = (Viaje) listaViajes.recuperar(posViajeBuscado);

                    if (viaje.getAsientosVendidos() > 0) {
                        //el vuelo tiene al menos un pasaje vendido, entonces debo cambiar el estado de los pasajes

                        //con este for itero sobre el hashmap, mas precisamente sobre los valores
                        for (Object value : this.mapeoPasajes.values()) {
                            l = (Lista) value;
                            lLista = l.longitud();
                            //con este while recorro cada elemento de la lista 
                            while (i <= lLista) {
                                p = (Pasaje) l.recuperar(i);
                                if (p != null && p.getVuelo().compareTo(codVuelo) == 0 && p.getFecha().compareTo(fecha) == 0) {
                                    p.setEstado('V');
                                }
                                i++;
                            }
                            i=1;
                        }
                        escribir("El viaje con el codigo de vuelo" + codVuelo + " y la fecha: " + fecha + " se realizo con exito");

                    } else {
                        escribir("El viaje correspondiente no posee ningun pasaje vendido, no se puede efectuar el viaje");
                    }
                } else {
                    escribir("Los datos ingresados no corresponden a ningun viaje existente");
                }
            } else {
                escribir("Error. El formato de la fecha no es valido");
            }
        } else {
            escribir("Error al efectuar el viaje, el codigo de vuelo no corresponde a un vuelo existente");
        }
    }
    
    public void cancelarViaje(String codVuelo, String fecha) {
        //Cambia el estado del pasaje de cada cliente que se encuentre en el vuelo correspondiente a 
        //la fecha ingresada por parametro
        int i = 1, lLista;
        Lista l;
        Pasaje p;
        //con este for itero sobre el hashmap, mas precisamente sobre los valores
        for (Object value : this.mapeoPasajes.values()) {
            l = (Lista) value;
            lLista = l.longitud();
            //con este while recorro cada elemento de la lista 
            while (i <= lLista) {
                p = (Pasaje) l.recuperar(i);
                if (p != null && p.getVuelo().compareTo(codVuelo) == 0 && p.getFecha().compareTo(fecha) == 0) {
                    p.setEstado('C');
                }
                i++;
            }
            i = 1;
        }

    }
    
    public String mostrarInformacionViaje(String codVuelo){
        String s = "";
   
        Lista l;
        int i = 1,longitudL;
        Vuelo v = (Vuelo)this.tablaVuelos.obtenerBuscado(codVuelo);
        if (v != null){
            l = v.getViajes();
            longitudL = l.longitud();
            while (i <= longitudL){
                if (l.recuperar(i) != null){
                s+= l.recuperar(i).toString()+"\n";
                i++;
            }
        }
        
    }
        return s;
    
    }
   
    public String mostrarEstadoSistema(){
        
        String s;
        //mostrar grafo
        s = "Grafo de Aeropuertos: "+"\n";
        s+= this.mapaAeropuertos.toString()+"\n";
        //mostrar clientes
        s+= "Clientes almacenados"+"\n";
        s += this.tablaClientes.toString();
        
        s+= "Mapeo de Pasajes";
        s+= this.mostrarMapeoPasajes();
        
        s+= "Tabla de Vuelos"+"\n";
        s+= this.tablaVuelos.toString();
        
       
       return s;
    }
   
    
}
    
        

