/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.util.Objects;

/**
 *
 * @author mano_
 */
public class ClaveCliente implements Comparable {
    private String tipo;
    private String num;

    //Antes que nada verificar si los campos tipo y num de dni son correctos
    public ClaveCliente(String t, String n) {
        
        //TODO: escribir metodo para validar datos
        this.tipo = t.toUpperCase();
        this.num = n;
        
    }

    public String getTipo() {
        return tipo;
    }

    public String getNum() {
        return num;
    }
    
      

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.tipo);
        hash = 37 * hash + Objects.hashCode(this.num);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClaveCliente other = (ClaveCliente) obj;
        return true;
    }
    
    
    @Override
    public int compareTo(Object otro){
        //se compara tomando en primer lugar el tipo y luego el num
        int resultado;
        ClaveCliente cc = (ClaveCliente) otro;
        if (this.tipo.compareTo(cc.tipo) < 0) {
            resultado = -1; 
        } else if (this.tipo.compareTo(cc.tipo) > 0){
            resultado = 1;
        } else {
            //tipo es igual , debo verificar el num
            if (this.num.compareTo(cc.num) < 0){
                resultado = -2;
            } else if (this.num.compareTo(cc.num) > 0){
                resultado = 2;
            } else {
                resultado = 0;
            }
        }
        return resultado;
    }
    
   
    
}

