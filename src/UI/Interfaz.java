/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Funcionalidad.SistemaViajes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author mano_
 */
public class Interfaz {
    
    public static SistemaViajes cargarDatos(String direccion){
    //realiza la precarga de los datos
    SistemaViajes sistema = new SistemaViajes();
   
          
        try {
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            String lecturaBf, p1,p2,p3,p4,p5,p6,p7;
            
            while (((lecturaBf = bf.readLine()) != null)){
                String[] partes = lecturaBf.split(","); //este split es para ir separando la cadena por ","
                //la parte 0 es lo que indica si es aeropuerto,vuelo,pasaje,cliente o viaje
                switch (lecturaBf.charAt(0)){
                    case 'A':
                        //opera sobre los aeropuertos
                        p1 = partes[0];
                        p1 = p1.substring(2);
                        p2 = partes[1];
                        p3 = partes[2];
                        sistema.agregarAeropuerto(p1,p2, Integer.parseInt(p3));
                        break;
                    case 'V':
                        //opera sobre los vuelos
                        p1 = partes[0];
                        p1 = p1.substring(2);
                        p2 = partes[1];
                        p3 = partes[2];
                        p4 = partes[3];
                        p5 = partes[4];
                        sistema.agregarVuelo(p1,p2,p3,p4,p5);
                        break;
                    case 'C':
                        //opera sobre los clientes
                        p1 = partes[0];
                        p1 = p1.substring(2);
                        p2 = partes[1];
                        p3 = partes[2];
                        p4 = partes[3];
                        p5 = partes[4];
                        p6 = partes[5];
                        p7 = partes[6];
                        sistema.agregarCliente(p1.toUpperCase(),p2,p3.toUpperCase(),p4.toUpperCase(),p5,p6,Integer.parseInt(p7));
                        break;
                    case 'P':
                        //opera sobre los pasajes
                        p1 = partes[0];
                        p1 = p1.substring(2);
                        p2 = partes[1];
                        p3 = partes[2];
                        p4 = partes[3];
                        sistema.agregarPasaje(p1, p2, p3, p4);
                        break;
                    case 'J':
                        //carga los viajes 
                        p1 = partes[0];
                        p1 = p1.substring(2);
                        p2 = partes[1];
                        p3 = partes[2];
                        sistema.agregarViaje(p1,p2,p3);
                    default:
                }
            } 
        }  catch (IOException e) {
            System.err.println("No se encontró archivo");
        }
        return sistema;
        
    }
    public static void main(String args[]) {

        String dir = "C:\\Users\\mano_\\Downloads\\TPFINALEDTAMarianoVergara\\CargaInicio.txt";

        SistemaViajes edtaViajes = cargarDatos(dir);

        int op = 1, opc1, opc2, opc3, opc4, opc5, opc6, opc7, opc8;

        while (op != 15) {
            menu();
            op = TecladoIn.readInt();

            switch (op) {
                case 1:
                    opc1 = 1;
                    while (opc1 != 5) {
                        ABMAeropuerto();
                        opc1 = TecladoIn.readInt();
                        switch (opc1) {
                            case 1:
                                System.out.println("Ingrese el codigoAeropuerto");
                                String codAer = TecladoIn.readLine();
                                System.out.println("Ingrese la ciudad donde se ubica el aeropuerto");
                                String nomCiudad = TecladoIn.readLine();
                                System.out.println("Ingrese el numero de contacto del aeropuerto");
                                long numAer = TecladoIn.readLineLong();
                                edtaViajes.agregarAeropuerto(codAer.toUpperCase(), nomCiudad, numAer);
                                break;
                            case 2:
                                System.out.println("Ingrese el codigo del Aeropuerto");
                                edtaViajes.eliminarAeropuerto(TecladoIn.readLineWord().toUpperCase());
                                break;
                            case 3:
                                String cod;
                                System.out.println("Ingrese el codigo del Aeropuerto");
                                cod = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el nuevo numero de contacto del aeropuerto");
                                edtaViajes.modificarAeropuerto(cod, TecladoIn.readLong());
                                break;
                            case 4:
                                System.out.println("Ingrese el codigo del aeropuerto");
                                cod = TecladoIn.readLineWord().toUpperCase();
                                System.out.println(edtaViajes.mostrarInfoAeropuerto(cod));
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("Opcion incorrecta");
                        }
                    }
                    break;
                case 2:
                    opc2 = 1;
                    while (opc2 != 5) {
                        ABMVuelo();
                        opc2 = TecladoIn.readLineInt();
                        String codV, aeropuertoO, aeropuertoD, hS, hL;
                        switch (opc2) {

                            case 1:
                                System.out.println("Ingrese el codigo de vuelo");
                                codV = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el aeropuerto de origen");
                                aeropuertoO = TecladoIn.readLineWord();
                                if (edtaViajes.existeAeropuerto(aeropuertoO)) {
                                    System.out.println("Ingrese el aeropuerto de destino");
                                    aeropuertoD = TecladoIn.readLineWord();
                                    if (edtaViajes.existeAeropuerto(aeropuertoD)) {
                                        System.out.println("Ingrese la hora de salida HH:HH");
                                        hS = TecladoIn.readLineWord();
                                        if (edtaViajes.verificarHoraValida(hS)) {
                                            System.out.println("Ingrese la hora de llegada HH:HH");
                                            hL = TecladoIn.readLineWord();
                                            if (edtaViajes.verificarHoraValida(hL)) {
                                                edtaViajes.agregarVuelo(codV, aeropuertoO, aeropuertoD, hS, hL);
                                            } else {
                                                System.out.println("Ingrese una hora valida");
                                            }
                                        } else {
                                            System.out.println("Ingrese una hora valida");
                                        }
                                    } else {
                                        System.out.println("Ingrese un aeropuerto de destino valido");
                                    }
                                } else {
                                    System.out.println("Ingrese un aeropuerto de origen valido");
                                }

                                break;
                            case 2:
                                System.out.println("Ingrese el codigo de vuelo");
                                codV = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el aeropuerto de origen");
                                aeropuertoO = TecladoIn.readLineWord();
                                if (edtaViajes.existeAeropuerto(aeropuertoO)) {
                                    System.out.println("Ingrese el aeropuerto de destino");
                                    aeropuertoD = TecladoIn.readLineWord();
                                    if (edtaViajes.existeAeropuerto(aeropuertoD)) {
                                        edtaViajes.eliminarVuelo(codV, aeropuertoO, aeropuertoD);
                                    } else {
                                        System.out.println("Ingrese un aeropuerto de destino valido");
                                    }
                                } else {
                                    System.out.println("Ingrese un aeropuerto de origen valido");
                                }
                             
                                break;
                            case 3:
                                int opcionV = 1;
                                while (opcionV != 3) {
                                    System.out.println("Ingrese el codigo de vuelo");
                                codV = TecladoIn.readLineWord().toUpperCase();
                                String ori,
                                 dest,
                                 horaS,
                                 horaL;
                                 opcionV = 1;
                                    System.out.println("¿Que desea modificar del vuelo?");
                                    menuModificacionVuelo();
                                    opcionV = TecladoIn.readInt();
                                    switch (opcionV) {
                                            
                                        case 1:
                                            System.out.println("Ingrese la hora de salida. HH:HH");
                                            horaS = TecladoIn.readLineWord();
                                            edtaViajes.modificarVuelo(codV, "", "", "", horaS);
                                           
                                            break;
                                        case 2:
                                            System.out.println("Ingrese la hora de llegada");
                                            horaL = TecladoIn.readLineWord();
                                            edtaViajes.modificarVuelo(codV, "", "", horaL, "");
                                            
                                        case 3:
                                            break;
                                        default:
                                            System.out.println("Opcion incorrecta");
                                    }
                                }
                            case 4:
                                System.out.println("Ingrese el codigo del vuelo");
                                String cod = TecladoIn.readLineWord().toUpperCase();
                                System.out.println(edtaViajes.mostrarInformacionVuelo(cod));
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("Opcion Incorrecta");

                        }
                    }
                    break;
                case 3:

                    opc3 = 1;
                    while (opc3 != 5) {
                        ABMCliente();
                        opc3 = TecladoIn.readInt();
                        String nom, apellido, tipo, num, fechaNac, domicilio;
                        long numTel;
                        switch (opc3) {
                            case 1:
                                System.out.println("Ingrese el tipo de identificacion");
                                tipo = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el num de la identificacion");
                                num = TecladoIn.readLineWord().toUpperCase();
                                if (!edtaViajes.verificarCliente(tipo, num)) { //verificar la existencia del cliente antes de pedir todos los datos
                                    System.out.println("Ingrese el nombre");
                                    nom = TecladoIn.readLineWord();
                                    System.out.println("Ingrese el apellido");
                                    apellido = TecladoIn.readLineWord();
                                    System.out.println("Ingrese la fecha de nacimiento. dd/mm/aaaa");
                                    fechaNac = TecladoIn.readLineWord();
                                    System.out.println("Ingrese el domicilio");
                                    domicilio = TecladoIn.readLineWord();
                                    System.out.println("Ingrese el num de telefono");
                                    numTel = TecladoIn.readLineLong();
                                    edtaViajes.agregarCliente(tipo, num, nom, apellido, fechaNac, domicilio, numTel);
                                } else {
                                    System.out.println("El cliente ingresado ya se encuentra en el sistema");;
                                }
                                break;
                            case 2:
                                System.out.println("Ingrese el tipo de identificacion");
                                tipo = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el num de la identificacion");
                                num = TecladoIn.readLineWord().toUpperCase();
                                edtaViajes.eliminarCliente(tipo, num);
                                break;
                            case 3:
                                System.out.println("Ingrese el tipo de identificacion");
                                tipo = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el num de la identifacion");
                                num = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el nuevo domilicio");
                                domicilio = TecladoIn.readLine();
                                System.out.println("Ingrese el nuevo num de telefono");
                                numTel = TecladoIn.readLineLong();
                                edtaViajes.modificarCliente(tipo, num, domicilio, numTel);
                                break;
                            case 4:
                                System.out.println("Ingrese el tipo de identificacion");
                                tipo = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el num de la identifacion");
                                num = TecladoIn.readLineWord().toUpperCase();
                                System.out.println(edtaViajes.mostrarInfoCliente(tipo, num));
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("Opcion incorrecta");
                        }
                    }
                    break;
                case 4:
                    opc4 = 1;
                    while (opc4 != 4) {
                        ABMPasaje();
                        String vuelo, fecha, tipo, num, estado;
                        int numAsiento;
                        opc4 = TecladoIn.readLineInt();
                        switch (opc4) {
                            case 1:
                                System.out.println("Ingrese el tipo de identificacion ");
                                tipo = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el num de la identificacion");
                                num = TecladoIn.readLineWord().toUpperCase();
                                if (edtaViajes.verificarCliente(tipo, num)) { //si existe en cliente
                                    System.out.println("Ingrese el num de vuelo");
                                    vuelo = TecladoIn.readLineWord().toUpperCase();
                                    System.out.println("Ingrese la fecha. dd/mm/aaaa");
                                    fecha = TecladoIn.readLineWord();
                                    if (edtaViajes.agregarPasaje(tipo, num, vuelo, fecha)) {
                                        System.out.println("Se agrego con el exito el pasaje del cliente: " + tipo + " " + num);
                                    } else {
                                        System.out.println("No se pudo agregar el pasaje del cliente: " + tipo + " " + num);
                                    }
                                } else {
                                    String opcion;
                                    System.out.println("El cliente ingresado no se encuentra en el sistema");
                                    System.out.println("¿Desea agregarlo? Ingrese S para si y N para no");
                                    opcion = TecladoIn.readLineWord().toUpperCase();

                                    switch (opcion) {
                                        case "S":
                                            String nom,
                                             ape,
                                             fech,
                                             dom;
                                            long numeroT;
                                            System.out.println("Ingrese el nombre");
                                            nom = TecladoIn.readLineWord();
                                            System.out.println("Ingrese el apellido");
                                            ape = TecladoIn.readLineWord();
                                            System.out.println("Ingrese la fecha de nacimiento");
                                            fech = TecladoIn.readLineWord();
                                            System.out.println("Ingrese el domiclio");
                                            dom = TecladoIn.readLineWord();
                                            System.out.println("Ingrese el numero de telefono");
                                            numeroT = TecladoIn.readLineLong();
                                            System.out.println("Ingrese el num de vuelo");
                                            vuelo = TecladoIn.readLineWord();
                                            System.out.println("Ingrese la fecha. dd/mm/aaaa");
                                            fecha = TecladoIn.readLineWord();
                                            if (edtaViajes.agregarPasaje(tipo, num, vuelo, fecha)) {
                                                System.out.println("Se agrego con el exito el pasaje del cliente: " + tipo + " " + num);
                                            } else {
                                                System.out.println("No se pudo agregar el pasaje del cliente: " + tipo + " " + num);
                                            }
                                            break;
                                        case "N":
                                            break;
                                        default:
                                            System.out.println("Opcion incorrecta");
                                    }
                                }
                                break;
                            case 2:
                                System.out.println("Ingrese el tipo de identificacion");
                                tipo = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el num de identificacion");
                                num = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el num de vuelo");
                                vuelo = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese la fecha del pasaje. dd/mm/aaaa");
                                fecha = TecladoIn.readLineWord();
                                System.out.println("Ingrese el num de asiento");
                                numAsiento = TecladoIn.readLineInt();
                                edtaViajes.eliminarPasaje(tipo, num, vuelo, fecha, numAsiento);
                                break;
                            case 3:
                                System.out.println("Ingrese el tipo de identificacion");
                                tipo = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el num de identificacion");
                                num = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el num de vuelo");
                                vuelo = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese la fecha del pasaje. dd/mm/aaaa");
                                fecha = TecladoIn.readLineWord();
                                System.out.println("Ingrese el num de asiento");
                                numAsiento = TecladoIn.readInt();
                                System.out.println("Ingrese el nuevo estado");
                                estado = TecladoIn.readLineWord();
                                edtaViajes.modificarPasaje(tipo, num, vuelo, fecha, numAsiento, estado);
                                break;
                            default:
                                System.out.println("Opcion incorrecta");
                        }
                    }
                    break;
                case 5:
                    opc5 = 1;
                    String codV,
                     fech,cantAsientosTotal;
                    while (opc5 != 4) {
                        menuViaje();
                        opc5 = TecladoIn.readInt();
                        switch (opc5) {
                            case 1:
                                System.out.println("Ingrese el codigo de vuelo");
                                codV = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese la fecha. dd/m/aaaa ");
                                fech = TecladoIn.readLineWord();
                                System.out.println("Ingrese la cantidad de asientos totales");
                                cantAsientosTotal = TecladoIn.readLineWord();
                                edtaViajes.agregarViaje(codV,fech,cantAsientosTotal);
                                break;
                            case 2:
                                System.out.println("Ingrese el codigo de vuelo");
                                codV = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese la fecha del vuelo. dd/m/aaaa");
                                fech = TecladoIn.readLineWord();
                                edtaViajes.efectuarViaje(codV, fech);
                                break;
                            case 3:
                                System.out.println("Ingrese el codigo de vuelo");
                                codV = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese la fecha. dd/m/aaaa ");
                                fech = TecladoIn.readLineWord();
                                edtaViajes.cancelarViaje(codV, fech);
                                break;
                        }   
                    }
                    break;
                case 6:
                    opc6 = 1;
                    String tipo,
                     num;
                    while (opc6 != 3) {
                        menuCliente();
                        opc6 = TecladoIn.readInt();
                        switch (opc6) {
                            case 1:
                                System.out.println("Ingrese el tipo de identificacion");
                                tipo = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el num de identifacion");
                                num = TecladoIn.readLineWord().toUpperCase();
                                edtaViajes.verfyMostrarInformacionCliente(tipo, num);
                                break;
                            case 2:
                                Object c;
                                System.out.println("Ingrese el tipo de identificacion");
                                tipo = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el num de identifacion");
                                num = TecladoIn.readLineWord().toUpperCase();
                                c = edtaViajes.obtenerCliente(tipo, num); //no se si conviene hacer esto o directamente mandar tipo y num de dni
                                edtaViajes.mostrarCiudadesVisitadas(c);
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Opcion incorrecta");
                        }
                    }
                    break;
                case 7:
                    opc7 = 1;
                    String cod,
                     fecha,
                     cod2;
                    while (opc7 != 3) {
                        menuVuelos();
                        opc7 = TecladoIn.readInt();
                        switch (opc7) {
                            case 1:
                                System.out.println("Ingrese el codigo de vuelo");
                                cod = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese la fecha del vuelo. dd/mm/aaaa");
                                fecha = TecladoIn.readLineWord();
                                edtaViajes.mostrarInformacionVuelo(cod);
                                break;
                            case 2:
                                System.out.println("Ingrese el codigo de vuelo");
                                cod = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el codigo de vuelo");
                                cod2 = TecladoIn.readLineWord().toUpperCase();
                                edtaViajes.mostrarCodigos(cod, cod2);
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Opcion incorrecta");

                        }
                    }

                    break;
                case 8:
                    opc8 = 1;
                    String aerA,aerB,aerC;
                    int cantV;
                    while (opc8 != 3) {
                        menuFunciones();
                        opc8 = TecladoIn.readLineInt();
                        switch (opc8) {
                            case 1:
                                System.out.println("Ingrese el codigo del aeropuerto");
                                aerA = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el codigo del aeropuerto");
                                aerB = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese la cantidad de vuelos");
                                cantV = TecladoIn.readInt();
                                edtaViajes.xcantidadVuelos(aerA, aerB, cantV);
                                break;
                            case 2:
                                System.out.println("Ingrese el codigo del aeropuerto");
                                aerA = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el codigo del aeropuerto");
                                aerB = TecladoIn.readLineWord().toUpperCase();
                                edtaViajes.caminoMasCortoporMinutos(aerA, aerB);
                                break;
                            case 3:
                                System.out.println("Ingrese el codigo del aeropuerto");
                                aerA = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el codigo del aeropuerto");
                                aerB = TecladoIn.readLineWord().toUpperCase();
                                System.out.println(edtaViajes.caminoMasCortoPorAeropuertos(aerA, aerB));
                                break;
                            case 4:
                                System.out.println("Ingrese el codigo del aeropuerto A");
                                aerA = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el codigo del aeropuerto B");
                                aerB = TecladoIn.readLineWord().toUpperCase();
                                System.out.println("Ingrese el codigo del aeropuerto C");
                                aerC = TecladoIn.readLineWord().toUpperCase();
                                System.out.println(edtaViajes.caminoMasCortodeAaByPasaPorC(aerA,aerB,aerC));
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("Opcion incorrecta");
                        }
                    }
                    break;
                case 9:
                    //mostrar clientes aptos promocion
                    System.out.println("Los clientes aptos para la promocion son");
                    System.out.println(edtaViajes.clientesAptosPromocion().toString());

                    break;
                case 10:
                    //mostrar grafo aeropuertos
                    System.out.println(edtaViajes.mostrarGrafoAeropuertos());
                    break;
                case 11:
                    //mostrar clientes almacenados
                    System.out.println(edtaViajes.mostrarClientes());
                    break;
                case 12:
                    //mostrar vuelos 
                    System.out.println(edtaViajes.mostrarVuelos());

                    break;
                case 13:
                    //mostrar mapeo de pasajes
                    System.out.println(edtaViajes.mostrarMapeoPasajes());
                case 14:
                    //mostrar estado del sistema
                    System.out.println(edtaViajes.mostrarEstadoSistema());
                    break;
                case 15:
                    break;
                default:
                    System.out.println("Opcion Incorrecta");
            }
        }
    }
    
