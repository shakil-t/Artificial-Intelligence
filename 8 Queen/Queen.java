/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8.queen;

import java.util.ArrayList;

/**
 *
 * @author shakil
 */
public class Queen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("BackTracjing:");
        BackTracking p1=new BackTracking();
        p1.bt();
        System.out.println();
    }
    
}
