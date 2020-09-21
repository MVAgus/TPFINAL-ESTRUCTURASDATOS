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
public class Testavl {
    
    
    public static void main(String args[]){
        ArbolAVL a = new ArbolAVL();
        
        a.insertar(60, "hola");
         a.insertar(80, "hola");
          a.insertar(23, "hola");
           a.insertar(33, "hola");
            a.insertar(40, "hola");
             a.insertar(32, "hola");
              a.insertar(51, "hola");
               a.insertar(89, "hola");
                a.insertar(32, "hola");
                 a.insertar(10, "hola");
                  a.insertar(13, "hola");
                   a.insertar(40, "hola");
                   System.out.println(a.listarDatos().toString());
           
           
           
    }
}