    public static void menu(){
        System.out.println("Ingrese la opcion deseada");
        System.out.println("1. ABM Aeropuerto");
        System.out.println("2. ABM Vuelo");
        System.out.println("3. ABM Cliente");
        System.out.println("4  ABM Pasaje");
        System.out.println("5. Menu de viajes");
        System.out.println("6. Consultas sobre clientes");
        System.out.println("7. Consultas sobre vuelos");
        System.out.println("8. Consultas sobre tiempos de viaje");
        System.out.println("9. Clientes aptos para promocion");
        System.out.println("10. Mostrar grafo de aeropuertos");
        System.out.println("11. Mostrar clientes almacenados");
        System.out.println("12. Mostrar vuelos almacenados");
        System.out.println("13. Mostrar mapeo de pasajes");
        System.out.println("14. Mostrar estado del sistema");
        System.out.println("15. Salir");

    }
    public static void menuViaje(){
        System.out.println("1. Generar Viaje");
        System.out.println("2. Efectuar Viaje");
        System.out.println("3. Cancelar Viaje");
        System.out.println("4. Atras");
    }
    
    public static void ABMAeropuerto(){
        System.out.println("1. Añadir aeropuerto");
        System.out.println("2. Eliminar aeropuerto");
        System.out.println("3. Modificar aeropuerto");
        System.out.println("4. Mostrar aeropuerto");
        System.out.println("5. Atras");
    }
    public static void ABMCliente(){
        System.out.println("1. Añadir cliente");
        System.out.println("2. Eliminar cliente ");
        System.out.println("3. Modificar cliente");
        System.out.println("4. Mostrar cliente");
        System.out.println("5. Atras");
    }
    
    public static void ABMVuelo(){
        System.out.println("1. Añadir vuelo");
        System.out.println("2. Eliminar vuelo ");//al eliminar el vuelo tengo que eliminar la ruta entre aeropuertos
        //eliminar los viajes cancelar las compras de los clientes
        System.out.println("3. Modificar vuelo");
        System.out.println("4. Mostrar vuelo");
        System.out.println("5. Atras");
    }
    public static void ABMPasaje(){
        System.out.println("1. Añadir pasaje");
        System.out.println("2. Eliminar pasaje  ");
        System.out.println("3. Modificar pasaje");
        System.out.println("4. Atras");
    }
   
    public static void menuCliente(){
        System.out.println("1.Mostrar Informacion de vuelo y pasajes comprados");
        System.out.println("2.Mostrar las ciudades que ha visitado el cliente");
        System.out.println("3.Atras");
    }
    public static void menuVuelos(){
        System.out.println("1.Obtener informacion de vuelo. Ingrese el codigo de vuelo y la fecha");
        System.out.println("2.Obtener los codigos existentes entre dos codigos de vuelo");
        System.out.println("3.Atras");
    }
    public static void menuFunciones(){
        System.out.println("1.Verificar si el cliente que parte del aeropuerto a llegue al aeropuerto"
                + "b en como maximo x vuelos");
        System.out.println("2.Obtener el camino con menor tiempo de vuelo");
        System.out.println("3.Obtener el camino de A a B pasando por la minima cantidad"
                + "de aeropuertos");
        System.out.println("4.Obtener el camino mas rapido de A a B pasando por C");
        System.out.println("5.Atras");
    }
    public static void menuModificacionVuelo(){
        System.out.println("-------------------");
        System.out.println("1. Hora salida");
        System.out.println("2. Hora llegada");
        System.out.println("3. Atras");
    }
    
    public static void menuModificacionCliente(){
        System.out.println("------------------");
        System.out.println("1. Domilicio");
        System.out.println("2. Numero de telefono");
        System.out.println("3. Ambos");
        System.out.println("4. Atras");
    }
            
    
    }
    
   